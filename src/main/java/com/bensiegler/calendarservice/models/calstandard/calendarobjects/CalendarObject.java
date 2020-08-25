package com.bensiegler.calendarservice.models.calstandard.calendarobjects;

import com.bensiegler.calendarservice.exceptions.CalObjectException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.UnknownProperty;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.ProductIdentifier;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;



@Component
public abstract class CalendarObject {

    private ProductIdentifier productIdentifier;

    private ArrayList<UnknownProperty> unknownProperties = new ArrayList<>();

    public CalendarObject() {
    }

    public CalendarObject(ArrayList<UnknownProperty> unknownProperties) {
        this.unknownProperties = unknownProperties;
    }

    public void addNewUnknownProperty(UnknownProperty unknownProperty) {
        unknownProperties.add(unknownProperty);
    }

    public ArrayList<UnknownProperty> getUnknownProperties() {
        return unknownProperties;
    }

    public ProductIdentifier getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(ProductIdentifier productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public abstract String retrieveCalStream() throws IllegalAccessException, PropertyException, CalObjectException, IOException;

    public abstract void validate() throws CalObjectException;


}
