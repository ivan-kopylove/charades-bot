package com.github.lazyf1sh.sandbox.runner;

import java.util.concurrent.atomic.AtomicInteger;

public class GlobalState
{
    public static AtomicInteger LAST_PROCESSED_ID = new AtomicInteger(0);
}
