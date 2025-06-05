package org.example;

import java.io.ByteArrayInputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream.of("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")
                .filter(s -> s.length() < 8)
                .map(s -> s.del())
                .skip(2)
                .sorted()
                .forEachOrdered(System.out::print);
    }
}