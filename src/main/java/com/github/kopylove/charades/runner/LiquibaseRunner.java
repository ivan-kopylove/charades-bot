package com.github.kopylove.charades.runner;

import com.github.kopylove.config.JdbcConnectionFactory;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.SQLException;

public final class LiquibaseRunner
{
    private final JdbcConnectionFactory jdbcConnectionFactory;

    public LiquibaseRunner(JdbcConnectionFactory jdbcConnectionFactory)
    {
        this.jdbcConnectionFactory = jdbcConnectionFactory;
    }

    public void runLiquibase() throws SQLException, LiquibaseException
    {
        JdbcConnection connection = new JdbcConnection(jdbcConnectionFactory.getConnection());

        Database database = DatabaseFactory.getInstance()
                                           .findCorrectDatabaseImplementation(connection);
        try (final Liquibase liquibaseRunner = new Liquibase("databaseChangeLog.xml",
                                                             new ClassLoaderResourceAccessor(),
                                                             database))
        {
            liquibaseRunner.update(new Contexts(), new LabelExpression());
        }
    }
}