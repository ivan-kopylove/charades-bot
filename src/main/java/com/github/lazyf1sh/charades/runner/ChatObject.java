package com.github.lazyf1sh.charades.runner;

public class ChatObject
{
    /**
     * Bot database ID.
     */
    private int    id;
    /**
     * From telegram API.
     */
    private long   chatId;
    /**
     * ru, en, etc.
     */
    private String languageCode;

    public long getChatId()
    {
        return chatId;
    }

    public void setChatId(final long chatId)
    {
        this.chatId = chatId;
    }

    public String getLanguageCode()
    {
        return languageCode;
    }

    public void setLanguageCode(final String languageCode)
    {
        this.languageCode = languageCode;
    }
}
