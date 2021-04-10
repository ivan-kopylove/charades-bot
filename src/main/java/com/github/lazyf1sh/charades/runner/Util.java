package com.github.lazyf1sh.charades.runner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lazyf1sh.charades.domain.local.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Util
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger       LOGGER        = LogManager.getLogger(Util.class);




    @NotNull
    public static String buildUptime(Date startTime, Date endTime)
    {
        long duration = endTime.getTime() - startTime.getTime();
        long seconds = duration / 1000;
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return String.format("%s%02d:%02d:%02d", day > 0 ? day + " days " : "", hours, minute, second);
    }


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

        LOGGER.info(message + " " + objectDump);
    }


    public static String buildUrl(String method)
    {
        return Const.TELEGRAM_API_BASE_URL + Const.BOT_PREFIX + Config.getProperty("bot.api.key") + "/" + method;
    }
}