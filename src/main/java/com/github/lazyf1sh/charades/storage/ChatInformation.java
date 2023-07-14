package com.github.lazyf1sh.charades.storage;

import com.github.lazyf1sh.charades.domain.local.Language;
import com.github.lazyf1sh.charades.runner.LangUtil;
import com.github.lazyf1sh.config.JdbcConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ChatInformation
{
    private static final Logger LOGGER = LogManager.getLogger(ChatInformation.class);

    private ChatInformation() {}

    public static void saveOrUpdateChatLanguage(final long telegramChatId, final String language)
    {
        if (loadIfChatPersisted(telegramChatId))
        {
            final String query = "UPDATE CHAT SET LANGUAGE = ? WHERE TELEGRAM_CHAT_ID = ?";
            final PreparedStatementOptions preparedStatementOptions = new PreparedStatementOptions();
            preparedStatementOptions.setString(1, language);
            preparedStatementOptions.setLong(2, telegramChatId);

            DMLStatementRunner.run(query, preparedStatementOptions);
        }
        else
        {
            final String query = "INSERT INTO CHAT VALUES(null, ?, ?)";
            final PreparedStatementOptions preparedStatementOptions = new PreparedStatementOptions();
            preparedStatementOptions.setLong(1, telegramChatId);
            preparedStatementOptions.setString(2, language);

            DMLStatementRunner.run(query, preparedStatementOptions);
        }
    }

    public static Language getChatLanguage(final long chatId)
    {
        String result = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            conn = JdbcConnectionFactory.getConnection();
            statement = conn.prepareStatement("SELECT LANGUAGE FROM CHAT WHERE TELEGRAM_CHAT_ID = ?");
            statement.setLong(1, chatId);

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                result = resultSet.getString("LANGUAGE");
            }

            conn.commit();
        }
        catch (final SQLException ex)
        {
            LOGGER.error(ex);

            JdbcBoilerPlateHandler.rollBackConnection(conn);
        }
        finally
        {
            JdbcBoilerPlateHandler.handleFinally(conn, statement, resultSet);
        }
        return LangUtil.convert(result);
    }

    public static boolean loadIfChatPersisted(final long telegramChatId)
    {
        boolean result;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try
        {
            conn = JdbcConnectionFactory.getConnection();
            st = conn.prepareStatement("SELECT * FROM CHAT WHERE TELEGRAM_CHAT_ID = ?");
            st.setLong(1, telegramChatId);
            resultSet = st.executeQuery();
            result = resultSet.next();
        }
        catch (final SQLException ex)
        {
            LOGGER.error(ex);
            result = false;
            JdbcBoilerPlateHandler.rollBackConnection(conn);
        }
        finally
        {
            JdbcBoilerPlateHandler.handleFinally(conn, st, resultSet);
        }
        return result;
    }

    public static void main(final String[] args)
    {
        final String s = "CREATE TABLE CHAT(ID IDENTITY PRIMARY KEY, TELEGRAM_CHAT_ID BIGINT UNIQUE, LANGUAGE VARCHAR(5));";
        final String s1 = "INSERT INTO CHAT VALUES (null, 123, 'ru')";


        final Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
