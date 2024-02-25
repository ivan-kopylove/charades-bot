package com.github.kopylove.charades.runner2;

import com.github.kopylove.charades.runner.Const;
import com.github.kopylove.charades.runner.LiquibaseRunner;
import com.github.kopylove.charades.runner.SingleUpdateProcessor;
import com.github.kopylove.charades.util.Util;
import com.github.kopylove.config.JdbcConnectionFactory;
import com.github.lazyf1sh.telegram.api.client.TelegramClient;
import com.github.lazyf1sh.telegram.api.domain.GetMe;
import com.github.lazyf1sh.telegram.api.domain.GetUpdate;
import com.github.lazyf1sh.telegram.api.domain.Update;
import liquibase.exception.LiquibaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static com.github.kopylove.charades.runner.Util.dumpObject;
import static com.github.lazyf1sh.telegram.api.client.TelegramClient.telegramClient;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class Runner2
{
    private static final Logger LOGGER = LogManager.getLogger(Runner2.class);

    private Runner2() {}

    public static void main(final String[] args) throws LiquibaseException, SQLException, URISyntaxException, IOException, InterruptedException
    {
        final ApplicationConfig appConfig = new ApplicationConfig();
        appConfig.setDatabaseUsername("sa");
        appConfig.setDatabasePassword("");
        appConfig.setDatabaseName("charades");
        appConfig.setTelegramApiKey(Util.getTelegramApiKey());

        LiquibaseRunner liquibaseRunner = new LiquibaseRunner(new JdbcConnectionFactory(appConfig));
        liquibaseRunner.runLiquibase();

        TelegramClient telegramClient = telegramClient(appConfig.getTelegramApiKey());


        GetMe me = telegramClient.getMe();

        dumpObject("", me);


        SingleUpdateProcessor singleUpdateProcessor = new SingleUpdateProcessor(telegramClient);

        final ScheduledExecutorService ses = newScheduledThreadPool(1);

        ses.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    final GetUpdate getUpdate = telegramClient.getUpdate();

                    final List<Update> updateResult = getUpdate.getResult();
                    for (final Update update : updateResult)
                    {
                        final long start = System.currentTimeMillis();
                        dumpObject("New update.", update);
                        singleUpdateProcessor.processUpdate(update);

                        LOGGER.info("Processed update in {} ms.", System.currentTimeMillis() - start);
                    }
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 5, SECONDS);
    }

    private static int findOutFirstUpdateIdToProcess(final GetUpdate getUpdate)
    {
        int result = 0;
        for (final Update update : getUpdate.getResult())
        {
            if (update.getMessage() != null)
            {
                final int date = update.getMessage()
                                       .getDate();
                if (date > result && date < (Const.BOT_START_TIME.getTime() / 1000))
                {
                    result = update.getUpdateId();
                }
            }
        }
        return result;
    }
}
