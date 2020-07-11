package com.bensiegler.calendarservice.services.events;

import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.FormatType;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.RSVPExpectation;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class StreamPropertyService {

    public Property getCalStandardProperty(DBParameter[] dbParameters, Property property) throws InvocationTargetException, NoSuchMethodException, ParameterException, InstantiationException, IllegalAccessException {
        return mapParametersToProperty(dbParameters, property);
    }

    private Property mapParametersToProperty(DBParameter[] parameters, Property property) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ParameterException {
        //At this point property is of the correct type!
        ArrayList<Field> fieldArrayList = new ArrayList<>(Arrays.asList(property.getClass().getDeclaredFields()));
        boolean alreadyMapped;
        for(DBParameter parameter: parameters) {
            alreadyMapped = false;
            for (Field f : fieldArrayList) {
                f.setAccessible(true);
                if (parameter.getName().equalsIgnoreCase(f.getName()) && !f.getName().equalsIgnoreCase("content")) {
                    alreadyMapped = true;
                    Parameter newParam = mapValuesOntoParameters(parameter, f.getType());
                    f.set(property, newParam);
                }
                f.setAccessible(false);
            }

            if(!alreadyMapped) {
                property.addExtra(new UnknownParameter(parameter.getName(), parameter.getContent()));
            }
        }


        return property;
    }


    private Parameter mapValuesOntoParameters(DBParameter dbParameter, Class<?> parameterClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //unknown parameter fix
        if(parameterClass.equals(UnknownParameter.class)) {
            return new UnknownParameter(dbParameter.getName(), dbParameter.getContent());
        }

        try {
            Parameter parameter = (Parameter) parameterClass.getDeclaredConstructor().newInstance();
            Field contentField;
            try {
                contentField= parameterClass.getDeclaredField("value");
            }catch (NoSuchFieldException e) {
                contentField = parameterClass.getSuperclass().getDeclaredField("value");
            }

            contentField.setAccessible(true);

            if(contentField.getType().equals(String.class)){
                contentField.set(parameter, dbParameter.getContent());

            }else if (contentField.getType().equals(ArrayList.class)) {
                ArrayList<String> items =
                        new ArrayList<>(Arrays.asList(dbParameter.getContent().split(",")));
                contentField.set(parameter, items);
            }

            contentField.setAccessible(false);

            return parameter;
        }catch (NoSuchFieldException e) {
            //this is a misc. parameter if you hit this
            if(parameterClass.equals(FormatType.class)) {
                String[] holder = dbParameter.getContent().split("/");
                return new FormatType(holder[0], holder[1]);

            }else if(parameterClass.equals(RSVPExpectation.class)) {
                if(dbParameter.getContent().equalsIgnoreCase("true")) {
                    return new RSVPExpectation(true);
                }else{
                    return new RSVPExpectation(false);
                }
            }else {
                String[] holder = dbParameter.getContent().split("/");
                return new TimeZoneIdentifier(holder[0], holder[1]);
            }
        }

    }


}
