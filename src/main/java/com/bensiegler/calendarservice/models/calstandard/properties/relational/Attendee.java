package com.bensiegler.calendarservice.models.calstandard.properties.relational;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.*;
import com.bensiegler.calendarservice.models.calstandard.parameters.stringlist.DelegatedFrom;
import com.bensiegler.calendarservice.models.calstandard.parameters.stringlist.DelegatedTo;
import com.bensiegler.calendarservice.models.calstandard.parameters.stringlist.Member;
import com.bensiegler.calendarservice.models.calstandard.properties.Property;

import java.util.ArrayList;

public class Attendee extends Property {
    private CalendarUserType cuType;
    private Member memberOf;
    private ParticipantRole participantRole;
    private ParticipantStatus participantStatus;
    private DelegatedTo delegatedTo;
    private DelegatedFrom delegatedFrom;
    private SentBy sentBy;
    private CommonName commonName;
    private DirectoryReference directoryReference;
    private Language language;
    private String content;

    public Attendee() {
        super("ATTENDEE");
    }

    public Attendee(String content) {
        super("ATTENDEE");
        this.content = content;
    }

    public Attendee(String name, ArrayList<UnknownParameter> extras, CalendarUserType cuType,
                    Member memberOf, ParticipantRole participantRole, ParticipantStatus participantStatus,
                    DelegatedTo delegatedTo, DelegatedFrom delegatedFrom, SentBy sentBy, CommonName commonName,
                    DirectoryReference directoryReference, Language language, String content) {
        super("ATTENDEE", extras);
        this.cuType = cuType;
        this.memberOf = memberOf;
        this.participantRole = participantRole;
        this.participantStatus = participantStatus;
        this.delegatedTo = delegatedTo;
        this.delegatedFrom = delegatedFrom;
        this.sentBy = sentBy;
        this.commonName = commonName;
        this.directoryReference = directoryReference;
        this.language = language;
        this.content = content;
    }

    public CalendarUserType getCuType() {
        return cuType;
    }

    public void setCuType(CalendarUserType cuType) {
        this.cuType = cuType;
    }

    public Member getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(Member memberOf) {
        this.memberOf = memberOf;
    }

    public ParticipantRole getParticipantRole() {
        return participantRole;
    }

    public void setParticipantRole(ParticipantRole participantRole) {
        this.participantRole = participantRole;
    }

    public ParticipantStatus getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(ParticipantStatus participantStatus) {
        this.participantStatus = participantStatus;
    }

    public DelegatedTo getDelegatedTo() {
        return delegatedTo;
    }

    public void setDelegatedTo(DelegatedTo delegatedTo) {
        this.delegatedTo = delegatedTo;
    }

    public DelegatedFrom getDelegatedFrom() {
        return delegatedFrom;
    }

    public void setDelegatedFrom(DelegatedFrom delegatedFrom) {
        this.delegatedFrom = delegatedFrom;
    }

    public SentBy getSentBy() {
        return sentBy;
    }

    public void setSentBy(SentBy sentBy) {
        this.sentBy = sentBy;
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

    @Override
    public void setContentUsingString(String content) {
        this.content = content;
    }

    @Override
    public String retrieveContentAsString() {
        return content;
    }
}
