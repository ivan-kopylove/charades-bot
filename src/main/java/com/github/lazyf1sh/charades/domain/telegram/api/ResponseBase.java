package com.github.lazyf1sh.charades.domain.telegram.api;

public class ResponseBase
{
    public boolean ok;

    public boolean isOk()
    {
        return ok;
    }

    public void setOk(final boolean ok)
    {
        this.ok = ok;
    }
}
