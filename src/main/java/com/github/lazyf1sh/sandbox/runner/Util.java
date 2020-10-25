package com.github.lazyf1sh.sandbox.runner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger       LOGGER        = LogManager.getLogger(Util.class);



    public static void dumpObject(String message, Object object)
    {
        String objectDump;
        try
        {
            objectDump = OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            objectDump = e.getMessage();
            e.printStackTrace();
        }

        LOGGER.info(message + ". " + objectDump);
    }


    public static String buildUrl(String method)
    {
        return Const.BASE_URL + Const.BOT_PREFIX + Config.PROPERTIES.getProperty("bot.api.key") + "/" + method;
    }

    public static String tryMultiplingBy2(String text)
    {
        String result = text;
        try
        {
            Long aLong = Long.valueOf(text);
            result = String.valueOf(aLong * 2);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}