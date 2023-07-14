package com.github.kopylove.charades.runner;

import com.github.kopylove.charades.domain.local.BaseAnswerResult;
import com.github.kopylove.charades.domain.local.CallbackAnswerResult;
import com.github.kopylove.charades.domain.local.MessageAnswerResult;
import com.github.kopylove.charades.domain.local.SingleButtonAnswerResult;
import com.github.lazyf1sh.telegram.api.client.TelegramClient;
import com.github.lazyf1sh.telegram.api.domain.Update;

public final class SingleUpdateProcessor
{
    private final TelegramClient telegramClient;

    public SingleUpdateProcessor(TelegramClient telegramClient) {this.telegramClient = telegramClient;}

    public void processUpdate(final Update update)
    {
        final BaseAnswerResult baseAnswerResult = UpdateHandler.handle(update);

        if (baseAnswerResult instanceof CallbackAnswerResult)
        {
            final CallbackAnswerResult callbackAnswerResult = (CallbackAnswerResult) baseAnswerResult;
            telegramClient.answerCallbackQuery(callbackAnswerResult.getCallbackId(), callbackAnswerResult.getMsg());
        }

        if (baseAnswerResult instanceof SingleButtonAnswerResult)
        {
            final SingleButtonAnswerResult singleButtonAnswerResult = (SingleButtonAnswerResult) baseAnswerResult;

            telegramClient.sendSingleButton(singleButtonAnswerResult.getChatId(),
                                            baseAnswerResult.getMsg(),
                                            singleButtonAnswerResult.getButtonText(),
                                            singleButtonAnswerResult.getCallbackName());
        }

        if (baseAnswerResult instanceof MessageAnswerResult)
        {
            final MessageAnswerResult answerResult = (MessageAnswerResult) baseAnswerResult;
            telegramClient.sendSingleMessage(answerResult.getMsg(), answerResult.getChatId());
        }
    }
}
