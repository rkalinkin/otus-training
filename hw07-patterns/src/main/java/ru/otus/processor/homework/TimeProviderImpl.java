package ru.otus.processor.homework;

public class TimeProviderImpl implements TimeProvider {

    @Override
    public long getSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
