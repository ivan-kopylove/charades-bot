package com.github.lazyf1sh.sandbox.runner;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UtilTest
{
    @Test
    public void run1()
    {
        String result = Util.buildUptime(new Date(1518477599000L), new Date(1603657197000L));
        assertEquals("985 days 20:59:58", result);
    }

    @Test
    public void run2()
    {
        String result = Util.buildUptime(new Date(1603646480000L), new Date(1603657655000L));
        assertEquals("03:06:15", result);
    }

    @Test
    public void run3()
    {
        String result = Util.buildUptime(new Date(1603481324000L), new Date(1603657845000L));
        assertEquals("2 days 01:02:01", result);
    }

    @Test
    public void run4()
    {
        Locale locale = new Locale("en");
        ResourceBundle exampleBundle = ResourceBundle.getBundle("i18n/Example", locale);

        assertEquals(exampleBundle.getString("abc"), "b");
    }


}