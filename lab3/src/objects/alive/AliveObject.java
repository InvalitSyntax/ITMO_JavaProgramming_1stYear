package objects.alive;

import exeptions.IllegalAgeSetting;
import objects.MyObject;

import java.util.Objects;

public abstract class AliveObject extends MyObject {
    private int age;

    public AliveObject(String name, int age) {
        super(name);
        if (age < 0) {
            throw new IllegalAgeSetting("Возраст " + name +  " должен быть положительным");
        } else {
            this.age = age;
        }
    }

    public int getAge() {
        return age;
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
    abstract String getType();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AliveObject that)) return false;
        if (!super.equals(obj)) return false;
        return age == that.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age);
    }

    @Override
    public String toString() {
        return "AliveObject{name='" + getName() + "', age=" + age + "}";
    }
}
