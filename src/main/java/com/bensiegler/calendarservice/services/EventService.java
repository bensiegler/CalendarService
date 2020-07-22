package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.UID;
import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.repositories.EventPropertyRepo;
import com.bensiegler.calendarservice.repositories.EventRepo;
import com.bensiegler.calendarservice.repositories.PropertyParameterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Service
public class EventService {
    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    EventPropertyRepo eventPropertyRepo;

    @Autowired
    PropertyParameterRepo propertyParameterRepo;

    public Event saveEvent(Event event, Long calendarId) throws CalObjectException, PropertyException {
        event.validate();

        if(calendarRepo.findOne(calendarId) == null) {
            throw new CalObjectException("No calendar was found with that ID");
        }

        DBEvent dbEvent;

        //save event
        try {
            dbEvent = new DBEvent(event.getDescription().getContent(), calendarId);
        }catch (NullPointerException e) {
            dbEvent = new DBEvent("New Event", calendarId);
        }
        eventRepo.save(dbEvent);

        //save properties
        ArrayList<Property> eventProperties = getProperties(event);
        saveProperties(eventProperties, calendarId, dbEvent.getId());


        event.setUid(new UID(String.valueOf(dbEvent.getId())));
        return event;
    }

    private void saveProperties(ArrayList<Property> properties, Long calendarId, Long eventId) throws PropertyException {

        for (Property p : properties) {
            //save property
            DBProperty dbProperty = new DBProperty();
            dbProperty.setCalendarId(calendarId);
            dbProperty.setEventId(eventId);
            dbProperty.setContent(p.retrieveContentAsString());
            dbProperty.setName(p.getClass().getSimpleName());
            eventPropertyRepo.save(dbProperty);

            //save property parameters
            ArrayList<Parameter> propertyParameters = getPropertyParameters(p);
            ArrayList<DBParameter> dbParameters = parameterToDBParameter(propertyParameters,
                    calendarId, eventId, dbProperty.getId());
            propertyParameterRepo.save(dbParameters);

        }

    }

    private ArrayList<Property> getProperties(CalendarObject calendarObject)  {
        Class<?> calendarObjectClass = calendarObject.getClass();
        Field[] calendarObjectFields = calendarObjectClass.getDeclaredFields();
        ArrayList<Property> properties = new ArrayList<>();

        try {
            for (Field f : calendarObjectFields) {
                f.setAccessible(true);
                Object object = f.get(calendarObject);

                if (object instanceof Property) {
                    properties.add((Property) object);
                }

                f.setAccessible(false);
            }
        }catch (IllegalAccessException e) {
            //do nothing... shouldn't happen
        }

        return properties;
    }

    private ArrayList<Parameter> getPropertyParameters(Property property) {
        try {
            Field[] propertyFields;

            propertyFields = property.retrieveNonContentFields();
            ArrayList<Parameter> parameters = new ArrayList<>();

            for (Field propertyField : propertyFields) {

                propertyField.setAccessible(true);
                if (propertyField.get(property) instanceof Parameter) {
                    parameters.add((Parameter)propertyField.get(property));
                }

                propertyField.setAccessible(false);
            }
            return parameters;

        }catch (NoSuchFieldException | IllegalAccessException e){
            //shouldn't happen
            return null;
        }

    }

    private ArrayList<DBParameter> parameterToDBParameter(ArrayList<Parameter> parameters , Long calendarId, Long eventId, Long propertyId) {
        ArrayList<DBParameter> dbParameters = new ArrayList<>();

        for(Parameter p: parameters) {
            DBParameter dbParameter = new DBParameter();
            dbParameter.setCalendarId(calendarId);
            dbParameter.setEventId(eventId);
            dbParameter.setPropertyId(propertyId);
            dbParameter.setName(p.getClass().getSimpleName());
            dbParameter.setContent(p.toStringNoName());
            dbParameters.add(dbParameter);
        }

        return dbParameters;
    }
}
