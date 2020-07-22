package com.bensiegler.calendarservice.models.calstandard.properties.relational;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.RelationshipType;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class RelatedTo extends Property {
    private RelationshipType relatedTo;
    private String content;

    public RelatedTo() {
        super("RELATED-TO");
    }

    public RelatedTo(String content) {
        super("RELATED-TO");
        this.content = content;
    }

    public RelatedTo(ArrayList<UnknownParameter> extras, RelationshipType relatedTo, String content) {
        super("RELATED-TO", extras);
        this.relatedTo = relatedTo;
        this.content = content;
    }

    public RelationshipType getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(RelationshipType relatedTo) {
        this.relatedTo = relatedTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void validate() throws PropertyException {
        if(null == content) {
            throw new PropertyException("content cannot be null");
        }
    }

    @Override
    public void setContentUsingString(String content) {
        this.content = content;
    }

    @Override
    public String retrieveContentAsString() {
        return content;
    }
}
