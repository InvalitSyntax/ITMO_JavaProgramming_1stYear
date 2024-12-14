package objects.alive;

import exeptions.IllegalAgeSetting;

import java.util.Objects;

public abstract class AliveObject {
    private int age;
    private final String type;
    private final String name;

    public AliveObject(String name, int age, String type) {
        this.type = type;
        this.name = name;
        if (age < 0) {
            throw new IllegalAgeSetting("Возраст " + name +  " должен быть положительным");
        } else {
            this.age = age;
        }
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getType(){
        return type;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalAgeSetting("Возраст " + getName() +  " должен быть положительным");
        } else {
            this.age = age;
        }
    }

    public void updateAge(int plusAge) {
        if (getAge() + plusAge < 0){
            throw new IllegalAgeSetting("Возраст " + getName() +  " должен быть положительным");
        } else {
            setAge(getAge() + plusAge);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AliveObject that)) return false;
        if (!super.equals(obj)) return false;
        return age == that.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age, type, name);
    }

    @Override
    public String toString() {
        return "AliveObject{name='" + getName() + "', age=" + age + "}";
    }
}
