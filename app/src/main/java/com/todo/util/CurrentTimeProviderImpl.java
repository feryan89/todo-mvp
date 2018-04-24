package com.todo.util;

import java.util.concurrent.TimeUnit;

public final class CurrentTimeProviderImpl implements CurrentTimeProvider {

    @Override
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public long getUnixTimestamp() {
        return TimeUnit.MILLISECONDS.toSeconds(getCurrentTimeMillis());
    }
}
