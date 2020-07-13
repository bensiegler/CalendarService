package com.bensiegler.calendarservice;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.Event;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.StandardOrDaylight;
import com.bensiegler.calendarservice.models.calstandard.calendarobjects.tz.TimeZone;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.DateTimeStamp;
import com.bensiegler.calendarservice.models.calstandard.properties.changemanagement.LastModified;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Categories;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Method;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.ProductIdentifier;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Summary;
import com.bensiegler.calendarservice.models.calstandard.properties.relational.UID;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.dt.DateTimeStart;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.Duration;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.misc.RecurrenceRule;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZIdentifierProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZOffsetFrom;
import com.bensiegler.calendarservice.models.calstandard.properties.temporal.timezone.TZOffsetTo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws PropertyException, CalObjectException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SpringApplication.run(Application.class, args);

        Calendar calendar = new Calendar();
        calendar.setProductIdentifier(new ProductIdentifier("64969478593"));
        calendar.setMethod(new Method("PUBLISH"));

        TimeZone timeZone = new TimeZone();
        //idCountry n idCity
        timeZone.setIdCity("more fiction");
        timeZone.setIdCountry("fiction");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        timeZone.setLastModified(cal);

        StandardOrDaylight daylight = new StandardOrDaylight();
        java.util.Calendar jCal = java.util.Calendar.getInstance();
        jCal.clear();
        jCal.set(2007, 3, 11, 2, 0, 0);

        daylight.setDateTimeStart(jCal.getTimeInMillis());
        daylight.setRecurrenceRule("FREQ=YEARLY;BYMONTH=3;BYDAY=2SU");

        daylight.setTzOffsetFrom(-5);

        TZOffsetTo tzOffsetTo = new TZOffsetTo();
        daylight.setTzOffsetTo(-4);
        ArrayList<StandardOrDaylight> arr = new ArrayList<>();
        arr.add(daylight);

        timeZone.setStandardOrDaylights(arr);
        calendar.addCalObject(timeZone);

//
//        ToDo toDo = new ToDo();
//        toDo.setDateTimeStamp(new DateTimeStamp(System.currentTimeMillis()));
//        toDo.setSummary(new Summary("Lots of stuff to summarize"));
//        toDo.setUid(new UID("57894309458"));
//        toDo.setDue(new DateTimeDue(System.currentTimeMillis() + 158495));
//        calendar.addCalObject(toDo);
////
//        //event setup
        Event event = new Event();
        event.addProperty(new Duration(644343L));
        event.addProperty(new Summary("WE ARE TESTING THE CAL STREAM!"));
        event.addProperty(new UID("item 1"));
        event.addProperty(new DateTimeStamp(System.currentTimeMillis()));
        event.addProperty(new DateTimeStart(System.currentTimeMillis()));
        event.addProperty(new RecurrenceRule("FREQ=DAILY;INTERVAL=1"));
        ArrayList<String> categories = new ArrayList<>();
        categories.add("TEST");
        categories.add("WHOOP WHOOP");
        event.addProperty(new Categories(categories));
        event.setParent(calendar);
        calendar.addCalObject(event);
        calendar.writeCalStreamToFile();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


//    @Override
//    protected SpringApplicationBuilder configure(
//            SpringApplicationBuilder builder) {
//        return builder.sources(Application.class);
//    }
}
