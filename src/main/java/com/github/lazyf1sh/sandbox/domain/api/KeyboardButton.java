package com.github.lazyf1sh.sandbox.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeyboardButton extends BaseApiType
{
    @JsonProperty("text")
    private String text;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
