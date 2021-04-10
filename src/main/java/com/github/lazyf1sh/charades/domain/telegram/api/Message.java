package com.github.lazyf1sh.charades.domain.telegram.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Message extends BaseApiType
{
    @JsonProperty("message_id")
    private int message_id;

    @JsonProperty("from")
    private User user;

    @JsonProperty("chat")
    private Chat chat;

    @JsonProperty("date")
    private int date;

    @JsonProperty("text")
    private String text;

    @JsonProperty("edit_date")
    private Integer edit_date;

    public void setMessage_id(int message_id)
    {
        this.message_id = message_id;
    }

    /**
     * Message from.
     *
     * @return
     */
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setChat(Chat chat)
    {
        this.chat = chat;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setEdit_date(Integer edit_date)
    {
        this.edit_date = edit_date;
    }

    public Integer getEdit_date()
    {
        return edit_date;
    }

    public int getMessage_id()
    {
        return message_id;
    }

    public Chat getChat()
    {
        return chat;
    }

    public int getDate()
    {
        return date;
    }

    public String getText()
    {
        return text;
    }

}
