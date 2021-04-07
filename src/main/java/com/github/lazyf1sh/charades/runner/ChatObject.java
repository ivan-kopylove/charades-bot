package com.github.lazyf1sh.charades.runner;

public class ChatObject
{
    /**
     * Bot database ID.
     */
    private int id;
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

    public void setChatId(long chatId)
    {
        this.chatId = chatId;
    }

    public String getLanguageCode()
    {
        return languageCode;
    }

    public void setLanguageCode(String languageCode)
    {
        this.languageCode = languageCode;
    }
}
