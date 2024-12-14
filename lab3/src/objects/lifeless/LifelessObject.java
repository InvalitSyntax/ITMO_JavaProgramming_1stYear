package objects.lifeless;

public abstract class LifelessObject {
    private final String type;
    private final String name;


    public LifelessObject(String name, String type) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
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
