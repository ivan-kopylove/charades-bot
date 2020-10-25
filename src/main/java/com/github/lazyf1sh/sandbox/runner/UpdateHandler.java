package com.github.lazyf1sh.sandbox.runner;

import com.github.lazyf1sh.sandbox.domain.api.CallbackQuery;
import com.github.lazyf1sh.sandbox.domain.api.Message;
import com.github.lazyf1sh.sandbox.domain.api.Update;
import com.github.lazyf1sh.sandbox.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.CallbackAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.CharadesGameDetails;
import com.github.lazyf1sh.sandbox.domain.local.Language;
import com.github.lazyf1sh.sandbox.domain.local.MessageAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.NoActionResult;
import com.github.lazyf1sh.sandbox.domain.local.SingleButtonAnswerResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public class UpdateHandler
{
    private static final Logger LOGGER = LogManager.getLogger(UpdateHandler.class);

    public static BaseAnswerResult handle(Update update)
    {
        Message message = update.getMessage();
        if (message != null)
        {
            CharadesGameDetails charadesGameDetails = CharadesState.findActiveGame(message.getChat().getId());
            String msgText = message.getText();

            if (msgText != null)
            {
                if (charadesGameDetails != null)
                {
                    if (charadesGameDetails.getWord().equals(msgText) && charadesGameDetails.getExplainerId() != message.getFrom().getId())
                    {
                        SingleButtonAnswerResult result = new SingleButtonAnswerResult();
                        result.setChatId(message.getChat().getId());
                        result.setMsg("Word is guessed: " + charadesGameDetails.getWord());
                        result.setButtonText("Go next");
                        result.setCallbackName("charadesStart");

                        charadesGameDetails.setGuessedMillis(System.currentTimeMillis());
                        charadesGameDetails.setActive(false);
                        return result;
                    }
                }

                if (msgText.startsWith("/start "))
                {
                    String[] args = msgText.split(" ");
                    if (args.length > 1)
                    {
                        return startNewCharades(message.getChat().getId(), LangConverter.convert(args[1]), message.getFrom().getId());
                    }
                }

                if ("/uptime".equals(msgText))
                {
                    MessageAnswerResult result = new MessageAnswerResult();
                    result.setChatId(message.getChat().getId());

                    long l = new Date().getTime() - Const.BOT_UPTIME.getTime();

                    result.setMsg(String.format("%s seconds.", l / 1000));
                    return result;
                }

                if ("/button".equals(msgText))
                {
                    SingleButtonAnswerResult result = new SingleButtonAnswerResult();
                    result.setChatId(message.getChat().getId());
                    result.setMsg("button text");
                    return result;
                }
            }
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null)
        {
            long chatId = callbackQuery.getMessage().getChat().getId();
            long fromId = callbackQuery.getFrom().getId();

            String data = callbackQuery.getData();
            CharadesGameDetails charadesGameDetails = CharadesState.GAME_DETAILS.get(data);

            CallbackAnswerResult result = new CallbackAnswerResult();
            result.setCallbackId(callbackQuery.getId());
            if (charadesGameDetails != null)
            {
                if (charadesGameDetails.getGuessedMillis() > 0)
                {
                    result.setMsg("The word was: " + charadesGameDetails.getWord());
                }
                else if (charadesGameDetails.getExplainerId() == fromId)
                {
                    result.setMsg("Please explain your word: " + charadesGameDetails.getWord());
                }
                else
                {
                    result.setMsg("This word is hidden for you at the moment. Try later.");
                }
            }
            else
            {
                result.setMsg("I have already forgotten this word. Maybe, someday I'll get better memory... :(");
            }

            if (data.equals("charadesStart"))
            {
                if (callbackQuery.getFrom() != null)
                {
                    return startNewCharades(chatId, CharadesState.getCurrentLocale(chatId), callbackQuery.getFrom().getId());
                }
                else
                {
                    LOGGER.error("callbackQuery.getFrom() is null.");
                }
            }

            return result;
        }

        return new NoActionResult();
    }

    @NotNull
    private static BaseAnswerResult startNewCharades(long chatId, Language lang, int explainerId)
    {
        BaseAnswerResult result;

        CharadesGameDetails gameDetails = CharadesState.findActiveGame(chatId);

        if (gameDetails != null)
        {
            result = new NoActionResult();
        }
        else
        {
            String newGameId = UUID.randomUUID().toString();

            result = new SingleButtonAnswerResult();
            result.setMsg("New game");

            ((SingleButtonAnswerResult) result).setChatId(chatId);
            ((SingleButtonAnswerResult) result).setButtonText("Check word");
            ((SingleButtonAnswerResult) result).setCallbackName(newGameId);

            String newWord = CharadesState.getRandomWord(lang);

            CharadesState.GAME_DETAILS.put(newGameId, new CharadesGameDetails(true, newWord, lang, newGameId, explainerId, chatId));
        }

        return result;

    }

}
