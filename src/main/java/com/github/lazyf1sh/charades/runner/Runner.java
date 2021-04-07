package com.github.lazyf1sh.charades.runner;


import com.github.lazyf1sh.charades.business.TelegramApiInteraction;
import com.github.lazyf1sh.charades.domain.telegram.api.GetMe;
import com.github.lazyf1sh.charades.domain.telegram.api.GetUpdate;
import com.github.lazyf1sh.charades.domain.telegram.api.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Runner
{
    private static final Logger LOGGER  = LogManager.getLogger(Runner.class);

    public static void main(String[] args)
    {
        try
        {
            run();
        }
        catch (Exception e)
        {
            LOGGER.error("Generic error", e);
        }
    }

    private static void run()
    {
        LOGGER.info("Bot start time is {}.", Const.BOT_START_TIME);
        GetMe getMe = GlobalClient.CLIENT
                .target(Util.buildUrl("getMe"))
                .request()
                .get(GetMe.class);

        GetUpdate getUpdate = TelegramApiInteraction.getUpdate(GlobalState.LAST_PROCESSED_ID.get());

        GlobalState.LAST_PROCESSED_ID.set(findOutFirstUpdateIdToProcess(getUpdate));

        while (true)
        {
            try
            {
                mainCycle();
                sleep(10, 20);
            }
            catch (Exception e)
            {
                LOGGER.error("Generic error.", e);
                sleep(10000, 20000);
                e.printStackTrace();
            }
        }
    }

    private static void mainCycle()
    {
        GetUpdate getUpdate = TelegramApiInteraction.getUpdate(GlobalState.LAST_PROCESSED_ID.get() + 1);

        List<Update> updateResult = getUpdate.getResult();
        for (Update update : updateResult)
        {
            long start = System.currentTimeMillis();
            Util.dumpObject("New update.", update);
            SingleUpdateProcessor.processUpdate(update);

            if(!GlobalState.LAST_PROCESSED_ID.compareAndSet(GlobalState.LAST_PROCESSED_ID.get(), update.getUpdateId()))
            {
                throw new RuntimeException("LAST_PROCESSED_ID");
            }

            LOGGER.info("Processed update in {} ms.", System.currentTimeMillis() - start);
        }
    }

    private static void sleep(int min, int max)
    {
        try
        {
            Thread.sleep(ThreadLocalRandom.current().nextInt(min, max));
        }
        catch (InterruptedException e)
        {
            LOGGER.error("InterruptedException", e);
        }
    }

    private static int findOutFirstUpdateIdToProcess(GetUpdate getUpdate)
    {
        int result = 0;
        for (Update update : getUpdate.getResult())
        {
            if (update.getMessage() != null)
            {
                int date = update.getMessage().getDate();
                if (date > result && date < (Const.BOT_START_TIME.getTime() / 1000))
                {
                    result = update.getUpdateId();
                }
            }
        }
        return result;
    }
}
