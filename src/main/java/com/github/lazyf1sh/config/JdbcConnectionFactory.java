package com.github.lazyf1sh.config;

import com.github.lazyf1sh.charades.runner.Config;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionFactory
{
    private static final Logger LOGGER = LogManager.getLogger(JdbcConnectionFactory.class);

    private static final String USER              = "sa";
    private static final String PASSWORD          = "";
    private static final String H2_DRIVER         = "org.h2.Driver";

    private static final ComboPooledDataSource POOLED_DATA_SOURCE = new ComboPooledDataSource();

    static
    {
        try
        {
            String connectionString = "jdbc:h2:~/" + Config.getProperty("db.name") + ";AUTO_SERVER=TRUE";

            POOLED_DATA_SOURCE.setDriverClass(H2_DRIVER);
            POOLED_DATA_SOURCE.setJdbcUrl(connectionString);
            POOLED_DATA_SOURCE.setUser(USER);
            POOLED_DATA_SOURCE.setPassword(PASSWORD);
            POOLED_DATA_SOURCE.setInitialPoolSize(2);
            POOLED_DATA_SOURCE.setAcquireIncrement(5);
            POOLED_DATA_SOURCE.setMaxPoolSize(20);
            POOLED_DATA_SOURCE.setMaxStatements(100);

        }
        catch (PropertyVetoException e)
        {
            // handle the exception
        }
    }

    public static Connection getConnection() throws SQLException
    {
        return POOLED_DATA_SOURCE.getConnection();
    }

    private JdbcConnectionFactory()
    {
    }
}
