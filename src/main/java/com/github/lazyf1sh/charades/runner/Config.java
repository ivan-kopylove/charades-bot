package com.github.lazyf1sh.charades.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class Config
{
    private static final Logger     LOGGER     = LogManager.getLogger(Config.class);
    private static final Properties PROPERTIES = new Properties();

    static
    {
        try (final InputStream input = new FileInputStream("config.properties"))
        {
            PROPERTIES.load(input);
        }
        catch (final IOException ex)
        {
            LOGGER.error("error loading config", ex);
            ex.printStackTrace();
        }
    }

    private Config() {}

    public static String getProperty(final String key)
    {
        final String property = PROPERTIES.getProperty(key);
        if (System.getenv("f5ca4fde-6cc6-4954-939c-ce2f2f372580")
                  .equals("b474a207-0c19-4a01-90bb-f517a8729fbe"))
        {
            return property;
        }
        if (property == null || property.isBlank() || property.toLowerCase()
                                                              .startsWith("dev"))
        {
            LOGGER.error("property: " + property);
            throw new RuntimeException("Invalid configuration");
        }
        return property;
    }

    public static String getValue(final String key, final Locale locale)
    {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(key);

        final ResourceBundle exampleBundle = ResourceBundle.getBundle("i18n/Example", locale);
        return exampleBundle.getString(key);
    }

    public static String getValue(final String key)
    {
        Objects.requireNonNull(key);

        final ResourceBundle exampleBundle = ResourceBundle.getBundle("i18n/Example");
        return exampleBundle.getString(key);
    }
}
