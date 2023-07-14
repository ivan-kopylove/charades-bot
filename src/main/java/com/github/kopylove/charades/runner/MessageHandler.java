package com.github.kopylove.charades.runner;

import com.github.kopylove.charades.domain.local.*;
import com.github.kopylove.charades.storage.ChatInformation;
import com.github.lazyf1sh.telegram.api.domain.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Locale;

public final class MessageHandler
{
    public static final String COMMAND_ARG_SPLITTER = " ";

    private MessageHandler() {}

    @Nullable
    public static BaseAnswerResult handleMessage(@NotNull final Message message)
    {
        final String msgText = message.getText();

        if (msgText != null)
        {
            final long chatId = message.getChat()
                                       .getId();

            final CharadesGameDetails charadesGameDetails = CharadesState.findActiveGame(chatId);

            if (charadesGameDetails != null)
            {
                if (charadesGameDetails.getExplainerId() != message.getUser()
                                                                   .getId() && charadesGameDetails.getWord()
                                                                                                  .equalsIgnoreCase(
                                                                                                          msgText))
                {
                    final SingleButtonAnswerResult result = new SingleButtonAnswerResult();
                    result.setChatId(chatId);
                    result.setMsg(Config.getValue("word.is.guessed",
                                                  new Locale(charadesGameDetails.getLang()
                                                                                .getCode())) + " " + charadesGameDetails.getWord());
                    result.setButtonText(Config.getValue("go.next.charades.game",
                                                         new Locale(charadesGameDetails.getLang()
                                                                                       .getCode())));
                    result.setCallbackName(Callbacks.START_CHARADES.toString());

                    charadesGameDetails.setGuessEndTime(System.currentTimeMillis());
                    charadesGameDetails.setActive(false);
                    return result;
                }
            }

            if (msgText.startsWith(Commands.START_CHARADES.getValue()))
            {
                //                return CharadesStarter.startNewCharades(chatId,
                //                                                        ChatInformation.getChatLanguage(chatId),
                //                                                        message.getUser()
                //                                                               .getId());
            }

            if (msgText.startsWith(Commands.LANGUAGE.getValue()))
            {
                return handleLanguage(message);
            }

            if (Commands.UPTIME.getValue()
                               .equals(msgText))
            {
                final MessageAnswerResult result = new MessageAnswerResult();
                result.setChatId(chatId);
                result.setMsg(Util.buildUptime(Const.BOT_START_TIME, new Date()));
                return result;
            }
        }
        return NoActionResult.NO_ACTION_RESULT;
    }

    private static BaseAnswerResult handleLanguage(@NotNull final Message message)
    {
        final String msgText = message.getText();
        final long chatId = message.getChat()
                                   .getId();
        final String[] args = msgText.split(COMMAND_ARG_SPLITTER);
        if (args.length > 1)
        {
            final String lang = args[1];

            if (LangUtil.validateLang(lang))
            {
                //                final GetChatMember chatMember = TelegramApiInteraction.getChatMember(chatId,
                message.getUser()
                       .getId();
                //                if (ChatMemberUtil.isPowerUser(chatMember))
                {
                    ChatInformation.saveOrUpdateChatLanguage(chatId, lang);

                    final Language convert = LangUtil.convert(lang);
                    final MessageAnswerResult result = new MessageAnswerResult();
                    result.setChatId(chatId);
                    result.setMsg(Config.getValue("language.changed.to",
                                                  new Locale(convert.getCode())) + " " + convert);
                    return result;
                    //                }
                }
            }
            return NoActionResult.NO_ACTION_RESULT;
        }

        throw new RuntimeException();
    }
}