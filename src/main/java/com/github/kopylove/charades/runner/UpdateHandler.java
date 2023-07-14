package com.github.kopylove.charades.runner;

import com.github.kopylove.charades.domain.local.BaseAnswerResult;
import com.github.kopylove.charades.domain.local.NoActionResult;
import com.github.lazyf1sh.telegram.api.domain.CallbackQuery;
import com.github.lazyf1sh.telegram.api.domain.Message;
import com.github.lazyf1sh.telegram.api.domain.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class UpdateHandler
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
