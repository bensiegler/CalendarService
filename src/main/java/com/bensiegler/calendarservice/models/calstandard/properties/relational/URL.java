package com.bensiegler.calendarservice.models.calstandard.properties.relational;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class URL extends Property {
    private URI content;

    public URL() {
        super("URL");
    }

    public URL(String content) throws URISyntaxException {
        super("URL");
        this.content = new URI(content);
    }

    public URL(ArrayList<UnknownParameter> extras, URI content) {
        super("URL", extras);
        this.content = content;
    }

    public URI getContent() {
        return content;
    }

    public void setContent(URI content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(content == null) {
            throw new PropertyException("Content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        try {
            this.content = new URI(content);
        }catch (URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public String retrieveContentAsString() {
        return content.toString();
    }
}
