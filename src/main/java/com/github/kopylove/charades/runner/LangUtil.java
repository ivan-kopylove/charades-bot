package com.github.kopylove.charades.runner;

import com.github.kopylove.charades.domain.local.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public final class LangUtil
{
    private static final Logger LOGGER = LogManager.getLogger(LangUtil.class);

    private LangUtil() {}

    public static Language convert(@Nullable final String lang)
    {
        if (lang == null)
        {
            final Language defaultLang = Language.RUSSIAN;
            LOGGER.error("returning default language: " + defaultLang.getCode());
            return defaultLang;
        }
        for (final Language value : Language.values())
        {
            if (value.getCode()
                     .equals(lang))
            {
                return value;
            }
        }

        return Language.RUSSIAN;
    }

    public static boolean validateLang(final String langCode)
    {
        for (final Language value : Language.values())
        {
            if (value.getCode()
                     .equals(langCode))
            {
                return true;
            }
        }
        return false;
    }
}
