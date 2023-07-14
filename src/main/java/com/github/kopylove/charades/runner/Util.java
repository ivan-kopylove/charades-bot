package com.github.kopylove.charades.runner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class Util
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger       LOGGER        = LogManager.getLogger(Util.class);

    private Util() {}

    @NotNull
    public static String buildUptime(final Date startTime, final Date endTime)
    {
        final long duration = endTime.getTime() - startTime.getTime();
        final long seconds = duration / 1000;
        final int day = (int) TimeUnit.SECONDS.toDays(seconds);
        final long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        final long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        final long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return String.format("%s%02d:%02d:%02d", day > 0 ? day + " days " : "", hours, minute, second);
    }

    public static void dumpObject(final String message, final Object object)
    {
        String objectDump;
        try
        {
            objectDump = OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (final JsonProcessingException e)
        {
            LOGGER.error("JsonProcessingException", e);
            objectDump = e.getMessage();
            e.printStackTrace();
        }

        LOGGER.info(message + " " + objectDump);
    }
}