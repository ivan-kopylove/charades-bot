package com.github.lazyf1sh.charades.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class PreparedStatementFiller
{
    public static void initPreparedStatement(PreparedStatementOptions preparedStatementOptions, PreparedStatement statement) throws SQLException
    {
        setLongs(preparedStatementOptions, statement);
        setStrings(preparedStatementOptions, statement);
    }

    private static void setLongs(PreparedStatementOptions preparedStatementOptions, PreparedStatement statement) throws SQLException
    {
        if (preparedStatementOptions.getLongs() != null)
        {
            for (Map.Entry<Integer, Long> longEntry : preparedStatementOptions.getLongs().entrySet())
            {
                statement.setLong(longEntry.getKey(), longEntry.getValue());
            }
        }
    }

    private static void setStrings(PreparedStatementOptions preparedStatementOptions, PreparedStatement statement) throws SQLException
    {
        if (preparedStatementOptions.getStrings() != null)
        {
            for (Map.Entry<Integer, String> longEntry : preparedStatementOptions.getStrings().entrySet())
            {
                statement.setString(longEntry.getKey(), longEntry.getValue());
            }
        }
    }

}
