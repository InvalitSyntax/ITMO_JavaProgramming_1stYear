package objects.lifeless;

import objects.MyObject;

public abstract class LifelessObject extends MyObject {
    private final String type;

    public LifelessObject(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LifelessObject that = (LifelessObject) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + type.hashCode();
    }

    @Override
    public String toString() {
        return "LifelessObject{" +
                "name='" + getName() + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
