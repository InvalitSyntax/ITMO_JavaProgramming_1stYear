package org.example.collectionClasses.commands;

import java.io.Serializable;

public record Answer(Object result) implements Serializable {
    @Override
    public String toString() {
        return result.toString();
    }
}