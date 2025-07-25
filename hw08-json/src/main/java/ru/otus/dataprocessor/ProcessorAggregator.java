package ru.otus.dataprocessor;

import java.util.*;
import java.util.stream.Collectors;
import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        // группирует выходящий список по name, при этом суммирует поля value
        if (data == null) {
            return Collections.emptyMap();
        }
        return data.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Measurement::name,
                        Measurement::value,
                        Double::sum,
                        TreeMap::new));
    }
}
