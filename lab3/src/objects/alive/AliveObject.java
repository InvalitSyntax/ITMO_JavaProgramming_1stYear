package objects.alive;

import objects.MyObject;

public abstract class AliveObject extends MyObject {
    private int age;

    public AliveObject(String name, int age) {
        super(name);
        if (age < 0) {
            throw new IllegalArgumentException("Возраст" + name +  "должен быть положительным");
        } else {
            this.age = age;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Возраст" + getName() +  "должен быть положительным");
        } else {
            this.age = age;
        }
    }

    public void updateAge(int plusAge) {
        if (getAge() + plusAge < 0){
            throw new IllegalArgumentException("Возраст" + getName() +  "должен быть положительным");
        } else {
            setAge(getAge() + plusAge);
        }
    }
}
