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
    private long     guessStartTime;
    /**
     * 0 for unguessed state.
     */
    private long     guessEndTime;

    public CharadesGameDetails(final boolean active, final String word, final Language lang, final String gameId, final int explainerId, final long chatId, final long guessStartTime)
    {
        this.gameId = gameId;
        this.explainerId = explainerId;
        this.active = active;
        this.word = word;
        this.lang = lang;
        this.chatId = chatId;
        this.guessStartTime = guessStartTime;
    }

    public long getChatId()
    {
        return chatId;
    }

    public void setChatId(final long chatId)
    {
        this.chatId = chatId;
    }

    public long getGuessStartTime()
    {
        return guessStartTime;
    }

    public void setGuessStartTime(final long guessStartTime)
    {
        this.guessStartTime = guessStartTime;
    }

    public long getGuessEndTime()
    {
        return guessEndTime;
    }

    public void setGuessEndTime(final long guessEndTime)
    {
        this.guessEndTime = guessEndTime;
    }

    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(final String gameId)
    {
        this.gameId = gameId;
    }

    public int getExplainerId()
    {
        return explainerId;
    }

    public void setExplainerId(final int explainerId)
    {
        this.explainerId = explainerId;
    }

    public Language getLang()
    {
        return lang;
    }

    public void setLang(final Language lang)
    {
        this.lang = lang;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(final boolean active)
    {
        this.active = active;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(final String word)
    {
        this.word = word;
    }
}
