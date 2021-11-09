package com.bensiegler.calendarservice.services;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.models.calstandard.parameters.Parameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.Created;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.LastModified;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.UID;
import com.bensiegler.calendarservice.models.dbmodels.DBEvent;
import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.repositories.EventPropertyRepo;
import com.bensiegler.calendarservice.repositories.EventRepo;
import com.bensiegler.calendarservice.repositories.PropertyParameterRepo;
import com.bensiegler.calendarservice.services.calstream.StreamObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

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

    @Autowired
    StreamObjectService streamObjectService;

    public Event saveEvent(Event event, String calendarId) throws CalObjectException, PropertyException {
        event.validate();

        event.setCreated(new Created(System.currentTimeMillis()));
        event.setLastModified(new LastModified(System.currentTimeMillis()));

        if(calendarRepo.findOne(calendarId) == null) {
            throw new CalObjectException("No calendar was found with that ID");
        }

        DBEvent dbEvent;

        //save event
        try {
            dbEvent = new DBEvent(event.getSummary().getContent(), calendarId);
        }catch (NullPointerException e) {
            dbEvent = new DBEvent("New Event", calendarId);
        }

        eventRepo.save(dbEvent);

        //save properties
        event.setUid(new UID(String.valueOf(dbEvent.getId())));
        ArrayList<Property> eventProperties = getProperties(event);
        saveProperties(eventProperties, calendarId, dbEvent.getId());


        return event;
    }

    public Event getEventById(String eventId) throws Exception {

        Event event = new Event();

        DBEvent dbEvent = eventRepo.findOne(eventId);

        if(null == dbEvent) {
            throw new CalObjectException("No event found with that ID");
        }

        event.setUid(new UID(dbEvent.getId()));
        ArrayList<DBProperty> properties = eventPropertyRepo.findByCalendarObjectId(eventId);

        for(DBProperty property: properties) {
            streamObjectService.mapPropertyOntoCalendarObject(property, property.getParameters().stream(), event);
        }

        return event;
    }

    @Transactional
    public Event deleteEventById(String eventId) throws Exception {
        Event event = getEventById(eventId);

        propertyParameterRepo.deleteByCalendarObjectId(eventId);
        eventPropertyRepo.deleteByCalendarObjectId(eventId);
        eventRepo.delete(eventId);

        return event;
    }

    private void saveProperties(ArrayList<Property> properties, String calendarId, String eventId)  {
        for (Property p : properties) {
            //save property
            DBProperty dbProperty = new DBProperty();
            dbProperty.setCalendarId(calendarId);
            dbProperty.setCalendarObjectId(eventId);
            dbProperty.setContent(p.retrieveContentAsString());
            dbProperty.setName(p.getClass().getSimpleName());

            eventPropertyRepo.save(dbProperty);

            //save property parameters
            ArrayList<Parameter> propertyParameters = getPropertyParameters(p);

            assert propertyParameters != null;

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
                    properties.add((Property)object);
                }else if (object instanceof ArrayList<?>) {
                    try {
                        if (((ArrayList<?>) object).get(0) instanceof Property) {
                            ArrayList<Property> list = (ArrayList<Property>) object;
                            properties.addAll(list);
                        }
                    }catch (IndexOutOfBoundsException e) {
                       //do nothing means empty array when checking if type property
                    }
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

    private ArrayList<DBParameter> parameterToDBParameter(ArrayList<Parameter> parameters , String calendarId, String eventId, String propertyId) {
        ArrayList<DBParameter> dbParameters = new ArrayList<>();

        for(Parameter p: parameters) {
            DBParameter dbParameter = new DBParameter();
            dbParameter.setPropertyId(propertyId);
            dbParameter.setCalendarObjectId(eventId);
            dbParameter.setName(p.getClass().getSimpleName());
            dbParameter.setContent(p.toStringNoName());
            dbParameters.add(dbParameter);
        }

        return dbParameters;
    }
}
