package com.github.kopylove.charades.runner2;

public class ApplicationConfig
{
    private String telegramApiKey;
    private String databasePassword;
    private String databaseUsername;
    private String databaseName;

    public String getTelegramApiKey()
    {
        return telegramApiKey;
    }

    public void setTelegramApiKey(final String telegramApiKey)
    {
        this.telegramApiKey = telegramApiKey;
    }

    public String getDatabasePassword()
    {
        return databasePassword;
    }

    public void setDatabasePassword(final String databasePassword)
    {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseUsername()
    {
        return databaseUsername;
    }

    public void setDatabaseUsername(final String databaseUsername)
    {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public void setDatabaseName(final String databaseName)
    {
        this.databaseName = databaseName;
    }
}
