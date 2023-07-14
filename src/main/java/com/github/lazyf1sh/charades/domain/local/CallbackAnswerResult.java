package com.github.lazyf1sh.charades.domain.local;

public class CallbackAnswerResult extends BaseAnswerResult
{
    private String callbackId;

    public String getCallbackId()
    {
        return callbackId;
    }

    public void setCallbackId(final String callbackId)
    {
        this.callbackId = callbackId;
    }
}
