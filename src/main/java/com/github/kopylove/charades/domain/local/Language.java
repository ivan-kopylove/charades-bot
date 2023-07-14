package com.github.kopylove.charades.domain.local;

public enum Language
{
    RUSSIAN("ru"),
    ENGLISH("en");

    private final String code;

    Language(final String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}
