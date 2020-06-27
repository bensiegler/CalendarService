package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.CalendarObjectMappingException;
import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Date;
import com.bensiegler.calendarservice.models.calstandard.datatypes.DateTime;
import com.bensiegler.calendarservice.models.calstandard.datatypes.Period;
import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.FormatType;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.RSVPExpectation;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.TimeZoneIdentifier;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.UnknownProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.Attendee;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.Contact;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.RelatedTo;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.props.ExceptionsProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence.RecurrencePeriods;
import com.bensiegler.calendarservice.models.dbmodel.DBCalendar;
import com.bensiegler.calendarservice.models.dbmodel.DBEvent;
import com.bensiegler.calendarservice.models.dbmodel.DBParameter;
import com.bensiegler.calendarservice.models.dbmodel.DBProperty;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class CalendarStreamService {

    @Autowired
    CalendarRepo calendarRepo;


    public void generate_iCalendarStream(Long calendarId) throws CalendarObjectMappingException, CalObjectException, PropertyException {
        DBCalendar DBCalendar = calendarRepo.findOne(calendarId);
        Calendar cal = getCalStandardEvents(DBCalendar);
        cal.writeCalStreamToFile();
    }

    private Calendar getCalStandardEvents(DBCalendar dbCalendar) throws CalendarObjectMappingException {
        Calendar calendar = new Calendar();
        calendar.setProductIdentifier(new ProductIdentifier(dbCalendar.getName()));

        for(DBEvent e: dbCalendar.getDBEvents()) {
            DBProperty[] properties = dbCalendar.getEventProperties().stream()
                    .filter(property -> property.getEventId().equals(e.getId())).toArray(DBProperty[]::new);

            Stream<DBParameter> stream = dbCalendar.getDBParameters().stream();

            Event event = new Event();
            event.setParent(calendar);

            for(DBProperty property: properties) {
                stream = dbCalendar.getDBParameters().stream();
                mapPropertyOnToCalendarObject(property, stream, event);
            }

            calendar.addCalObject(event);

        }
        return calendar;
    }

    private void mapPropertyOnToCalendarObject(DBProperty dbProperty,
                                              Stream<DBParameter> parameterStream,
                                              CalendarObject obj) throws CalendarObjectMappingException {
        //get parameters for this property
        DBParameter[] parameters = parameterStream
                .filter(parameter -> parameter.getPropertyId().equals(dbProperty.getId()))
                .toArray(DBParameter[]::new);

        try {
            //get all fields for the type of CalendarObject given
            Field[] fields = obj.getClass().getDeclaredFields();


            //set property onto CalendarObject
            for (Field field : fields) {
                if (field.getName().equalsIgnoreCase(dbProperty.getName())) {
                    field.setAccessible(true);
                    //TODO try to make this better

                    if (field.getType().equals(ArrayList.class)) {
                        //get the name of the field and use that to determine what object you need to create.
                        if (field.getName().equalsIgnoreCase("attachment")) {
                            addObjectToFieldOfTypeArrayList(Attachment.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Attendee")) {
                            addObjectToFieldOfTypeArrayList(Attendee.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Categories")) {
                            addObjectToFieldOfTypeArrayList(Categories.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Comment")) {
                            addObjectToFieldOfTypeArrayList(Comment.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Contact")) {
                            addObjectToFieldOfTypeArrayList(Contact.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("ExceptionsProperty")) {
                            addObjectToFieldOfTypeArrayList(ExceptionsProperty.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("RelatedTo")) {
                            addObjectToFieldOfTypeArrayList(RelatedTo.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Resources")) {
                            addObjectToFieldOfTypeArrayList(Resources.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("RecurrenceDates")) {
                            addObjectToFieldOfTypeArrayList(RecurrencePeriods.class, parameters, field, obj);

                        }
                        field.setAccessible(false);
                        return;
                    } else {
                        //field is not ArrayList. Create new instance. Map parameters onto it. Set it to correct field;
                        Property newProperty = (Property) field.getType().getDeclaredConstructor().newInstance();

                        Field contentField = newProperty.getClass().getDeclaredField("content");
                        Method setContentMethod = null;
                        try {
                            setContentMethod = newProperty.getClass().getDeclaredMethod("setContent", contentField.getType());
                        }catch (NoSuchMethodException e) {
                            Method[] methods = newProperty.getClass().getDeclaredMethods();
                            for(Method m: methods) {
                                if(m.getName().equalsIgnoreCase("setContent")) {
                                    setContentMethod = m;
                                }
                            }

                        }

                        if(null == setContentMethod) {
                            throw new Exception("This class is causing trouble " + field.getType());
                        }

                        //TODO you also really have to make this better
                        if(contentField.getType().equals(String.class)) {
                            setContentMethod.invoke(newProperty, dbProperty.getContent());
                        }else if(contentField.getType().equals(Long.class)) {
                            setContentMethod.invoke(newProperty,Long.parseLong(dbProperty.getContent()));
                        }else if(contentField.getType().equals(Integer.class)) {
                            setContentMethod.invoke(newProperty,Integer.parseInt(dbProperty.getContent()));
                        }else if(contentField.getType().equals(String[].class)) {
                            String[] contentArr = dbProperty.getContent().split(",");
                            setContentMethod.invoke(newProperty,contentArr);
                        }else if(contentField.getType().equals(Duration.class)) {
                            Duration duration = Duration.ofMillis(Long.parseLong(dbProperty.getContent()));
                            setContentMethod.invoke(newProperty,duration);
                        }else if(contentField.getType().equals(Period[].class)) {
                            String[] stringArr = dbProperty.getContent().split(",");
                            Period[] periods = new Period[stringArr.length];

                            for(int i = 0; i < stringArr.length; i++) {
                                String[] substrings = stringArr[i].split("/");

                                periods[i] = new Period(new DateTime(Long.parseLong(substrings[0])),
                                        Duration.ofMillis(Long.parseLong(substrings[1])));
                            }
                            setContentMethod.invoke(newProperty,periods);
                        }else if(contentField.getType().equals(Date[].class)) {
                            String[] stringArr = dbProperty.getContent().split(",");
                            Date[] dates = new Date[stringArr.length];

                            for(int i = 0; i < stringArr.length; i++) {
                                dates[i] = new Date(Long.parseLong(stringArr[i]));
                            }
                            setContentMethod.invoke(newProperty,dates);
                        }else if(contentField.getType().equals(DateTime[].class)) {
                            String[] stringArr = dbProperty.getContent().split(",");
                            DateTime[] dates = new DateTime[stringArr.length];

                            for (int i = 0; i < stringArr.length; i++) {
                                dates[i] = new DateTime(Long.parseLong(stringArr[i]));
                            }
                            setContentMethod.invoke(newProperty,dates);
                        }else if(contentField.getType().equals(DateTime.class) || contentField.getType().equals(Date.class)) {
                            setContentMethod.invoke(newProperty,Long.parseLong(dbProperty.getContent()));
                        }else {
                            throw new Exception(field.getType() + " is causing trouble");
                        }

                        mapParametersToProperty(parameters, newProperty);

                        field.set(obj, newProperty);
                        field.setAccessible(false);
                        return;
                    }
                }

            }

            //if no fields are found that match, the property is not known and will be added as unknown.
            UnknownProperty unknownProperty = new UnknownProperty();
            mapParametersToProperty(parameters, unknownProperty);
            obj.addNewUnknownProperty(unknownProperty);
        }catch (Exception e) {
            throw new CalendarObjectMappingException(e.getMessage(), e.getCause());
        }
    }

    @SuppressWarnings({"unchecked"})
    private void addObjectToFieldOfTypeArrayList(Class<?> newPropertyType, DBParameter[] parameters, Field field, CalendarObject calendarObject) throws CalendarObjectMappingException {
        try {
            Property newProperty = (Property) newPropertyType.getDeclaredConstructor().newInstance();

            mapParametersToProperty(parameters, newProperty);
            ArrayList<Property> list = (ArrayList<Property>) field.get(calendarObject);
            list.add(newProperty);
        }catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | ParameterException e) {
            throw new CalendarObjectMappingException(e.getMessage(), e.getCause());
        }catch (ClassCastException e) {
            throw new CalendarObjectMappingException(
                    "Please check arraylist fields in calendarobject class of type "
                            + calendarObject.getClass(), e.getCause());
        }
    }

    private void mapParametersToProperty(DBParameter[] parameters, Property property) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ParameterException {
        //At this point property is of the correct type!
        ArrayList<Field> fieldArrayList = new ArrayList<>(Arrays.asList(property.getClass().getDeclaredFields()));
        boolean alreadyMapped;
        for(DBParameter parameter: parameters) {
            alreadyMapped = false;
            for (Field f : fieldArrayList) {
                if (parameter.getName().equalsIgnoreCase(f.getName()) && !f.getName().equalsIgnoreCase("content")) {
                    alreadyMapped = true;
                    f.set(property, mapValuesOntoParameters(parameter, f.getType()));
                }
            }

            if(!alreadyMapped) {
                property.addExtra(new UnknownParameter(parameter.getName(), parameter.getContent()));
            }
        }



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

