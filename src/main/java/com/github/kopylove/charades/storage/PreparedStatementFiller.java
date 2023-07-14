package com.github.kopylove.charades.storage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public final class PreparedStatementFiller
{
    private PreparedStatementFiller() {}

    public static void initPreparedStatement(final PreparedStatementOptions preparedStatementOptions, final PreparedStatement statement) throws SQLException
    {
        setLongs(preparedStatementOptions, statement);
        setStrings(preparedStatementOptions, statement);
    }

    private static void setLongs(final PreparedStatementOptions preparedStatementOptions, final PreparedStatement statement) throws SQLException
    {
        if (preparedStatementOptions.getLongs() != null)
        {
            for (final Map.Entry<Integer, Long> longEntry : preparedStatementOptions.getLongs()
                                                                                    .entrySet())
            {
                statement.setLong(longEntry.getKey(), longEntry.getValue());
            }
        }
    }

    private static void setStrings(final PreparedStatementOptions preparedStatementOptions, final PreparedStatement statement) throws SQLException
    {
        if (preparedStatementOptions.getStrings() != null)
        {
            for (final Map.Entry<Integer, String> longEntry : preparedStatementOptions.getStrings()
                                                                                      .entrySet())
            {
                statement.setString(longEntry.getKey(), longEntry.getValue());
            }
        }
    }
}
