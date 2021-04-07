package com.github.lazyf1sh.charades.domain.telegram.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * https://core.telegram.org/bots/api#update
 *
 * @author Ivan Kopylov
 */
public class Update extends BaseApiType
{
    @JsonProperty("update_id")
    private int updateId;

    /**
     * null when callback received.
     */
    @JsonProperty("message")
    private Message message;

    /**
     * Optional. New incoming callback query.
     * <p>
     * null when usual message is received.
     */
    @JsonProperty("callback_query")
    private CallbackQuery callbackQuery;

    public void setUpdateId(int updateId)
    {
        this.updateId = updateId;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }

    public CallbackQuery getCallbackQuery()
    {
        return callbackQuery;
    }

    public void setCallbackQuery(CallbackQuery callbackQuery)
    {
        this.callbackQuery = callbackQuery;
    }

    public int getUpdateId()
    {
        return updateId;
    }

    public Message getMessage()
    {
        return message;
    }
}
