package com.github.lazyf1sh.sandbox.runner;

import com.github.lazyf1sh.sandbox.domain.local.CharadesGameDetails;
import com.github.lazyf1sh.sandbox.domain.local.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CharadesState
{
    private static final Logger                           LOGGER       = LogManager.getLogger(CharadesState.class);
    public static final  Map<String, CharadesGameDetails> GAME_DETAILS = new HashMap<>();
    public static final  Map<String, Language>            WORDS        = new HashMap<>();

    /**
     * @param chatId
     * @return null when no game found.
     */
    @Nullable
    public static CharadesGameDetails findActiveGame(long chatId)
    {
        return GAME_DETAILS.values()
                .stream()
                .filter(charadesGameDetails -> charadesGameDetails.getChatId() == chatId)
                .filter(CharadesGameDetails::isActive)
                .findFirst()
                .orElse(null);
    }

    public static String getRandomWord(Language lang)
    {
        List<Map.Entry<String, Language>> filtered = WORDS
                .entrySet()
                .stream()
                .filter(stringLanguageEntry -> stringLanguageEntry.getValue().equals(lang)).collect(Collectors.toList());

        int randomIndex = ThreadLocalRandom.current().nextInt(0, filtered.size());
        Map.Entry<String, Language> stringLanguageEntry = filtered.get(randomIndex);
        return stringLanguageEntry.getKey();
    }

    public static String getCurrentWord(long chatId)
    {
        String currentWord = GAME_DETAILS.values()
                .stream()
                .filter(charadesGameDetails -> charadesGameDetails.getChatId() == chatId)
                .filter(CharadesGameDetails::isActive)
                .findFirst()
                .map(CharadesGameDetails::getWord)
                .orElse(null);
        return Objects.requireNonNullElse(currentWord, "unable to find current word");
    }

    public static Language getCurrentLocale(long chatId)
    {
        Language currentLanguage = GAME_DETAILS.values()
                .stream()
                .filter(charadesGameDetails -> charadesGameDetails.getChatId() == chatId)
                .findFirst()
                .map(CharadesGameDetails::getLang)
                .orElse(null);
        return Objects.requireNonNullElse(currentLanguage, Language.EN);
    }

    static
    {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        initWords(classloader, new File("words_ru.txt"), Language.RU);
        initWords(classloader, new File("words_en.txt"), Language.EN);
    }

    private static void initWords(ClassLoader classloader, File file, Language language)
    {
        try
        {
            List<String> words = Files.readAllLines(file.toPath());
            for (String word : words)
            {
                WORDS.put(word, language);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
