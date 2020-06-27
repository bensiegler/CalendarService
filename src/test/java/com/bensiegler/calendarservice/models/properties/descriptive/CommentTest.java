package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.ParameterException;
import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.AlternateRepresentation;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.Language;
import com.bensiegler.calendarservice.models.calstandard.parameters.string.UnknownParameter;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Comment;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommentTest {

    @Test
    public void testCalStream() throws PropertyException, ParameterException {
        Comment comment = new Comment();
        comment.setContent("hellooooooooooooooo");
        assertEquals("COMMENT:hellooooooooooooooo",
                comment.toCalStream(comment));

        Language language = new Language("EN-us");
        comment.setLanguage(language);
        assertEquals("COMMENT;LANGUAGE=\"EN-us\":hellooooooooooooooo",
                comment.toCalStream(comment));

        AlternateRepresentation alternateRepresentation = new AlternateRepresentation("url");
        comment.setAlternateRepresentation(alternateRepresentation);
        assertEquals("COMMENT;ALTREP=\"url\";LANGUAGE=\"EN-us\":hellooooooooooooooo",
                comment.toCalStream(comment));

        ArrayList<UnknownParameter> unknownParameters = new ArrayList<>();
        unknownParameters.add(new UnknownParameter("x", "y"));
        unknownParameters.add(new UnknownParameter("a", "b"));
        comment.setExtras(unknownParameters);

        assertEquals("COMMENT;x=\"y\";a=\"b\";ALTREP=\"url\";LANGUAGE=\"EN-us\":hellooooooooooooooo",
                comment.toCalStream(comment));

    }

    @Test
    public void testContentNullCheck() {
        Comment comment = new Comment();
        assertThrows(PropertyException.class, () -> {
            comment.toCalStream(comment);
        });
    }
}
