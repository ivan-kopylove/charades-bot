package com.github.kopylove.charades.util;

public class Util
{

    public static String getTelegramApiKey()
    {
        final String devKey = System.getenv("TELEGRAM_API_CHARADES_DEV");
        if (!devKey.isBlank())
        {
            return devKey;
        }
        final String prodKey = System.getenv("TELEGRAM_API_CHARADES_PROD");
        if (!prodKey.isBlank())
        {
            return devKey;
        }
        throw new RuntimeException("Invalid configuration");
    }
}
