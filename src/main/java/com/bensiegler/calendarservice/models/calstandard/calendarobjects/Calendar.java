package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Calendar {
    //TODO add name field for own system identification
    private ProductIdentifier productIdentifier;
    private final Version version = new Version("2.0");
    private CalendarScale calendarScale = new CalendarScale("GREGORIAN");
    private Method method = new Method("PUBLISH");
    private Color color = new Color("BLUE");

    public ProductIdentifier getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(ProductIdentifier productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public Version getVersion() {
        return version;
    }

    public CalendarScale getCalendarScale() {
        return calendarScale;
    }

    public void setCalendarScale(CalendarScale calendarScale) {
        this.calendarScale = calendarScale;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


    private final ArrayList<TimeZone> timeZones = new ArrayList<>();
    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<FreeBusy> freeBusies = new ArrayList<>();
    private final ArrayList<ToDo> toDos = new ArrayList<>();


    public void addCalObject(CalendarObject calendarObject) {
        if(calendarObject instanceof TimeZone) {
            timeZones.add((TimeZone)calendarObject);
        }else if(calendarObject instanceof Event) {
            events.add((Event) calendarObject);
        }else if(calendarObject instanceof FreeBusy) {
            freeBusies.add((FreeBusy) calendarObject);
        }else if(calendarObject instanceof ToDo) {
            toDos.add((ToDo)calendarObject);
        }
    }


    public void validate() throws CalObjectException {
        if(null == productIdentifier) {
            throw new CalObjectException("Product Identifier cannot be null");
        }

        if(null == version) {
            throw new CalObjectException("Version cannot be null");
        }
    }

    public void writeCalStreamToFile() throws PropertyException, CalObjectException {
        validate();
        String fileLocation = "/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/" + productIdentifier.getContent();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation))) {
            ArrayList<String> lines = new ArrayList<>();

            lines.add("BEGIN:VCALENDAR");
            lines.add(Property.toCalStream(productIdentifier));
            lines.add(Property.toCalStream(version));
            lines.add(Property.toCalStream(calendarScale));
            lines.add(Property.toCalStream(color));

            for(TimeZone tz: timeZones) {
                lines.addAll(tz.getCalStream());
            }

            for(Event e: events) {
                lines.addAll(e.getCalStream());
            }

            for(ToDo toDo: toDos) {
                lines.addAll(toDo.getCalStream());
            }

            for(FreeBusy freeBusy: freeBusies) {
                lines.addAll(freeBusy.getCalStream());
            }

            lines.add("END:VCALENDAR");
            writeLines(lines, writer);

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void writeLines(ArrayList<String> lines, BufferedWriter writer) throws IOException {
        for(String line: lines) {
            for (int i = 0; i < line.length(); i++) {
                writer.write(line.substring(i, i + 1));
            }
            writer.newLine();
        }
    }
}
