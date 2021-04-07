package com.github.lazyf1sh.sandbox.runner;

import com.github.lazyf1sh.sandbox.domain.api.CallbackQuery;
import com.github.lazyf1sh.sandbox.domain.api.Message;
import com.github.lazyf1sh.sandbox.domain.api.Update;
import com.github.lazyf1sh.sandbox.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.NoActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateHandler
{
    private static final Logger LOGGER = LogManager.getLogger(UpdateHandler.class);

    public static BaseAnswerResult handle(Update update)
    {
        Message message = update.getMessage();
        if (message != null)
        {
            BaseAnswerResult messageResult = MessageHandler.handleMessage(message);
            if (messageResult != null)
            {
                return messageResult;
            }
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null)
        {
            BaseAnswerResult callBackResult = CallbackHandler.handleCallback(callbackQuery);
            if (callBackResult != null)
            {
                return callBackResult;
            }
        }

        return new NoActionResult();
    }






}
