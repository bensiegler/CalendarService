package com.bensiegler.calendarservice.models.calstandard.properties.relational;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.CommonName;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.DirectoryReference;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.SentBy;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

public class Organizer extends Property {
    private CommonName commonName;
    private DirectoryReference directoryReference;
    private SentBy sentBy;
    private Language language;
    private String content;

    public Organizer() {
        super("ORGANIZER");
    }

    public CommonName getCommonName() {
        return commonName;
    }

    public void setCommonName(CommonName commonName) {
        this.commonName = commonName;
    }

    public DirectoryReference getDirectoryReference() {
        return directoryReference;
    }

    public void setDirectoryReference(DirectoryReference directoryReference) {
        this.directoryReference = directoryReference;
    }

    public SentBy getSentBy() {
        return sentBy;
    }

    public void setSentBy(SentBy sentBy) {
        this.sentBy = sentBy;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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
            throw new PropertyException("Content cannot be null");
        }
    }
}
