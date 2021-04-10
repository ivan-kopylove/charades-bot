package com.github.lazyf1sh.charades.domain.telegram.api;

public enum ChatMemberStatus
{
    CREATOR("creator"),
    ADMIN("administrator"),
    MEMBER("member"),
    RESTRICTED("restricted"),
    LEFT("left");

    private String value;

    ChatMemberStatus(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
