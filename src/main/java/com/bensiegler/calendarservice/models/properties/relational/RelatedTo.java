package com.bensiegler.calendarservice.models.properties.relational;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.string.RelationshipType;
import com.bensiegler.calendarservice.models.properties.Property;

public class RelatedTo extends Property {
    private RelationshipType relatedTo;
    private String content;

    public RelatedTo() {
        super("RELATED-TO");
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

    }
}