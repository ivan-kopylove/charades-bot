package com.github.lazyf1sh.sandbox.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config
{
    private static final Logger     LOGGER     = LogManager.getLogger(Config.class);
    public static final  Properties PROPERTIES = new Properties();

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
