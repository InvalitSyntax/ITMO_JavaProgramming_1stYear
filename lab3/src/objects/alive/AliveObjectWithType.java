package objects.alive;

public class AliveObjectWithType extends AliveObject{
    private final String type;

    public AliveObjectWithType(String name, int age, String type) {
        super(name, age);
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AliveObjectWithType that = (AliveObjectWithType) o;
        return getAge() == that.getAge() &&
                getName().equals(that.getName()) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getAge();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AliveObjectWithType{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", type='" + type + '\'' +
                '}';
    }
}
