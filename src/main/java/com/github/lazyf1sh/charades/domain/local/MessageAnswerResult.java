package com.github.lazyf1sh.charades.domain.local;

public class MessageAnswerResult extends BaseAnswerResult
{
    private long chatId;

    public long getChatId()
    {
        return chatId;
    }


    public void setChatId(long chatId)
    {
        this.chatId = chatId;
    }
}
