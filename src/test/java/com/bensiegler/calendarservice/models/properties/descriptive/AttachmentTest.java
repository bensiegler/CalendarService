package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.parameters.misc.FormatType;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Encoding;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.ValueType;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Attachment;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AttachmentTest {
    FormatType formatType = new FormatType("word", "doc");
    Encoding encoding = new Encoding("BASE-64");
    ValueType valueType = new ValueType("binary");
    ArrayList<UnknownParameter> extras = new ArrayList<>();

    @Test
    public void testContentNullCheck()  {
        Attachment attachment = new Attachment();
        assertThrows(PropertyException.class, () -> {
            attachment.toCalStream(attachment);
        });
    }

    @Test
    public void testValueAndEncodingTypeRestraint() {
        Attachment attachment = new Attachment();
        attachment.setContent("content");
        attachment.setEncoding(encoding);
        assertThrows(PropertyException.class, () -> {
            attachment.toCalStream(attachment);
        });
        attachment.setValueType(valueType);
    }

    @Test
    public void testCalStreamReturn() throws PropertyException, ParameterException {
        Attachment attachment = new Attachment();

        //just with content
        attachment.setContent("content");
        assertEquals("ATTACH:content", attachment.toCalStream(attachment));

        //with content, encoding and value type
        attachment.setEncoding(encoding);
        attachment.setValueType(valueType);
        assertEquals("ATTACH;ENCODING=\"BASE-64\";VALUE=\"binary\":content",
                attachment.toCalStream(attachment));

        //with content, encoding, value type and format type
        attachment.setFormatType(formatType);
        assertEquals("ATTACH;FMTTYPE=word/doc;ENCODING=\"BASE-64\";VALUE=\"binary\":content",
                attachment.toCalStream(attachment));

        //with content, encoding, value type, format type and two unknown parameters
        extras.add(new UnknownParameter("x", "y"));
        extras.add(new UnknownParameter("a", "b"));
        attachment.setExtras(extras);
        assertEquals("ATTACH;x=\"y\";a=\"b\";FMTTYPE=word/doc;ENCODING=\"BASE-64\";VALUE=\"binary\":content"
                , attachment.toCalStream(attachment));


        //with content and only format type (not empty array list for no unknown parameters)
        attachment.setExtras(new ArrayList<>());
        attachment.setEncoding(null);
        attachment.setValueType(null);
        assertEquals("ATTACH;FMTTYPE=word/doc:content",
                attachment.toCalStream(attachment));

    }
}
