package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.config.JdbcConnectionFactory;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.SQLException;

public class LiquibaseRunner
{

    public static void run() throws SQLException, LiquibaseException
    {

            java.sql.Connection connection = JdbcConnectionFactory.getConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibaseRunner = new liquibase.Liquibase("databaseChangeLog.xml", new ClassLoaderResourceAccessor(), database);
            liquibaseRunner.update(new Contexts(), new LabelExpression());

    }
}
