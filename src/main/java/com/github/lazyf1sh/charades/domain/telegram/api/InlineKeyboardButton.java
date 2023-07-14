package com.github.lazyf1sh.charades.domain.telegram.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InlineKeyboardButton extends BaseApiType
{
    @JsonProperty("callback_data")
    private String callbackData;
    @JsonProperty("text")
    private String text;

    public String getCallbackData()
    {
        return callbackData;
    }

    public void setCallbackData(final String callbackData)
    {
        this.callbackData = callbackData;
    }

    public String getText()
    {
        return text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }
}
