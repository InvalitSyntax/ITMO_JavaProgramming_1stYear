package objects;
import interfaces.CanThinkAboutViolation;
import interfaces.HasAge;
import records.ViolationDetail;

import java.util.Objects;

public class Person extends Alive implements HasAge, CanThinkAboutViolation {
    private int age;
    public Person(String name, int age) {
        super(name);
        this.age = age;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    public String getNameWithType(){
        return getName();
    }

    public String getNameCapitalized() {
        return getNameWithType().substring(0, 1).toUpperCase() + getNameWithType().substring(1);
    }

    public void thinkAboutViolation(ViolationDetail violationDetail) {
        System.out.println(getNameCapitalized() +
                " думать, что пятнадцать суток ареста - это слишком небольшой срок за "
                + violationDetail.violation());
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
