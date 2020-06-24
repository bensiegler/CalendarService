package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

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
