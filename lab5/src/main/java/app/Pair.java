package app;

import java.util.Scanner;

public class Pair<E, T> {
    private E first;
    private T second;

    public Pair(E name, T value) {
        this.first = name;
        this.second = value;
    }

    public E getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}
