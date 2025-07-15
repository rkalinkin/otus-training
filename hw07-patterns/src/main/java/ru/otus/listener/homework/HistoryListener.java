package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> storage = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        var historyMsg = copyMessage(msg);
        storage.put(historyMsg.getId(), historyMsg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    private Message copyMessage(Message msg) {
        return msg.toBuilder().build();
    }
}
