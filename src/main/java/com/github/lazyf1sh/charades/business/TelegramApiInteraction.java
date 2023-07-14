package com.github.lazyf1sh.charades.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lazyf1sh.charades.domain.telegram.api.*;
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

    private TelegramApiInteraction() {}

    public static void answerCallbackQuery(final String updateId, final String buttonText)
    {
        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("callback_query_id", String.valueOf(updateId));
        params.add("text", buttonText);
        params.add("show_alert", String.valueOf(true));

        final String url = Util.buildUrl("answerCallbackQuery");

        performRequest(url, params);
    }

    public static void sendSingleButton(final long chatId, final String messageText, final String buttonText, final String callbackName)
    {
        final InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttonText);
        inlineKeyboardButton.setCallbackData(callbackName);

        final InlineKeyboardButton[][] keyboard = {{inlineKeyboardButton}};
        final InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setInlineKeyboard(keyboard);

        final ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try
        {
            json = objectMapper.writeValueAsString(keyboardMarkup);
        }
        catch (final JsonProcessingException e)
        {
            e.printStackTrace();
        }

        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("text", messageText);
        params.add("disable_notification", String.valueOf(true));
        params.add("reply_markup", json);


        final String url = Util.buildUrl("sendMessage");

        performRequest(url, params);
    }

    public static void sendReplyButton(final long chatId, final String buttonText)
    {
        final KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("button test");
        final KeyboardButton[][] keyboardButtons = {{keyboardButton}};

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardButtons);

        final ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try
        {
            json = objectMapper.writeValueAsString(replyKeyboardMarkup);
        }
        catch (final JsonProcessingException e)
        {
            e.printStackTrace();
        }

        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("text", "hardcoded placeholder2");
        params.add("disable_notification", String.valueOf(true));
        params.add("reply_markup", json);

        final String url = Util.buildUrl("sendMessage");

        performRequest(url, params);
    }

    /**
     * @param startFrom inclusive.
     * @return
     */
    public static GetUpdate getUpdate(final int startFrom)
    {
        final String url = Util.buildUrl("getUpdates");

        return GlobalClient.CLIENT.target(url)
                                  .queryParam("offset", String.valueOf(startFrom))
                                  .request(MediaType.APPLICATION_JSON)
                                  .get(GetUpdate.class);
    }

    public static GetChatMember getChatMember(final long chatId, final long userId)
    {
        final String url = Util.buildUrl("getChatMember");

        return GlobalClient.CLIENT.target(url)
                                  .queryParam("chat_id", String.valueOf(chatId))
                                  .queryParam("user_id", String.valueOf(userId))
                                  .request(MediaType.APPLICATION_JSON)
                                  .get(GetChatMember.class);
    }

    public static void sendSingleMessage(final String text, final long chatId)
    {
        final String url = Util.buildUrl("sendMessage");

        final MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
        params.add("chat_id", String.valueOf(chatId));
        params.add("disable_notification", String.valueOf(true));
        params.add("text", text);

        performRequest(url, params);
    }

    private static void performRequest(final String url, final MultivaluedMap<String, String> params)
    {

        final WebTarget webTarget = GlobalClient.CLIENT.target(url);

        final Response response = webTarget.request(MediaType.APPLICATION_JSON)
                                           .post(Entity.form(params));

        if (response.getStatus() != 200)
        {
            Util.dumpObject("not 200", response.getStatusInfo());
        }
    }
}
