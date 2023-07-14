package com.github.kopylove.charades.runner;

public enum Commands
{
    START_CHARADES("/startCharades"),
    LANGUAGE("/language"),
    UPTIME("/uptime");

    /**
     * user command.
     */
    private String value;

    Commands(final String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(final String value)
    {
        this.value = value;
    }
}
