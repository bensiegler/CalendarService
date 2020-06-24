package com.bensiegler.calendarservice.models.properties.descriptive;

import com.bensiegler.calendarservice.exceptions.PropertyException;
import com.bensiegler.calendarservice.models.parameters.string.Language;
import com.bensiegler.calendarservice.models.properties.Property;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.ibatis.annotations.Lang;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CategoriesTest {


    @Test
    public void testContentNullCheck() {
        Categories categories = new Categories();

        assertThrows(PropertyException.class, () -> {
            Property.toCalStream(categories);
        });

        Language language = new Language("EN-us");
        categories.setLanguageParam(language);

        assertThrows(PropertyException.class, () -> {
            Property.toCalStream(categories);
        });
    }

    @Test
    public void testCalStreamReturn() throws PropertyException {
        Categories categories = new Categories();

        ArrayList<String> content = new ArrayList<>();
        categories.setContent(content);
        content.add("APPOINTMENT");

        assertEquals("CATEGORIES:APPOINTMENT", Property.toCalStream(categories));

        content.add("EDUCATION");

        assertEquals("CATEGORIES:APPOINTMENT,EDUCATION", Property.toCalStream(categories));

        Language language = new Language("EN-us");
        categories.setLanguageParam(language);

        assertEquals("CATEGORIES;LANGUAGE=\"EN-us\":APPOINTMENT,EDUCATION", Property.toCalStream(categories));
    }
}
