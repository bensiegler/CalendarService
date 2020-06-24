package com.bensiegler.calendarservice.models.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.properties.Property;
import com.bensiegler.calendarservice.models.properties.UnknownProperty;
import com.bensiegler.calendarservice.models.properties.changemanagement.Created;
import com.bensiegler.calendarservice.models.properties.changemanagement.DateTimeStamp;
import com.bensiegler.calendarservice.models.properties.changemanagement.LastModified;
import com.bensiegler.calendarservice.models.properties.changemanagement.Sequence;
import com.bensiegler.calendarservice.models.properties.descriptive.*;
import com.bensiegler.calendarservice.models.properties.relational.*;
import com.bensiegler.calendarservice.models.properties.temporal.dt.datetime.DateTimeStart;
import com.bensiegler.calendarservice.models.properties.temporal.dt.props.End;
import com.bensiegler.calendarservice.models.properties.temporal.dt.props.ExceptionsProperty;
import com.bensiegler.calendarservice.models.properties.temporal.dt.props.RecurrenceID;
import com.bensiegler.calendarservice.models.properties.temporal.misc.Duration;
import com.bensiegler.calendarservice.models.properties.temporal.misc.RecurrenceDates;
import com.bensiegler.calendarservice.models.properties.temporal.misc.RecurrenceRule;
import com.bensiegler.calendarservice.models.properties.temporal.misc.Transparency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Event extends CalendarObject{

    //universal add method
    public void addProperty(Property p) {
        Field[] fields = this.getClass().getDeclaredFields();
        int i = 0;
        for(Field f : fields) {
            f.setAccessible(true);

            try {
                if (f.getType().equals(p.getClass())) {
                    f.set(this, p);
                }else if(f.getType().equals(ArrayList.class) && p.getName().equalsIgnoreCase(f.getName())) {
                    if (p instanceof Attachment) {
                        attachments.add((Attachment) p);
                    } else if (p instanceof Attendee) {
                        attendees.add((Attendee) p);
                    } else if (p instanceof Categories) {
                        categories.add((Categories) p);
                    } else if (p instanceof Comment) {
                        comments.add((Comment) p);
                    } else if (p instanceof Contact) {
                        contacts.add((Contact) p);
                    }else if(p instanceof ExceptionsProperty) {
                        exceptionsDates.add((ExceptionsProperty)p);
                    }else if(p instanceof RelatedTo) {
                        relationships.add((RelatedTo)p);
                    }else if(p instanceof Resources) {
                        resources.add((Resources)p);
                    }else if(p instanceof RecurrenceDates) {
                        recurrenceDates.add((RecurrenceDates)p);
                    }else {
                        super.getUnknownProperties().add((UnknownProperty) p);
                    }
                }
            }catch (IllegalAccessException e) {
                //should not happen
            }
            f.setAccessible(false);
        }
    }

    //required
    private Calendar parent;
    private UID uid;
    private DateTimeStamp dateTimeStamp;

    public Calendar getParent() {
        return parent;
    }

    public void setParent(Calendar parent) {
        this.parent = parent;
    }

    public UID getUid() {
        return uid;
    }

    public void setUid(UID uid) {
        this.uid = uid;
    }

    public DateTimeStamp getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(DateTimeStamp dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    //required based on whether method is specified in parent
    private DateTimeStart dateTimeStart;

    public DateTimeStart getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(DateTimeStart dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    //optional, only once
    private Classification classification;
    private Created created;
    private Description description;
    private GeographicPosition geographicPosition;
    private LastModified lastModified;
    private Location location;
    private Organizer organizer;
    private Priority priority;
    private Sequence sequence;
    private Status status;
    private Summary summary;
    private Transparency transparency;
    private URL url;
    private RecurrenceID recurrenceID;
    private RecurrenceRule recurrenceRule;

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Created getCreated() {
        return created;
    }

    public void setCreated(Created created) {
        this.created = created;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public GeographicPosition getGeographicPosition() {
        return geographicPosition;
    }

    public void setGeographicPosition(GeographicPosition geographicPosition) {
        this.geographicPosition = geographicPosition;
    }

    public LastModified getLastModified() {
        return lastModified;
    }

    public void setLastModified(LastModified lastModified) {
        this.lastModified = lastModified;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Transparency getTransparency() {
        return transparency;
    }

    public void setTransparency(Transparency transparency) {
        this.transparency = transparency;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public RecurrenceID getRecurrenceID() {
        return recurrenceID;
    }

    public void setRecurrenceID(RecurrenceID recurrenceID) {
        this.recurrenceID = recurrenceID;
    }

    public RecurrenceRule getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    //one or the other
    private End end;
    private Duration duration;

    public End getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    //optional, more than once
    private ArrayList<Attachment> attachments = new ArrayList<>();
    private ArrayList<Attendee> attendees = new ArrayList<>();
    private ArrayList<Categories> categories = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<ExceptionsProperty> exceptionsDates = new ArrayList<>();
// TODO include statuses
    private ArrayList<RelatedTo> relationships = new ArrayList<>();
    private ArrayList<Resources> resources = new ArrayList<>();
    private ArrayList<RecurrenceDates> recurrenceDates = new ArrayList<>();

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }

    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<ExceptionsProperty> getExceptionsDates() {
        return exceptionsDates;
    }

    public void setExceptionsDates(ArrayList<ExceptionsProperty> exceptionsDates) {
        this.exceptionsDates = exceptionsDates;
    }


    public ArrayList<RelatedTo> getRelationships() {
        return relationships;
    }

    public void setRelationships(ArrayList<RelatedTo> relationships) {
        this.relationships = relationships;
    }

    public ArrayList<Resources> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resources> resources) {
        this.resources = resources;
    }

    public ArrayList<RecurrenceDates> getRecurrenceDates() {
        return recurrenceDates;
    }

    public void setRecurrenceDates(ArrayList<RecurrenceDates> recurrenceDates) {
        this.recurrenceDates = recurrenceDates;
    }

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        validate();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:VEVENT");
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field f: fields) {
            f.setAccessible(true);
            if(f.get(this) instanceof ArrayList ) {
                ArrayList<Property> list;
                try {
                    list = (ArrayList<Property>) f.get(this);
                }catch (ClassCastException e) {
                    throw new PropertyException("There is something very wrong with field "
                            +  f.getName() + " with type " + f.getType()
                            + ". It does not seem to be of type " + Property.class);
                }

                for(Property p: list) {
                    lines.add(Property.toCalStream(p));
                }
            }else if(!f.getName().equals("parent")) {
                try {
                   Property p  = (Property) f.get(this);
                   lines.add(Property.toCalStream(p));
                }catch (NullPointerException e) {
                    //do nothing
                }

            }
        }
        lines.add("END:VEVENT");
        return lines;
    }

    @Override
    public void validate() throws CalObjectException {
        if(null == parent) {
            throw new CalObjectException("Please specify a parent calendar object. " +
                    "This is required to enforce property rules");
        }
        if(null == uid) {
            throw new CalObjectException("UID is a required property for an Event");
        }

        if(null == dateTimeStamp) {
            throw new CalObjectException("DateTimeStamp is required for an Event");
        }

        if(null == dateTimeStart) {
            throw new CalObjectException("DateTimeStart is required for an event");
        }

        if(null == parent.getMethod() && null == dateTimeStart) {
            throw new CalObjectException("Either a method property must be specified in the parent object ("
                    + parent.getClass() + ") or DateTimeStart must be specified");
        }

        if(null != end && null != duration) {
            throw new CalObjectException("You can only specify either End or Duration. Not both at the same time.");
        }

    }
}
