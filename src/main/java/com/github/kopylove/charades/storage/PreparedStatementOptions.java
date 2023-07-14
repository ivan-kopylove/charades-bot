package com.github.kopylove.charades.storage;

import java.util.HashMap;
import java.util.Map;

public class PreparedStatementOptions
{
    private Map<Integer, Long>   longs;
    private Map<Integer, String> strings;

    public Map<Integer, String> getStrings()
    {
        return strings;
    }

    public void setString(final int argumentNumber, final String value)
    {
        if (argumentNumber > 0)
        {
            if (strings == null)
            {
                strings = new HashMap<>();
            }
            strings.put(argumentNumber, value);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public Map<Integer, Long> getLongs()
    {
        return longs;
    }

    public void setLong(final int argumentNumber, final Long value)
    {
        if (argumentNumber > 0)
        {
            if (longs == null)
            {
                longs = new HashMap<>();
            }
            longs.put(argumentNumber, value);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
