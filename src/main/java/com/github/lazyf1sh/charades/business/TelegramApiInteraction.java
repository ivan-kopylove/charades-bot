package com.github.lazyf1sh.charades.business;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lazyf1sh.charades.domain.telegram.api.ChatMember;
import com.github.lazyf1sh.charades.domain.telegram.api.GetChatMember;
import com.github.lazyf1sh.charades.domain.telegram.api.GetUpdate;
import com.github.lazyf1sh.charades.domain.telegram.api.InlineKeyboardButton;
import com.github.lazyf1sh.charades.domain.telegram.api.InlineKeyboardMarkup;
import com.github.lazyf1sh.charades.domain.telegram.api.KeyboardButton;
import com.github.lazyf1sh.charades.domain.telegram.api.ReplyKeyboardMarkup;
import com.github.lazyf1sh.charades.runner.GlobalClient;
import com.github.lazyf1sh.charades.runner.Util;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TelegramApiInteraction
{
    private static final Logger LOGGER = LogManager.getLogger(TelegramApiInteraction.class);

    public static void answerCallbackQuery(String updateId, String buttonText)
    {
        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("callback_query_id", String.valueOf(updateId));
        params.add("text", buttonText);
        params.add("show_alert", String.valueOf(true));

        String url = Util.buildUrl("answerCallbackQuery");

        performRequest(url, params);
    }

    public static void sendSingleButton(long chatId, String messageText, String buttonText, String callbackName)
    {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttonText);
        inlineKeyboardButton.setCallbackData(callbackName);

        InlineKeyboardButton[][] keyboard = {{inlineKeyboardButton}};
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setInlineKeyboard(keyboard);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try
        {
            json = objectMapper.writeValueAsString(keyboardMarkup);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("text", messageText);
        params.add("disable_notification", String.valueOf(true));
        params.add("reply_markup", json);


        String url = Util.buildUrl("sendMessage");

        performRequest(url, params);
    }

    public static void sendReplyButton(long chatId, String buttonText)
    {
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("button test");
        KeyboardButton[][] keyboardButtons = {{keyboardButton}};

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardButtons);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try
        {
            json = objectMapper.writeValueAsString(replyKeyboardMarkup);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("text", "hardcoded placeholder2");
        params.add("disable_notification", String.valueOf(true));
        params.add("reply_markup", json);

        String url = Util.buildUrl("sendMessage");

        performRequest(url, params);
    }

    /**
     * @param startFrom inclusive.
     * @return
     */
    public static GetUpdate getUpdate(int startFrom)
    {
        String url = Util.buildUrl("getUpdates");

        return GlobalClient.CLIENT
                .target(url)
                .queryParam("offset", String.valueOf(startFrom))
                .request(MediaType.APPLICATION_JSON)
                .get(GetUpdate.class);
    }


    public static GetChatMember getChatMember(long chatId, long userId)
    {
        String url = Util.buildUrl("getChatMember");

        return GlobalClient.CLIENT
                .target(url)
                .queryParam("chat_id", String.valueOf(chatId))
                .queryParam("user_id", String.valueOf(userId))
                .request(MediaType.APPLICATION_JSON)
                .get(GetChatMember.class);
    }



    public static void sendSingleMessage(String text, long chatId)
    {
        String url = Util.buildUrl("sendMessage");

        MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("disable_notification", String.valueOf(true));
        params.add("text", text);

        performRequest(url, params);
    }

    private static void performRequest(String url, MultivaluedMap<String, String> params)
    {

        WebTarget webTarget = GlobalClient.CLIENT
                .target(url);

        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(params));

        if (response.getStatus() != 200)
        {
            Util.dumpObject("not 200", response.getStatusInfo());
        }
    }
}
