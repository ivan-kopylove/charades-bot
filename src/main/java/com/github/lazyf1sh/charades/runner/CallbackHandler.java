package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.local.Language;
import com.github.lazyf1sh.charades.domain.local.NoActionResult;
import com.github.lazyf1sh.charades.domain.telegram.api.CallbackQuery;
import com.github.lazyf1sh.charades.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.charades.domain.local.CallbackAnswerResult;
import com.github.lazyf1sh.charades.domain.local.CharadesGameDetails;
import com.github.lazyf1sh.charades.domain.telegram.api.Message;
import com.github.lazyf1sh.charades.domain.telegram.api.User;
import com.github.lazyf1sh.charades.storage.ChatInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

public class CallbackHandler
{
    private static final Logger LOGGER = LogManager.getLogger(CallbackHandler.class);

    @NotNull
    public static BaseAnswerResult handleCallback(@NotNull CallbackQuery callbackQuery)
    {
        Objects.requireNonNull(callbackQuery);

        Message message = callbackQuery.getMessage();
        if (message == null)
        {
            return NoActionResult.NO_ACTION_RESULT;
        }

        long chatFromId = message.getChat().getId();
        User from = callbackQuery.getFrom();

        String data = callbackQuery.getData();
        if (data != null && !data.isBlank())
        {
            Language chatLanguage = ChatInformation.getChatLanguage(chatFromId);
            Locale chatLocale = new Locale(chatLanguage.getCode());

            CallbackAnswerResult result = new CallbackAnswerResult();
            result.setCallbackId(callbackQuery.getId());

            String[] fields = data.split("\\" + Const.CALLBACK_FIELDS_SPLITTER);
            if (fields.length > 1)
            {
                if (Callbacks.CHECK_WORD.toString().equals(fields[0]))
                {
                    CharadesGameDetails charadesGameDetails = CharadesState.getGameById(fields[1]);

                    if (charadesGameDetails != null)
                    {
                        if (charadesGameDetails.getGuessEndTime() > 0)
                        {
                            result.setMsg(Config.getValue("guessed.word", chatLocale) + " " + charadesGameDetails.getWord());
                        }
                        else if (charadesGameDetails.getExplainerId() == from.getId())
                        {
                            result.setMsg(Config.getValue("please.explain.word", chatLocale) + " " + charadesGameDetails.getWord());
                        }
                        else
                        {
                            result.setMsg(Config.getValue("hidden.for.guessers", chatLocale));
                        }
                    }
                    else
                    {

                        result.setMsg(Config.getValue("bot.forgot.word", chatLocale));
                    }
                }

                return result;
            }
            else if (fields.length > 0 && Callbacks.START_CHARADES.toString().equals(fields[0]))
            {
                if (from != null)
                {
                    return CharadesStarter.startNewCharades(chatFromId, chatLanguage, from.getId());
                }
                else
                {
                    LOGGER.error("callbackQuery.getFrom() is null.");
                }
            }
        }
        else
        {
            throw new RuntimeException("data is null.");
        }
        return NoActionResult.NO_ACTION_RESULT;
    }

}
