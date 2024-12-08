package objects;

import enums.Intelligence;
import enums.Time;

public class People extends Alive {
    private int age;
    private Time time;
    private Intelligence intelligence;

    public People(String name, int age) {
        super(name);
        setAge(age);
    }

    public void setAge(int age) {
        if (age >= 0){
            this.age = age;
            updateIntelligence();
            updateTime();
        } else {
            throw new IllegalArgumentException("Age of " + getName() + " must be a positive integer");
        }
    }

    public void updateAge(int plusAge) {
        setAge((this.age + plusAge));
    }

    private void updateIntelligence() {
        if (this.age < 10) {
            this.intelligence = Intelligence.LOW;
        } else if (age < 18) {
            this.intelligence = Intelligence.MEDIUM;
        } else {
            this.intelligence = Intelligence.HIGH;
        }
    }

    private void updateTime() {
        switch (this.intelligence) {
            case LOW -> this.time = Time.SLOW;
            case MEDIUM -> this.time = Time.NORMAL;
            case HIGH -> this.time = Time.FAST;
        }
    }

    public int getAge() {
        return age;
    }

    public Time getTime() {
        return this.time;
    }

    public Intelligence getIntelligence() {
        return intelligence;
    }

    public void getIntelligencePrint() {
        System.out.println(getName() + " " + this.intelligence);
    }

    public void getTimePrint() {
        System.out.println("у " + getName() + " время тянется " + this.time);
    }
}
