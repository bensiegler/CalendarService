package com.bensiegler.calendarservice.services.calstream;

import com.bensiegler.calendarservice.exceptions.CalendarObjectMappingException;
import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.CalendarObject;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.UnknownProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Attachment;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Categories;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Comment;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Resources;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.Attendee;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.Contact;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.RelatedTo;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DTTemplate;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeExceptions;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence.RecurrencePeriods;
import com.bensiegler.calendarservice.models.dbmodels.DBParameter;
import com.bensiegler.calendarservice.models.dbmodels.DBProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.stream.Stream;

@Service
public class StreamObjectService {
    @Autowired
    StreamPropertyService streamPropertyService;

    public void mapPropertyOntoCalendarObject(DBProperty dbProperty,
                                       Stream<DBParameter> parameterStream,
                                       CalendarObject obj) throws Exception {
        //get parameters for this property
        DBParameter[] parameters = parameterStream
                .filter(parameter -> parameter.getPropertyId().equals(dbProperty.getId()))
                .toArray(DBParameter[]::new);

//        try {
        //get all fields for the type of CalendarObject given
        Field[] fields = obj.getClass().getDeclaredFields();

        //set property onto CalendarObject
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase(dbProperty.getName())) {
                field.setAccessible(true);

                if (field.getType().equals(ArrayList.class)) {
                    //get the name of the field and use that to determine what object you need to create.
                    if (field.getName().equalsIgnoreCase("attachment")) {
                        addObjectToArrayListField(dbProperty, Attachment.class, parameters, field, obj);
                    } else if (field.getName().equalsIgnoreCase("Attendee")) {
                        addObjectToArrayListField(dbProperty, Attendee.class, parameters, field, obj);

                    } else if (field.getName().equalsIgnoreCase("Categories")) {
                        addObjectToArrayListField(dbProperty, Categories.class, parameters, field, obj);
                    } else if (field.getName().equalsIgnoreCase("Comments")) {
                        addObjectToArrayListField(dbProperty, Comment.class, parameters, field, obj);

                    } else if (field.getName().equalsIgnoreCase("Contact")) {
                        addObjectToArrayListField(dbProperty, Contact.class, parameters, field, obj);

                    } else if (field.getName().equalsIgnoreCase("ExceptionsProperty")) {
                        addObjectToArrayListField(dbProperty, DateTimeExceptions.class, parameters, field, obj);

                    } else if (field.getName().equalsIgnoreCase("RelatedTo")) {
                        addObjectToArrayListField(dbProperty, RelatedTo.class, parameters, field, obj);

                    } else if (field.getName().equalsIgnoreCase("Resources")) {
                        addObjectToArrayListField(dbProperty, Resources.class, parameters, field, obj);

                    } else if (field.getName().equalsIgnoreCase("RecurrenceDates") ||
                            field.getName().equalsIgnoreCase("RecurrencePeriods") ||
                            field.getName().equalsIgnoreCase("RecurrenceDateTimes")
                    ) {
                        addObjectToArrayListField(dbProperty, RecurrencePeriods.class, parameters, field, obj);
                    }
                    field.setAccessible(false);
                } else {
                    //field is not Array. Create new instance. Map parameters onto it. Set it to correct field;
                    Property newProperty = (Property) field.getType().getDeclaredConstructor().newInstance();

                    if(newProperty instanceof DTTemplate) {
                        //TODO check to see if db says date or just datetime. If it says date tell this object that it should
                        // show only date.
                    }

                    setContentOnProperty(newProperty, dbProperty);
                    newProperty = streamPropertyService.getCalStandardProperty(parameters, newProperty);

                    field.set(obj, newProperty);
                    field.setAccessible(false);
                    return;
                }
            }

        }

        //if no fields are found that match, the property is not known and will be added as unknown.
        UnknownProperty unknownProperty = new UnknownProperty();
        streamPropertyService.getCalStandardProperty(parameters, unknownProperty);
        obj.addNewUnknownProperty(unknownProperty);
//        }catch (Exception e) {
//            throw new CalendarObjectMappingException(e.getMessage());
//        }
    }

    private void setContentOnProperty(Property newProperty, DBProperty dbProperty) throws InvocationTargetException, IllegalAccessException {
        Method setContentUsingStringMethod = null;
        try {
            setContentUsingStringMethod = newProperty.getClass().getDeclaredMethod("setContentUsingString", String.class);
        }catch (NoSuchMethodException e) {
            //should not happen... is enforced in property class as abstract method
        }

        assert setContentUsingStringMethod != null;
        setContentUsingStringMethod.setAccessible(true);
        setContentUsingStringMethod.invoke(newProperty, dbProperty.getContent());
        setContentUsingStringMethod.setAccessible(false);

    }

    @SuppressWarnings({"unchecked"})
    private void addObjectToArrayListField(DBProperty dbProperty, Class<?> newPropertyType, DBParameter[] parameters, Field field, CalendarObject calendarObject) throws CalendarObjectMappingException {
        try {
            Property newProperty = (Property) newPropertyType.getDeclaredConstructor().newInstance();
            setContentOnProperty(newProperty, dbProperty);
            streamPropertyService.getCalStandardProperty(parameters, newProperty);
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


}
