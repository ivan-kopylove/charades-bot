package com.github.lazyf1sh.charades.domain.local;

public class SingleButtonAnswerResult extends BaseAnswerResult
{
    private long   chatId;
    /**
     * technical name.
     */
    private String callbackName;

    private String buttonText;

    public String getButtonText()
    {
        return buttonText;
    }

    public void setButtonText(String buttonText)
    {
        this.buttonText = buttonText;
    }

    public String getCallbackName()
    {
        return callbackName;
    }

    public void setCallbackName(String callbackName)
    {
        this.callbackName = callbackName;
    }

    public long getChatId()
    {
        return chatId;
    }

    public void setChatId(long chatId)
    {
        this.chatId = chatId;
    }
}
