package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.calstandard.properties.descriptive.Classification;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassificationTest {

    @Test
    public void testCalStream() throws PropertyException {
        Classification classification = new Classification();
        classification.setContent("PUBLIC");

        assertEquals("CLASS:PUBLIC", classification.toCalStream(classification));
    }

    @Test
    public void testContentNullCheck() {
        Classification classification = new Classification();

        assertThrows(PropertyException.class, () -> {
            classification.toCalStream(classification);
        });
    }
}
