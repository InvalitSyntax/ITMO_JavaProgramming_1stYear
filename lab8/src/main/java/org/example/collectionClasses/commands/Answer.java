package org.example.collectionClasses.commands;

import java.io.Serializable;
import java.util.List;
import org.example.collectionClasses.model.SpaceMarine;

public class Answer implements Serializable {
    private final Object result;
    private final boolean condition;
    private List<SpaceMarine> collection;

    public Answer(Object result, boolean condition) {
        this.result = result;
        this.condition = condition;
    }

    public Object result() { return result; }
    public boolean condition() { return condition; }
    public List<SpaceMarine> getCollection() { return collection; }
    public void setCollection(List<SpaceMarine> collection) { this.collection = collection; }

    @Override
    public String toString() {
        return result != null ? result.toString() : "";
    }
}