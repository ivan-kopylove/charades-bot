package com.github.kopylove.charades.domain.local;

public class MessageAnswerResult extends BaseAnswerResult
{
    private long chatId;

    public long getChatId()
    {
        return chatId;
    }

    public void setChatId(final long chatId)
    {
        this.chatId = chatId;
    }
}
