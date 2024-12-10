package objects;

import interfaces.HasName;

import java.util.Objects;

abstract class Alive implements HasName {
    private final String name;

    Alive(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    abstract public String getNameCapitalized();
    abstract public String getNameWithType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alive alive = (Alive) o;
        return Objects.equals(name, alive.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Живое{" +
                "name='" + name + '\'' +
                '}';
    }
}