package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.Created;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.DateTimeStamp;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.LastModified;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.Sequence;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.*;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.*;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.DateTimeCompleted;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.Duration;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.RecurrenceRule;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class ToDo extends CalendarObject {


    //required, only once
    private DateTimeStamp dateTimeStamp;
    private UID uid;

    //optional, only once
    private Classification classification;
    private DateTimeCompleted dateTimeCompleted;
    private Created created;
    private Description description;
    private DateTimeStart dateTimeStart;
    private GeographicPosition geographicPosition;
    private LastModified lastModified;
    private Location location;
    private Organizer organizer;
    private PercentComplete percentComplete;
    private Priority priority;
    private DateTimeRecurrenceID recurrenceID;
    private Sequence sequence;
    private Status status;
    private Summary summary;
    private URL url;
    private RecurrenceRule recurrenceRule;

    //one or the other
    private DateTimeDue due;
    private Duration duration;

    //optional, more than once
    private ArrayList<Attachment> attachment = new ArrayList<>();
    private ArrayList<Attendee> attendee = new ArrayList<>();
    private ArrayList<Categories> categories = new ArrayList<>();
    private ArrayList<Comment> comment = new ArrayList<>();
    private ArrayList<Contact> contact = new ArrayList<>();
    private ArrayList<DateTimeExceptions> dateExceptions = new ArrayList<>();
    //TODO missing something called rStatus
    private ArrayList<RelatedTo> relatedTo = new ArrayList<>();
    private ArrayList<Resources> resources = new ArrayList<>();
    private ArrayList<DateTimeRecurrences> dateRecurrences = new ArrayList<>();


    public DateTimeStamp getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(DateTimeStamp dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public UID getUid() {
        return uid;
    }

    public void setUid(UID uid) {
        this.uid = uid;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public DateTimeCompleted getDateTimeCompleted() {
        return dateTimeCompleted;
    }

    public void setDateTimeCompleted(DateTimeCompleted dateTimeCompleted) {
        this.dateTimeCompleted = dateTimeCompleted;
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

    public DateTimeStart getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(DateTimeStart dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
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

    public PercentComplete getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(PercentComplete percentComplete) {
        this.percentComplete = percentComplete;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public DateTimeRecurrenceID getRecurrenceID() {
        return recurrenceID;
    }

    public void setRecurrenceID(DateTimeRecurrenceID recurrenceID) {
        this.recurrenceID = recurrenceID;
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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public RecurrenceRule getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public DateTimeDue getDue() {
        return due;
    }

    public void setDue(DateTimeDue due) {
        this.due = due;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ArrayList<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(ArrayList<Attachment> attachment) {
        this.attachment = attachment;
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

    public ArrayList<Comment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
    }

    public ArrayList<Contact> getContact() {
        return contact;
    }

    public void setContact(ArrayList<Contact> contact) {
        this.contact = contact;
    }

    public ArrayList<DateTimeExceptions> getDateExceptions() {
        return dateExceptions;
    }

    public void setDateExceptions(ArrayList<DateTimeExceptions> dateExceptions) {
        this.dateExceptions = dateExceptions;
    }

    public ArrayList<RelatedTo> getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(ArrayList<RelatedTo> relatedTo) {
        this.relatedTo = relatedTo;
    }

    public ArrayList<Resources> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resources> resources) {
        this.resources = resources;
    }

    public ArrayList<DateTimeRecurrences> getDateRecurrences() {
        return dateRecurrences;
    }

    public void setDateRecurrences(ArrayList<DateTimeRecurrences> dateRecurrences) {
        this.dateRecurrences = dateRecurrences;
    }

    @Override
    public ArrayList<String> getCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException {
        validate();
        ArrayList<String> lines = new ArrayList<>();
        lines.add("BEGIN:VTODO");
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredFields()));

        try {
            fields.add(this.getClass().getSuperclass().getDeclaredField("unknownProperties"));
        }catch (NoSuchFieldException e) {
            throw new CalObjectException("check CalendarObject class for unknownProperties field. It seems to not exist");
        }

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
        lines.add("END:VTODO");
        return lines;
    }

    @Override
    public void validate() throws CalObjectException {
        if(null == dateTimeStamp) {
            throw new CalObjectException("ToDo object require a DateTimeStamp");
        }

        if(null == uid) {
            throw new CalObjectException("ToDo object require a UID");
        }

        if(null != due && null != duration) {
            throw new CalObjectException("A ToDo object can not have both a Due (date) and a Duration set");
        }

        if(duration != null && null == dateTimeStart) {
            throw new CalObjectException("If a duration is set a DateTimeStart is also required.");
        }
    }
}
