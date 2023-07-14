package com.github.kopylove.charades.runner;

import com.github.kopylove.charades.domain.local.CharadesGameDetails;
import com.github.kopylove.charades.domain.local.Language;
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

public final class CharadesState
{
    private static final Logger                           LOGGER       = LogManager.getLogger(CharadesState.class);
    /**
     * game id to game.
     */
    private static final Map<String, CharadesGameDetails> GAME_DETAILS = new HashMap<>();
    /**
     * word to language.
     */
    private static final Map<String, Language>            WORDS        = new HashMap<>();

    static
    {
        final ClassLoader classloader = Thread.currentThread()
                                              .getContextClassLoader();
        initWords(classloader, new File("words_ru.txt"), Language.RUSSIAN);
        initWords(classloader, new File("words_en.txt"), Language.ENGLISH);
    }

    private CharadesState() {}

    public static void addNewGame(final String key, final CharadesGameDetails charadesGameDetails)
    {
        GAME_DETAILS.put(key, charadesGameDetails);
    }

    public static CharadesGameDetails getGameById(final String id)
    {
        return GAME_DETAILS.get(id);
    }

    /**
     * @param chatId
     * @return null when no game found.
     */
    @Nullable
    public static CharadesGameDetails findActiveGame(final long chatId)
    {
        return GAME_DETAILS.values()
                           .stream()
                           .filter(charadesGameDetails -> charadesGameDetails.getChatId() == chatId)
                           .filter(CharadesGameDetails::isActive)
                           .findFirst()
                           .orElse(null);
    }

    public static String getRandomWord(final Language lang)
    {
        final List<Map.Entry<String, Language>> filtered = WORDS.entrySet()
                                                                .stream()
                                                                .filter(stringLanguageEntry -> stringLanguageEntry.getValue()
                                                                                                                  .equals(lang))
                                                                .collect(Collectors.toList());

        final int randomIndex = ThreadLocalRandom.current()
                                                 .nextInt(0, filtered.size());
        final Map.Entry<String, Language> stringLanguageEntry = filtered.get(randomIndex);
        return stringLanguageEntry.getKey();
    }

    public static String getCurrentWord(final long chatId)
    {
        final String currentWord = GAME_DETAILS.values()
                                               .stream()
                                               .filter(charadesGameDetails -> charadesGameDetails.getChatId() == chatId)
                                               .filter(CharadesGameDetails::isActive)
                                               .findFirst()
                                               .map(CharadesGameDetails::getWord)
                                               .orElse(null);
        return Objects.requireNonNullElse(currentWord, "unable to find current word");
    }

    public static Language getCurrentLocale(final long chatId)
    {
        final Language currentLanguage = GAME_DETAILS.values()
                                                     .stream()
                                                     .filter(charadesGameDetails -> charadesGameDetails.getChatId() == chatId)
                                                     .findFirst()
                                                     .map(CharadesGameDetails::getLang)
                                                     .orElse(null);
        return Objects.requireNonNullElse(currentLanguage, Language.ENGLISH);
    }

    private static void initWords(final ClassLoader classloader, final File file, final Language language)
    {
        try
        {
            final List<String> words = Files.readAllLines(file.toPath());
            for (final String word : words)
            {
                WORDS.put(word, language);
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
}
