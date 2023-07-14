package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.charades.domain.local.NoActionResult;
import com.github.lazyf1sh.charades.domain.telegram.api.CallbackQuery;
import com.github.lazyf1sh.charades.domain.telegram.api.Message;
import com.github.lazyf1sh.charades.domain.telegram.api.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateHandler
{
    private static final Logger LOGGER = LogManager.getLogger(UpdateHandler.class);

    private UpdateHandler() {}

    public static BaseAnswerResult handle(final Update update)
    {
        final Message message = update.getMessage();
        if (message != null)
        {
            return MessageHandler.handleMessage(message);
        }

        final CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null)
        {
            return CallbackHandler.handleCallback(callbackQuery);
        }

        LOGGER.error("message & callbackQuery are null");
        return NoActionResult.NO_ACTION_RESULT;
    }
}
