package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.business.TelegramApiInteraction;
import com.github.lazyf1sh.charades.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.charades.domain.local.CallbackAnswerResult;
import com.github.lazyf1sh.charades.domain.local.MessageAnswerResult;
import com.github.lazyf1sh.charades.domain.local.SingleButtonAnswerResult;
import com.github.lazyf1sh.charades.domain.telegram.api.Update;

public class SingleUpdateProcessor
{
    private SingleUpdateProcessor() {}

    public static void processUpdate(final Update update)
    {
        final BaseAnswerResult baseAnswerResult = UpdateHandler.handle(update);

        if (baseAnswerResult instanceof CallbackAnswerResult)
        {
            final CallbackAnswerResult callbackAnswerResult = (CallbackAnswerResult) baseAnswerResult;
            TelegramApiInteraction.answerCallbackQuery(callbackAnswerResult.getCallbackId(),
                                                       callbackAnswerResult.getMsg());
        }

        if (baseAnswerResult instanceof SingleButtonAnswerResult)
        {
            final SingleButtonAnswerResult singleButtonAnswerResult = (SingleButtonAnswerResult) baseAnswerResult;

            TelegramApiInteraction.sendSingleButton(singleButtonAnswerResult.getChatId(),
                                                    baseAnswerResult.getMsg(),
                                                    singleButtonAnswerResult.getButtonText(),
                                                    singleButtonAnswerResult.getCallbackName());
        }

        if (baseAnswerResult instanceof MessageAnswerResult)
        {
            final MessageAnswerResult answerResult = (MessageAnswerResult) baseAnswerResult;
            TelegramApiInteraction.sendSingleMessage(answerResult.getMsg(), answerResult.getChatId());
        }
    }
}
