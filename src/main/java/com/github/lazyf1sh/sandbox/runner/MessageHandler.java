package com.github.lazyf1sh.sandbox.runner;

import com.github.lazyf1sh.sandbox.domain.api.Message;
import com.github.lazyf1sh.sandbox.domain.local.BaseAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.CharadesGameDetails;
import com.github.lazyf1sh.sandbox.domain.local.MessageAnswerResult;
import com.github.lazyf1sh.sandbox.domain.local.SingleButtonAnswerResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MessageHandler
{
    private static final Logger LOGGER = LogManager.getLogger(MessageHandler.class);

    @Nullable
    public static BaseAnswerResult handleMessage(@NotNull Message message)
    {
        Objects.requireNonNull(message);
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
                    return CharadesStarter.startNewCharades(message.getChat().getId(), LangConverter.convert(args[1]), message.getFrom().getId());
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
        return null;
    }

}
