package ru.otus;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class HelloOtus {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        System.out.println("Current Java version is: " + System.getProperty("java.version") + "\n");

        for (int i = 1; i <= 5; i++) {
            System.out.printf("i=%s ", i);
        }
        System.out.println();

        ImmutableMap<String, Integer> ages = ImmutableMap.of(
            "Alice", 30,
            "Bob", 25,
            "Charlie", 35
        );
        System.out.println(ages.get("Bob"));

        List<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 10;
        for (int i = min; i < max; i++) {
            example.add(i);
        }
        System.out.println(Lists.reverse(example));
    }
}
