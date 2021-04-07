package com.github.lazyf1sh.charades.domain.local;

public class CharadesGameDetails
{
    /**
     * also used for callback name.
     */
    private String   gameId;
    private long     chatId;
    private boolean  active;
    private String   word;
    private Language lang;
    private int      explainerId;
    /**
     * -1 means word is not guessed.
     */
    private long     guessedMillis = -1;

    public CharadesGameDetails(boolean active, String word, Language lang, String gameId, int explainerId, long chatId)
    {
        this.gameId = gameId;
        this.explainerId = explainerId;
        this.active = active;
        this.word = word;
        this.lang = lang;
        this.chatId = chatId;
    }

    public long getChatId()
    {
        return chatId;
    }

    public void setChatId(long chatId)
    {
        this.chatId = chatId;
    }

    public long getGuessedMillis()
    {
        return guessedMillis;
    }

    public void setGuessedMillis(long guessedMillis)
    {
        this.guessedMillis = guessedMillis;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    public void setExplainerId(int explainerId)
    {
        this.explainerId = explainerId;
    }

    public String getGameId()
    {
        return gameId;
    }

    public int getExplainerId()
    {
        return explainerId;
    }

    public Language getLang()
    {
        return lang;
    }

    public void setLang(Language lang)
    {
        this.lang = lang;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }
}
