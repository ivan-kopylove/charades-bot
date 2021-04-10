package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.business.TelegramApiInteraction;
import com.github.lazyf1sh.charades.domain.local.Language;
import com.github.lazyf1sh.charades.domain.local.NoActionResult;
import com.github.lazyf1sh.charades.domain.telegram.api.GetChatMember;
import com.github.lazyf1sh.charades.domain.telegram.api.Message;
import com.github.lazyf1sh.charades.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.charades.domain.local.CharadesGameDetails;
import com.github.lazyf1sh.charades.domain.local.MessageAnswerResult;
import com.github.lazyf1sh.charades.domain.local.SingleButtonAnswerResult;
import com.github.lazyf1sh.charades.storage.ChatInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Locale;

public class MessageHandler
{
    private static final Logger LOGGER               = LogManager.getLogger(MessageHandler.class);
    public static final  String COMMAND_ARG_SPLITTER = " ";

    @Nullable
    public static BaseAnswerResult handleMessage(@NotNull Message message)
    {
        String msgText = message.getText();

        if (msgText != null)
        {
            long chatId = message.getChat().getId();

            CharadesGameDetails charadesGameDetails = CharadesState.findActiveGame(chatId);

            if (charadesGameDetails != null)
            {
                if (charadesGameDetails.getExplainerId() != message.getUser().getId() && charadesGameDetails.getWord().equalsIgnoreCase(msgText))
                {
                    SingleButtonAnswerResult result = new SingleButtonAnswerResult();
                    result.setChatId(chatId);
                    result.setMsg(Config.getValue("word.is.guessed", new Locale(charadesGameDetails.getLang().getCode())) + " " + charadesGameDetails.getWord());
                    result.setButtonText(Config.getValue("go.next.charades.game", new Locale(charadesGameDetails.getLang().getCode())));
                    result.setCallbackName(Callbacks.START_CHARADES.toString());

                    charadesGameDetails.setGuessEndTime(System.currentTimeMillis());
                    charadesGameDetails.setActive(false);
                    return result;
                }
            }

            if (msgText.startsWith(Commands.START_CHARADES.getValue()))
            {
                return CharadesStarter.startNewCharades(chatId, ChatInformation.getChatLanguage(chatId), message.getUser().getId());
            }

            if (msgText.startsWith(Commands.LANGUAGE.getValue()))
            {
                return handleLanguage(message);
            }

            if (Commands.UPTIME.getValue().equals(msgText))
            {
                MessageAnswerResult result = new MessageAnswerResult();
                result.setChatId(chatId);
                result.setMsg(Util.buildUptime(Const.BOT_START_TIME, new Date()));
                return result;
            }
        }
        return NoActionResult.NO_ACTION_RESULT;
    }

    private static BaseAnswerResult handleLanguage(@NotNull Message message)
    {
        String msgText = message.getText();
        long chatId = message.getChat().getId();
        String[] args = msgText.split(COMMAND_ARG_SPLITTER);
        if (args.length > 1)
        {
            String lang = args[1];

            if (LangUtil.validateLang(lang))
            {
                GetChatMember chatMember = TelegramApiInteraction.getChatMember(chatId, message.getUser().getId());
                if (ChatMemberUtil.isPowerUser(chatMember))
                {
                    ChatInformation.saveOrUpdateChatLanguage(chatId, lang);

                    Language convert = LangUtil.convert(lang);
                    MessageAnswerResult result = new MessageAnswerResult();
                    result.setChatId(chatId);
                    result.setMsg(Config.getValue("language.changed.to", new Locale(convert.getCode())) + " " + convert);
                    return result;
                }
            }
        }
        return NoActionResult.NO_ACTION_RESULT;
    }

}
