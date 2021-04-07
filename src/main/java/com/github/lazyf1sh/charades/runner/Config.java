package com.github.lazyf1sh.charades.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public static String getBotApiKey()
    {
        String property = PROPERTIES.getProperty("bot.api.key");
        if (property == null)
        {
            new RuntimeException("Invalid configuration");
        }
        return property;
    }

    public static String getValue(Locale locale, String key)
    {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(key);

        ResourceBundle exampleBundle = ResourceBundle.getBundle("i18n/Example", locale);
        return exampleBundle.getString(key);
    }

    public static String getValue(String key)
    {
        Objects.requireNonNull(key);

        ResourceBundle exampleBundle = ResourceBundle.getBundle("i18n/Example");
        return exampleBundle.getString(key);
    }


    static
    {
        try (InputStream input = new FileInputStream("config.properties"))
        {
            PROPERTIES.load(input);
        }
        catch (IOException ex)
        {
            LOGGER.error("error loading config", ex);
            ex.printStackTrace();
        }
    }
}
