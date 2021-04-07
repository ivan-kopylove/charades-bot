package com.github.lazyf1sh.charades.domain.telegram.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplyKeyboardMarkup extends BaseApiType
{
    @JsonProperty("keyboard")
    private KeyboardButton[][] keyboard;

    public KeyboardButton[][] getKeyboard()
    {
        return keyboard;
    }

    public void setKeyboard(KeyboardButton[][] keyboard)
    {
        this.keyboard = keyboard;
    }
}
