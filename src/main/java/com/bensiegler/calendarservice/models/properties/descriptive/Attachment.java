package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.misc.FormatType;
import com.bensiegler.calendarservice.models.parameters.string.Encoding;
import com.bensiegler.calendarservice.models.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.properties.Property;

//preliminary test complete
public class Attachment extends Property {
    private FormatType formatType;
    private Encoding encoding;
    private ValueType valueType;
    private String content;


    public Attachment() {
        super("ATTACH");
    }

    public FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
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
            throw new PropertyException("Invalid Attachment Property: content is null");
        }

        if((null == encoding && null != valueType) || (null != encoding && null == valueType)) {
            throw new PropertyException("Invalid Attachment Property: " +
                    "if either encoding or value type is specified so must the other");
        }
    }



    //TODO this is the start of being able to interpret someone else calendar stream. Currently not essential.
    public static Attachment parseInstanceFromString(String line) throws PropertyException {
        Attachment attachment = new Attachment();
        int locationOfAttach;
        //confirm correct property
        if((locationOfAttach = line.indexOf("ATTACH")) != 0) {
            throw new PropertyException("Non Attach property string passed to parser");
        }


        int locationOfDelimiter = locationOfAttach + "ATTACH".length() + 1;

        String firstDelimiter = line.substring(locationOfDelimiter, locationOfDelimiter + 1);

        if(firstDelimiter.equals(":")) {
            attachment.setContent(line.substring(locationOfDelimiter + 1));
        }else if(firstDelimiter.equals(";")) {

        }else {
            throw new PropertyException("Issue with line formatting near " + locationOfDelimiter + " in " + line);
        }
        return attachment;
    }

}
