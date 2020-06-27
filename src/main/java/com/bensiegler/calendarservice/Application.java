package com.bensiegler.calendarservice;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws PropertyException, CalObjectException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SpringApplication.run(Application.class, args);


//        Calendar calendar = new Calendar();
//        calendar.setProductIdentifier(new ProductIdentifier("64969478593"));
//        calendar.setMethod(new Method("PUBLISH"));
//
//        //event setup
//        Event event = new Event();
//        event.addProperty(new Duration(644343L));
//        event.addProperty(new Summary("WE ARE TESTING THE CAL STREAM!"));
//        event.addProperty(new UID("item 1"));
//        event.addProperty(new DateTimeStamp(System.currentTimeMillis()));
//        event.addProperty(new DateTimeStart(System.currentTimeMillis()));
//        event.addProperty(new RecurrenceRule("FREQ=DAILY;INTERVAL=1"));
//        ArrayList<String> categories = new ArrayList<>();
//        categories.add("TEST");
//        categories.add("WHOOP WHOOP");
//        event.addProperty(new Categories(categories));
//        event.setParent(calendar);
//        calendar.addCalObject(event);
//        calendar.writeCalStreamToFile();
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
