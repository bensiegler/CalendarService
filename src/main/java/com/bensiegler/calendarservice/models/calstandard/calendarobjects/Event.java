package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.UnknownProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.Created;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.DateTimeStamp;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.LastModified;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.Sequence;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.*;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeEnd;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeExceptions;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeRecurrenceID;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeStart;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.CustomDuration;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.RecurrenceRule;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.Transparency;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.recurrence.Recurrence;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZIdentifierProperty;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Event extends CalendarObject{

    //universal add method
    public void addProperty(Property p) {
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field f : fields) {
            f.setAccessible(true);

            try {
                if (f.getType().equals(p.getClass())) {
                    f.set(this, p);
                }else if(f.getType().equals(ArrayList.class) && p.getName().equalsIgnoreCase(f.getName())) {
                    if (p instanceof Attachment) {
                        attachments.add((Attachment) p);
                    } else if (p instanceof Attendee) {
                        attendee.add((Attendee) p);
                    } else if (p instanceof Categories) {
                        categories.add((Categories) p);
                    } else if (p instanceof Comment) {
                        comments.add((Comment) p);
                    } else if (p instanceof Contact) {
                        contacts.add((Contact) p);
                    }else if(p instanceof DateTimeExceptions) {
                        exceptionsDates.add((DateTimeExceptions)p);
                    }else if(p instanceof RelatedTo) {
                        relationships.add((RelatedTo)p);
                    }else if(p instanceof Resources) {
                        resources.add((Resources)p);
                    }else if(p instanceof Recurrence) {
                        recurrenceInfo.add((Recurrence) p);
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
    private UID uid = new UID();
    private DateTimeStamp dateTimeStamp = new DateTimeStamp();

    //required based on whether method is specified in parent
    private DateTimeStart dateTimeStart = new DateTimeStart();

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
    private Summary summary = new Summary();
    private Transparency transparency;
    private URL url;
    private DateTimeRecurrenceID recurrenceID;
    private RecurrenceRule recurrenceRule;
    private TZIdentifierProperty TZIdentifierProperty;


    //one or the other
    private DateTimeEnd dateTimeEnd;
    private CustomDuration customDuration;

    //optional, more than once
    private ArrayList<Attachment> attachments = new ArrayList<>();
    private ArrayList<Attendee> attendee = new ArrayList<>();
    private ArrayList<Categories> categories = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<DateTimeExceptions> exceptionsDates = new ArrayList<>();
// TODO include statuses
    private ArrayList<RelatedTo> relationships = new ArrayList<>();
    private ArrayList<Resources> resources = new ArrayList<>();
    private ArrayList<Recurrence> recurrenceInfo = new ArrayList<>();

    private ArrayList<Alarm> alarms = new ArrayList<>();


    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public UID getUid() {
        return uid;
    }

    public void setUid(UID uid) {
        this.uid = uid;
    }

    public TZIdentifierProperty getTZIdentifierProperty() {
        return TZIdentifierProperty;
    }

    public void setTZIdentifierProperty(TZIdentifierProperty TZIdentifierProperty) {
        this.TZIdentifierProperty = TZIdentifierProperty;
    }

    public DateTimeStamp getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(DateTimeStamp dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public DateTimeStart getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(DateTimeStart dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

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

    public DateTimeRecurrenceID getRecurrenceID() {
        return recurrenceID;
    }

    public void setRecurrenceID(DateTimeRecurrenceID recurrenceID) {
        this.recurrenceID = recurrenceID;
    }

    public RecurrenceRule getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public DateTimeEnd getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(DateTimeEnd dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public CustomDuration getCustomDuration() {
        return customDuration;
    }

    public void setCustomDuration(CustomDuration customDuration) {
        this.customDuration = customDuration;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<Attendee> getAttendee() {
        return attendee;
    }

    public void setAttendee(ArrayList<Attendee> attendee) {
        this.attendee = attendee;
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

    public ArrayList<DateTimeExceptions> getExceptionsDates() {
        return exceptionsDates;
    }

    public void setExceptionsDates(ArrayList<DateTimeExceptions> exceptionsDates) {
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

    public ArrayList<Recurrence> getRecurrenceInfo() {
        return recurrenceInfo;
    }

    public void setRecurrenceInfo(ArrayList<Recurrence> recurrenceInfo) {
        this.recurrenceInfo = recurrenceInfo;
    }

    @Override
    public String retrieveCalStream() throws PropertyException, CalObjectException {
        validate();
        StringBuilder string = new StringBuilder();
        string.append("BEGIN:VEVENT").append("\n");
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.get(this) instanceof ArrayList) {
                    ArrayList<Property> list;
                    try {
                        list = (ArrayList<Property>) f.get(this);
                    } catch (ClassCastException e) {
                        throw new PropertyException("There is something wrong with field "
                                + f.getName() + " with type " + f.getType()
                                + ". It does not seem to be of type " + Property.class);
                    }

                    for (Property p : list) {
                        string.append(Property.toCalStream(p)).append("\n");
                    }
                } else if (!f.getName().equals("parent")) {
                    try {
                        Property p = (Property) f.get(this);
                        string.append(Property.toCalStream(p)).append("\n");
                    } catch (NullPointerException e) {
                        //do nothing
                    }

                }

                for (Alarm a : alarms) {
                    string.append(a.retrieveCalStream()).append("\n");
                }
            }
        }catch (IllegalAccessException e) {
            //should not happen
        }
        string.append("END:VEVENT").append("\n");
        return string.toString();
    }

    @Override
    public void validate() throws CalObjectException {

        if(null == uid) {
            throw new CalObjectException("UID is a required property for an Event");
        }

        if(null == dateTimeStamp) {
            throw new CalObjectException("DateTimeStamp is required for an Event");
        }

        if(null == dateTimeStart) {
            throw new CalObjectException("DateTimeStart is required for an Event");
        }

        if(null != dateTimeEnd && null != customDuration) {
            throw new CalObjectException("You can only specify either End or Duration. Not both at the same time.");
        }

    }
}
