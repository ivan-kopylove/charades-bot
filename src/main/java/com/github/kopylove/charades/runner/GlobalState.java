package com.github.kopylove.charades.runner;

import java.util.concurrent.atomic.AtomicInteger;

public final class GlobalState
{
    public static AtomicInteger LAST_PROCESSED_ID = new AtomicInteger(0);

    private GlobalState() {}
}
