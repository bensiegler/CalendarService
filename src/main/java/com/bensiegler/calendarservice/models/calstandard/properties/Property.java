package com.bensiegler.calendarservice.models.calstandard.properties;

import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.StringParameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Property {
    protected String name;
    protected ArrayList<UnknownParameter> extras = new ArrayList<>();

    public Property() {
    }

    public Property(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addExtra(UnknownParameter parameter) throws ParameterException {
        validateExtra(parameter);
        extras.add(parameter);
    }

    public void deleteExtra(String parameterName) throws ParameterException {
        for(StringParameter p: extras) {
            if(p.getName().equals(parameterName)) {
                extras.remove(p);
                return;
            }
        }

        throw new ParameterException("could not delete parameter with name " + parameterName
                + " since it could not be found");
    }

    public void editExtra(UnknownParameter parameter) throws ParameterException {
        validateExtra(parameter);
        deleteExtra(parameter.getName());
        addExtra(parameter);
    }

    public void setExtras(ArrayList<UnknownParameter> extras) throws ParameterException {
        this.extras = extras;
        validateExtras();
    }

    public ArrayList<UnknownParameter> getExtras() {
        return extras;
    }

    private void validateExtras() throws ParameterException {
        for(StringParameter p: extras) {
            if(!p.complete()) {
                throw new ParameterException("null value in parameter with name " + p.getName() + " or value "
                        + p.getValue());
            }
        }
    }

    private void validateExtra(StringParameter parameter) throws ParameterException {
        if(parameter.complete()) {
            throw new ParameterException("null value in parameter with name " + parameter.getName() + " or value "
                    + parameter.getValue());
        }
    }

    public abstract void validate() throws PropertyException;

    @Override
    public String toString() {
        String s = name;

            for (Parameter p : extras) {
                s += ";" + p.toString();
            }

        return s;
    }

    public static String toCalStream(Property p) throws PropertyException {
        Field[] fields;
        p.validate();

        try {
            fields = p.getNonContentFields();
        }catch (NoSuchFieldException e) {
            throw new PropertyException("Check fields in property " + p.getClass());
        }

        return p.toString() + p.addNonContentFields(fields, p) + p.addContentField(p);
    }

    private String listToDelimitedString(ArrayList<?> arrayList, String delimiter) {
        String s = "";

        for(Object obj: arrayList) {
            s += delimiter + obj.toString();
        }

        return s;
    }

    @SuppressWarnings("unchecked")
    private String addNonContentFields(Field[] fields, Property p) throws PropertyException {
        String calString = "";
        for(Field f: fields) {
            if(!f.getName().equals("content")) {
                try {
                    f.setAccessible(true);

                    if(f.get(p).equals("null")){
                        continue;
                    }

                    if(f.get(p) instanceof Iterable) {
                        //multiple of same type allowed
                        try {
                            calString += listToDelimitedString((ArrayList<Parameter>) f.get(p), ";");
                        }catch (ClassCastException e) {
                            throw new PropertyException("Either ArrayList in with name " + f.getName() + " in class " + p.getClass() + " must have enclosing type of " + Parameter.class + " or Iterable cannot cast to ArrayList");
                        }
                    }else {
                        //single of that type allowed
                        String value = f.get(p).toString();

                        if(!value.equals("null")) {
                            calString += ";" + value;
                        }

                    }
                    f.setAccessible(false);
                } catch (NullPointerException e) {
                    //field not set resulting in it not being included in calStream
                } catch (IllegalAccessException e) {
                    throw new PropertyException("Issue with access modifier on field \"" + f.getName() + "\" in class "
                            + p.getClass());
                }

            }
        }

        return calString;
    }

    private String addContentField(Property p) throws PropertyException {
        String calString = "";

        //make sure that content field is existent.
        Field contentField;
        try {
            contentField = getContentField();
            contentField.setAccessible(true);
        }catch (NoSuchFieldException e) {
            throw new PropertyException("Content field must specified in class " + p.getClass());
        }

        try {
            //add content field
            if (contentField.get(p) instanceof Iterable<?>) {
                //question ==  is this not prone to issues if something other than a ArrayList is passed to it? Even though
                // it may be iterable? It doesn't show unchecked cast.
                ArrayList<?> content = (ArrayList<?>) contentField.get(p);

                if (!(content.size() > 0)) {
                    throw new PropertyException("Content array empty. This is not allowed");
                }

                calString += ":";
                for(Object s: content) {
                    calString += s.toString() + ",";
                }

                //pull off extra comma
                calString = calString.substring(0, calString.length() - 1);
            } else {
                if(contentField.get(p) instanceof Parameter) {
                    Parameter param = (Parameter)contentField.get(p);
                    calString += ":" + param.toStringNoName();
                }else {
                    calString += ":" + contentField.get(p).toString();
                }
            }
        }catch (IllegalAccessException e) {
            throw new PropertyException("Issue with access modifier on field \"content\" in class "
                    + p.getClass());
        }
        contentField.setAccessible(false);
        return calString;
    }

    protected Field getContentField() throws NoSuchFieldException {
        return this.getClass().getDeclaredField("content");
    }

    protected Field[] getNonContentFields() throws NoSuchFieldException {
        return this.getClass().getDeclaredFields();
    }

    public abstract void setContentUsingString(String content);
}
