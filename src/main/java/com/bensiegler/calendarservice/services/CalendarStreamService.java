package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.CalendarObjectMappingException;
import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
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
import com.bensiegler.calendarservice.models.dbmodels.DBCalendar;
import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.repositories.EventPropertyRepo;
import com.bensiegler.calendarservice.repositories.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class CalendarStreamService {

    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    EventPropertyRepo eventPropertyRepo;


    public void generate_iCalendarStream(String name) throws CalendarObjectMappingException, CalObjectException, PropertyException {
        DBCalendar DBCalendar = calendarRepo.findByName(name);
        DBCalendar.setDBEvents(eventRepo.findByCalendarId(DBCalendar.getId()));
        DBCalendar.setEventProperties(eventPropertyRepo.findByCalendarId(DBCalendar.getId()));
        Calendar cal = getCalStandardEvents(DBCalendar);
        cal.writeCalStreamToFile();
    }

    private Calendar getCalStandardEvents(DBCalendar dbCalendar) throws CalendarObjectMappingException {
        Calendar calendar = new Calendar();
        calendar.setProductIdentifier(new ProductIdentifier(dbCalendar.getName()));

        for(DBEvent e: dbCalendar.getDBEvents()) {
            DBProperty[] properties = dbCalendar.getEventProperties().stream()
                    .filter(property -> property.getEventId().equals(e.getId())).toArray(DBProperty[]::new);

            Stream<DBParameter> stream;

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
                            addObjectToArrayListField(Attachment.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Attendee")) {
                            addObjectToArrayListField(Attendee.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Categories")) {
                            addObjectToArrayListField(Categories.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Comments")) {
                            addObjectToArrayListField(Comment.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Contact")) {
                            addObjectToArrayListField(Contact.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("ExceptionsProperty")) {
                            addObjectToArrayListField(ExceptionsProperty.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("RelatedTo")) {
                            addObjectToArrayListField(RelatedTo.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("Resources")) {
                            addObjectToArrayListField(Resources.class, parameters, field, obj);

                        } else if (field.getName().equalsIgnoreCase("RecurrenceDates") ||
                                field.getName().equalsIgnoreCase("RecurrencePeriods") ||
                                field.getName().equalsIgnoreCase("RecurrenceDateTimes")
                        ) {
                            addObjectToArrayListField(RecurrencePeriods.class, parameters, field, obj);
                        }
                        field.setAccessible(false);
                        return;
                    } else {
                        //field is not ArrayList. Create new instance. Map parameters onto it. Set it to correct field;
                        Property newProperty = (Property) field.getType().getDeclaredConstructor().newInstance();


                        Method setContentUsingStringMethod = null;
                        try {
                            setContentUsingStringMethod = newProperty.getClass().getDeclaredMethod("setContentUsingString", String.class);
                        }catch (NoSuchMethodException e) {
                           //should not happen... is enforced in property class as abstract method
                        }

                        if(null == setContentUsingStringMethod) {
                            throw new Exception("This class is causing trouble " + field.getType());
                        }


                        setContentUsingStringMethod.invoke(newProperty, dbProperty.getContent());
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
    private void addObjectToArrayListField(Class<?> newPropertyType, DBParameter[] parameters, Field field, CalendarObject calendarObject) throws CalendarObjectMappingException {
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

