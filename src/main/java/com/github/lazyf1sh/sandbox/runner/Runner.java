package com.github.lazyf1sh.sandbox.runner;


import com.github.lazyf1sh.sandbox.business.TelegramApiInteraction;
import com.github.lazyf1sh.sandbox.domain.api.GetMe;
import com.github.lazyf1sh.sandbox.domain.api.GetUpdate;
import com.github.lazyf1sh.sandbox.domain.api.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Runner
{
    private static final Logger LOGGER  = LogManager.getLogger(Runner.class);
    private static final String PRIVATE = "private";

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

        GetUpdate getUpdate = TelegramApiInteraction.getUpdate(GlobalState.LAST_PROCESSED_ID);

        GlobalState.LAST_PROCESSED_ID = findOutFirstUpdateIdToProcess(getUpdate);

        while (true)
        {
            try
            {
                mainCycle();
                sleep(100, 200);
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

        GetUpdate getUpdate = TelegramApiInteraction.getUpdate(GlobalState.LAST_PROCESSED_ID);

        List<Update> updateResult = getUpdate.getResult();
        for (Update update : updateResult)
        {
            Util.dumpObject("New update", update);
            SingleUpdateProcessor.processUpdate(update);
            GlobalState.LAST_PROCESSED_ID = update.getUpdateId();
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
