package com.github.lazyf1sh.charades.runner;

public enum Commands
{
    START_CHARADES("/start");

    /**
     * user command.
     */
    private String value;
    Commands(String value)
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
