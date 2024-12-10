package objects;
import java.util.Objects;

public class Person extends Alive{
    private int age;
    public Person(String name, int age) {
        super(name);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNameCapitalized() {
        return getName().substring(0, 1).toUpperCase() + getName().substring(1);
    }
    @Override
    public String toString() {
        return "Человек{" +
                "имя='" + getNameCapitalized() + '\'' +
                ", возраст=" + age +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(getName(), person.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getName(), age);
    }
}
