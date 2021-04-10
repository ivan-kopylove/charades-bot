package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.local.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class LangUtil
{
    private static final Logger LOGGER = LogManager.getLogger(LangUtil.class);
    public static Language convert(@Nullable String lang)
    {
        if(lang == null)
        {
            Language defaultLang = Language.RUSSIAN;
            LOGGER.error("returning default language: " + defaultLang.getCode());
            return defaultLang;
        }
        for (Language value : Language.values())
        {
            if (value.getCode().equals(lang))
            {
                return value;
            }
        }

        return Language.RUSSIAN;
    }

    public static boolean validateLang(String langCode)
    {
        for (Language value : Language.values())
        {
            if (value.getCode().equals(langCode))
            {
                return true;
            }
        }
        return false;
    }
}
