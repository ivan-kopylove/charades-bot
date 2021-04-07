package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.telegram.api.CallbackQuery;
import com.github.lazyf1sh.charades.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.charades.domain.local.CallbackAnswerResult;
import com.github.lazyf1sh.charades.domain.local.CharadesGameDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

public class CallbackHandler
{
    private static final Logger LOGGER = LogManager.getLogger(CallbackHandler.class);

    @Nullable
    public static BaseAnswerResult handleCallback(@NotNull CallbackQuery callbackQuery)
    {
        Objects.requireNonNull(callbackQuery);

        long chatId = callbackQuery.getMessage().getChat().getId();
        long fromId = callbackQuery.getFrom().getId();

        String data = callbackQuery.getData();
        if (data != null && !data.isBlank())
        {
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
                        if (charadesGameDetails.getGuessedMillis() > 0)
                        {
                            result.setMsg(Config.getValue(new Locale(charadesGameDetails.getLang().getCode()), "guessed.word") + " " + charadesGameDetails.getWord());
                        }
                        else if (charadesGameDetails.getExplainerId() == fromId)
                        {
                            result.setMsg(Config.getValue(new Locale(charadesGameDetails.getLang().getCode()), "please.explain.word") + " " + charadesGameDetails.getWord());
                        }
                        else
                        {
                            result.setMsg(Config.getValue(new Locale(charadesGameDetails.getLang().getCode()), "hidden.for.guessers"));
                        }
                    }
                    else
                    {
                        result.setMsg(Config.getValue("bot.forgot.word"));
                    }
                }

                return result;
            }
            else if (fields.length > 0 && Callbacks.START_CHARADES.toString().equals(fields[0]))
            {
                if (callbackQuery.getFrom() != null)
                {
                    return CharadesStarter.startNewCharades(chatId, CharadesState.getCurrentLocale(chatId), callbackQuery.getFrom().getId());
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
        return null;
    }

}
