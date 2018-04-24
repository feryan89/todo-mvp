package com.todo.util;

public interface CurrentTimeProvider {

    long getCurrentTimeMillis();

    long getUnixTimestamp();
}
