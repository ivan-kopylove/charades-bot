package com.github.lazyf1sh.charades.domain.telegram.api;

public class SendMessage extends ResponseBase
{
    private Message result;

    public Message getResult()
    {
        return result;
    }

    public void setResult(Message result)
    {
        this.result = result;
    }
}
