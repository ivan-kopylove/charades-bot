package com.github.lazyf1sh.sandbox.domain.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMe extends ResponseBase
{
    @JsonProperty("result")
    public User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}