package objects;

import java.util.Objects;

public abstract class MyObject {
    private final String name;
    public MyObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MyObject myObject = (MyObject) obj;
        return Objects.equals(name, myObject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MyObject{name='" + name + "'}";
    }
}
