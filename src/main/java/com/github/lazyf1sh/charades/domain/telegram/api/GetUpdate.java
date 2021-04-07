package com.github.lazyf1sh.charades.domain.telegram.api;



import java.util.List;

public class GetUpdate extends BaseApiType
{
    public boolean      ok;
    public List<Update> result;

    public boolean isOk()
    {
        return ok;
    }

    public void setOk(boolean ok)
    {
        this.ok = ok;
    }

    public List<Update> getResult()
    {
        return result;
    }

    public void setResult(List<Update> result)
    {
        this.result = result;
    }
}

