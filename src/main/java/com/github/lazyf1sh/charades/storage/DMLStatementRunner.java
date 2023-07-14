package com.github.lazyf1sh.charades.storage;

import com.github.lazyf1sh.config.JdbcConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DMLStatementRunner
{
    private static final Logger LOGGER = LogManager.getLogger(DMLStatementRunner.class);

    public static int run(final String query, final PreparedStatementOptions preparedStatementOptions)
    {
        Objects.requireNonNull(query);
        Objects.requireNonNull(preparedStatementOptions);


        Connection connection = null;
        PreparedStatement statement = null;
        int result;
        try
        {
            connection = JdbcConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            PreparedStatementFiller.initPreparedStatement(preparedStatementOptions, statement);

            result = statement.executeUpdate();
            LOGGER.error("Number of rows affected: " + result);
            connection.commit();
        }
        catch (final SQLException ex)
        {
            LOGGER.error(ex);

            result = 0;
            try
            {
                connection.rollback();
            }
            catch (final SQLException throwables)
            {
                LOGGER.error(throwables);
            }
        }
        finally
        {
            try
            {
                connection.close();
                statement.close();
            }
            catch (final SQLException ex)
            {
                LOGGER.error(ex);
            }
        }
        return result;
    }
}
