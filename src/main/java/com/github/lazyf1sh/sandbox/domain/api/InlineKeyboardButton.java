package com.github.lazyf1sh.sandbox.domain.api;

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

    public void setCallbackData(String callbackData)
    {
        this.callbackData = callbackData;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
