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
import java.util.Locale;
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
                    if (charadesGameDetails.getExplainerId() != message.getFrom().getId() && charadesGameDetails.getWord().equals(msgText))
                    {
                        SingleButtonAnswerResult result = new SingleButtonAnswerResult();
                        result.setChatId(message.getChat().getId());
                        result.setMsg(Config.getValue(new Locale(charadesGameDetails.getLang().getCode()), "word.is.guessed") + " " + charadesGameDetails.getWord());
                        result.setButtonText(Config.getValue(new Locale(charadesGameDetails.getLang().getCode()), "go.next.charades.game"));
                        result.setCallbackName(Callbacks.START_CHARADES.toString());

                        charadesGameDetails.setGuessedMillis(System.currentTimeMillis());
                        charadesGameDetails.setActive(false);
                        return result;
                    }
                }

                if (msgText.startsWith("/start"))
                {
                    String[] args = msgText.split(" ");
                    if (args.length > 1)
                    {
                        return startNewCharades(message.getChat().getId(), LangConverter.convert(args[1]), message.getFrom().getId());
                    }
                    else
                    {
                        MessageAnswerResult result = new MessageAnswerResult();
                        result.setChatId(message.getChat().getId());
                        if (charadesGameDetails != null)
                        {
                            result.setMsg(Config.getValue(new Locale(charadesGameDetails.getLang().getCode()), "please.use.full.start.command"));
                        }
                        else
                        {
                            result.setMsg(Config.getValue("please.use.full.start.command"));
                        }
                        return result;
                    }
                }

                if ("/uptime".equals(msgText))
                {
                    MessageAnswerResult result = new MessageAnswerResult();
                    result.setChatId(message.getChat().getId());
                    result.setMsg(Util.buildUptime(Const.BOT_START_TIME, new Date()));
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
            if (data != null && !data.isBlank())
            {


                CallbackAnswerResult result = new CallbackAnswerResult();
                result.setCallbackId(callbackQuery.getId());

                String[] fields = data.split("\\" + Const.CALLBACK_FIELDS_SPLITTER);
                if (fields.length > 1)
                {
                    CharadesGameDetails charadesGameDetails = CharadesState.GAME_DETAILS.get(fields[1]);

                    if (Callbacks.CHECK_WORD.toString().equals(fields[0]))
                    {
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
                        return startNewCharades(chatId, CharadesState.getCurrentLocale(chatId), callbackQuery.getFrom().getId());
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
            result.setMsg(Config.getValue(new Locale(lang.getCode()), "new.game.has.started"));
            ((SingleButtonAnswerResult) result).setChatId(chatId);
            ((SingleButtonAnswerResult) result).setButtonText(Config.getValue(new Locale(lang.getCode()), "check.word"));
            ((SingleButtonAnswerResult) result).setCallbackName(Callbacks.CHECK_WORD.toString() + Const.CALLBACK_FIELDS_SPLITTER + newGameId);

            String newWord = CharadesState.getRandomWord(lang);

            CharadesState.GAME_DETAILS.put(newGameId, new CharadesGameDetails(true, newWord, lang, newGameId, explainerId, chatId));
        }

        return result;

    }

}
