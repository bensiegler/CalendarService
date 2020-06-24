package com.bensiegler.calendarservice;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calendarobjects.Calendar;
import com.bensiegler.calendarservice.models.calendarobjects.Event;
import com.bensiegler.calendarservice.models.properties.Property;
import com.bensiegler.calendarservice.models.properties.changemanagement.DateTimeStamp;
import com.bensiegler.calendarservice.models.properties.descriptive.Categories;
import com.bensiegler.calendarservice.models.properties.descriptive.ProductIdentifier;
import com.bensiegler.calendarservice.models.properties.descriptive.Summary;
import com.bensiegler.calendarservice.models.properties.relational.UID;
import com.bensiegler.calendarservice.models.properties.temporal.misc.Duration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws PropertyException, CalObjectException {
//        SpringApplication.run(Application.class, args);

        Calendar calendar = new Calendar();
        calendar.setProductIdentifier(new ProductIdentifier("64969478593"));

        //event setup
        Event event = new Event();
        event.addProperty(new Duration(644343L));
        event.addProperty(new Summary("WE ARE TESTING THE CAL STREAM!"));
        event.addProperty(new UID("item 1"));
        event.addProperty(new DateTimeStamp(System.currentTimeMillis()));
        ArrayList<String> categories = new ArrayList<>();
        categories.add("TEST");
        categories.add("WHOOP WHOOP");
        event.addProperty(new Categories(categories));
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
