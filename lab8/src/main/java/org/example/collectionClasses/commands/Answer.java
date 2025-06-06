package org.example.collectionClasses.commands;

import java.io.Serializable;

public record Answer(Object result, boolean condition) implements Serializable {
    @Override
    public String toString() {
        return result.toString();
    }
}