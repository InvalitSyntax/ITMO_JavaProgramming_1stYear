package objects;

import interfaces.HasName;

import java.util.Objects;

abstract class ObjWithName implements HasName {
    private final String name;

    ObjWithName(String name) {
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
        ObjWithName objWithName = (ObjWithName) o;
        return Objects.equals(name, objWithName.name);
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