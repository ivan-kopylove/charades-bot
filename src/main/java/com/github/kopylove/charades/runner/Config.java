package com.github.kopylove.charades.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public final class Config
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
        }
    }

    private Config() {}

    public static String getProperty(final String key)
    {
        final String property = PROPERTIES.getProperty(key);
        final String devKey = System.getenv("TELEGRAM_API_CHARADES_DEV");
        if (!devKey.isBlank())
        {
            return devKey;
        }
        final String prodKey = System.getenv("TELEGRAM_API_CHARADES_PROD");
        if (!prodKey.isBlank())
        {
            return devKey;
        }
        throw new RuntimeException("Invalid configuration");
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
