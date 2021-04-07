package com.github.lazyf1sh.charades.domain.telegram.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InlineKeyboardMarkup extends BaseApiType
{
    @JsonProperty("inline_keyboard")
    private InlineKeyboardButton[][] inlineKeyboard;

    public InlineKeyboardButton[][] getInlineKeyboard()
    {
        return inlineKeyboard;
    }

    public void setInlineKeyboard(InlineKeyboardButton[][] inlineKeyboard)
    {
        this.inlineKeyboard = inlineKeyboard;
    }
}
