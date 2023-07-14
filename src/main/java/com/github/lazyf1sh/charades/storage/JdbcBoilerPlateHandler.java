package com.github.lazyf1sh.charades.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcBoilerPlateHandler
{
    private static final Logger LOGGER = LogManager.getLogger(JdbcBoilerPlateHandler.class);

    private JdbcBoilerPlateHandler() {}

    public static void rollBackConnection(final Connection connection)
    {
        try
        {
            if (connection != null)
            {
                connection.rollback();
            }
        }
        catch (final SQLException sqlException)
        {
            LOGGER.error(sqlException);
        }
    }

    public static void handleFinally(final Connection connection, final Statement statement, final ResultSet resultSet)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
            if (statement != null)
            {
                statement.close();
            }
            if (resultSet != null)
            {
                resultSet.close();
            }
        }
        catch (final SQLException exception)
        {
            LOGGER.error(exception);
            exception.printStackTrace();
        }
    }
}
