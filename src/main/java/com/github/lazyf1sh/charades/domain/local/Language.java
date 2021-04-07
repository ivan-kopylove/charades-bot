package com.github.lazyf1sh.charades.domain.local;

public enum Language
{
    RU("ru"),
    EN("en");

    private final String code;

    public String getCode()
    {
        return code;
    }

    Language(String code)
    {
        this.code = code;
    }
}