package com.github.lazyf1sh.charades.domain.telegram.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User extends BaseApiType
{
    @JsonProperty("id")
    private int     id;

    @JsonProperty("is_bot")
    private boolean isBot;

    @JsonProperty("first_name")
    private String  firstName;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public boolean isBot()
    {
        return isBot;
    }

    public void setBot(boolean bot)
    {
        isBot = bot;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
}
