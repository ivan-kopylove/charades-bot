package com.github.lazyf1sh.sandbox.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

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
