package com.github.kopylove.config;

import com.github.kopylove.charades.runner2.ApplicationConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionFactory
{
    private static final Logger                LOGGER             = LogManager.getLogger(JdbcConnectionFactory.class);
    private static final String                H2_DRIVER          = "org.h2.Driver";
    private final        ComboPooledDataSource POOLED_DATA_SOURCE = new ComboPooledDataSource();

    public JdbcConnectionFactory(final ApplicationConfig applicationConfig)
    {
        try
        {
            final String connectionString = "jdbc:h2:~/" + applicationConfig.getDatabaseName() + ";AUTO_SERVER=TRUE";

            POOLED_DATA_SOURCE.setDriverClass(H2_DRIVER);
            POOLED_DATA_SOURCE.setJdbcUrl(connectionString);
            POOLED_DATA_SOURCE.setUser(applicationConfig.getDatabaseUsername());
            POOLED_DATA_SOURCE.setPassword(applicationConfig.getDatabasePassword());
            POOLED_DATA_SOURCE.setInitialPoolSize(2);
            POOLED_DATA_SOURCE.setAcquireIncrement(5);
            POOLED_DATA_SOURCE.setMaxPoolSize(20);
            POOLED_DATA_SOURCE.setMaxStatements(100);
        }
        catch (final PropertyVetoException e)
        {
            LOGGER.error("PropertyVetoException", e);
        }
    }

    public Connection getConnection() throws SQLException
    {
        return POOLED_DATA_SOURCE.getConnection();
    }
}
