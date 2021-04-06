package com.github.lazyf1sh.sandbox.runner;

import com.github.lazyf1sh.sandbox.business.TelegramApiInteraction;
import com.github.lazyf1sh.sandbox.domain.api.Update;
import com.github.lazyf1sh.sandbox.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.CallbackAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.MessageAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.SingleButtonAnswerResult;

public class SingleUpdateProcessor
{
    public static void processUpdate(Update update)
    {
        BaseAnswerResult baseAnswerResult = UpdateHandler.handle(update);

        if (baseAnswerResult instanceof CallbackAnswerResult)
        {
            CallbackAnswerResult callbackAnswerResult = (CallbackAnswerResult) baseAnswerResult;
            TelegramApiInteraction.answerCallbackQuery(callbackAnswerResult.getCallbackId(), callbackAnswerResult.getMsg());
        }

        if (baseAnswerResult instanceof SingleButtonAnswerResult)
        {
            SingleButtonAnswerResult singleButtonAnswerResult = (SingleButtonAnswerResult) baseAnswerResult;

            TelegramApiInteraction.sendSingleButton2(singleButtonAnswerResult.getChatId(), baseAnswerResult.getMsg(), singleButtonAnswerResult.getButtonText(), singleButtonAnswerResult.getCallbackName());
        }

        if (baseAnswerResult instanceof MessageAnswerResult)
        {
            MessageAnswerResult answerResult = (MessageAnswerResult) baseAnswerResult;
            TelegramApiInteraction.sendSingleMessage(answerResult.getMsg(), answerResult.getChatId());
        }
    }
}
