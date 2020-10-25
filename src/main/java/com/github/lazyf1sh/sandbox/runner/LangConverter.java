package com.github.lazyf1sh.sandbox.runner;

import com.github.lazyf1sh.sandbox.domain.local.Language;

public class LangConverter
{
    public static Language convert(String lang)
    {
        if(Language.EN.getCode().equals(lang))
        {
            return Language.EN;
        }
        if(Language.RU.getCode().equals(lang))
        {
            return Language.RU;
        }

        return Language.EN;
    }
}
