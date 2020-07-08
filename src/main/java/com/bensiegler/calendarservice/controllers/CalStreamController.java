package com.bensiegler.calendarservice.controllers;

import com.bensiegler.NumberGenerators;
import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.CalendarObjectMappingException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.repositories.CalendarRepo;
import com.bensiegler.calendarservice.services.CalendarStreamService;
import com.bensiegler.calendarservice.services.CalendarTestService;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class CalStreamController {

    @Autowired
    CalendarRepo calendarRepo;

    @Autowired
    CalendarStreamService streamService;

    @Autowired
    CalendarTestService calendarTestService;

    @GetMapping()
    public String getCalStream() {
        System.out.println("cal requested");
        String calStream = "";
        try (BufferedReader inputReader = new BufferedReader(
                new FileReader("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/64969478593"))) {

            String line;
            while ((line = inputReader.readLine()) != null ) {
                calStream += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calStream;
    }

    @GetMapping("/testparams")
    public String testParams() throws Exception {
        String name = "934247##testCal";
        streamService.generate_iCalendarStream(name);
        String calStream = "";
        try (BufferedReader inputReader = new BufferedReader(
                new FileReader("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/" + name))) {

            String line;
            while ((line = inputReader.readLine()) != null ) {
                calStream += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calStream;
    }
    public String getRandomCal() throws Exception {
        //make random changes. to database as to model people using this
        String name = calendarTestService.insertNewCalendar();
        streamService.generate_iCalendarStream(name);
        String calStream = "";
         try (BufferedReader inputReader = new BufferedReader(
                new FileReader("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/" + name))) {

            String line;
            while ((line = inputReader.readLine()) != null ) {
                calStream += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return calStream;

    }

    @GetMapping("seltest")
    public void seleniumTest() throws CalendarObjectMappingException, CalObjectException, PropertyException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Long randomNum = Long.parseLong(String.valueOf(NumberGenerators.randomNumberWithFixedLength(8)));
            WebDriver driver = null;
            try {
                driver = new HtmlUnitDriver();
                driver.get("https://icalendar.org/validator.html");
                WebElement validatorForm = driver.findElement(By.name("jform[ical_text]"));
                String calStream = getRandomCal();
                validatorForm.sendKeys(calStream);
                validatorForm.submit();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/bensiegler/Library/Mobile Documents/com~apple~CloudDocs/Documents/CodingShit/Tools/CalendarService/src/main/resources/calendarstreams/analysis/info", true))) {
                    int indexOfPRODID = calStream.indexOf("PRODID:") + +"PRODID:".length();
                    int endOfActualPRODID = indexOfPRODID + 6 + "##testCal".length();
                    String prodid = calStream.substring(indexOfPRODID, endOfActualPRODID);
                    writer.write(prodid);

                    try {
                        WebElement success = driver.findElement(By.xpath("/html/body/div[2]/div/div/section/div[5]/div[4]"));
                        writer.write(success.getText());
                    } catch (NoSuchElementException e) {
                        //do nothing
                    }

                    writer.newLine();
                    writer.newLine();
                } catch (IOException e) {
                    //should not happen
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != driver) {
                    driver.quit();
                }
            }
            Thread.sleep(5000);
        }
    }
}
