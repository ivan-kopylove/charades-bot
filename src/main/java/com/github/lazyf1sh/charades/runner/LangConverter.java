package com.github.lazyf1sh.charades.runner;

import com.github.lazyf1sh.charades.domain.local.Language;

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
