package objects.alive;

import enums.Intelligence;
import enums.TimeSpeed;
import enums.Violation;
import interfaces.*;
import records.ViolationDetail;

import java.util.Objects;

import static objects.Utils.capitalized;
import static objects.Utils.getRandomEnum;


public final class LittleMan extends AliveObject implements HasIntelligence, HasTimeSpeed, CanDoViolate {
    private TimeSpeed timeSpeed;
    private Intelligence intelligence;
    private boolean availableToViolate;
    private ViolationDetail violationDetail = null;
    private boolean arrested = false;

    public LittleMan(String name, int age) {
        super(name, age, "коротышка");
    }

    public LittleMan(String name, int age, String type) {
        super(name, age, type);
    }

    @Override
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Возраст " + getAge() +  " должен быть положительным");
        } else {
            super.setAge(age);
            updateIntelligence();
            updateTimeSpeed();
            updateAvailableToViolate();
        }
    }

    @Override
    public void updateIntelligence() {
        if (getAge() < 10) {
            this.intelligence = Intelligence.LOW;
        } else if (getAge() < 18) {
            if (this.intelligence != Intelligence.MEDIUM) {
                this.intelligence = Intelligence.MEDIUM;
                System.out.printf("%s %s стал %s\n", capitalized(getType()), capitalized(getName()), this.intelligence);
            }

        } else {
            if (this.intelligence != Intelligence.HIGH) {
                this.intelligence = Intelligence.HIGH;
                System.out.printf("%s %s стал %s\n", capitalized(getType()), capitalized(getName()), this.intelligence);
            }
        }
    }

    @Override
    public void updateTimeSpeed() {
        switch (this.intelligence) {
            case LOW -> this.timeSpeed = TimeSpeed.SLOW;
            case MEDIUM -> this.timeSpeed = TimeSpeed.NORMAL;
            case HIGH -> this.timeSpeed = TimeSpeed.FAST;
        }
    }

    private void updateAvailableToViolate() {
        this.availableToViolate = this.intelligence != Intelligence.HIGH;
    }

    @Override
    public void doViolate(int timeOfViolate) {
        Violation violation = getRandomEnum(Violation.class);
        if (this.availableToViolate & !this.arrested) {
            System.out.printf("%s %s делать %s\n",capitalized(getType()), capitalized(getName()), violation);
            violationDetail = new ViolationDetail(violation, timeOfViolate);
        } else {
            System.out.printf("%s %s больше не делать %s\n",capitalized(getType()), capitalized(getName()), violation);
        }
    }

    public void thinkAboutViolation() {
        if (this.arrested){
            System.out.printf("%s %s думает о том, как же %s тянется время, я правда сожалею о %s\n",
                    capitalized(getType()), capitalized(getName()), this.timeSpeed, this.violationDetail.violation());
        }
    }

    public boolean getArrested() {
        return arrested;
    }
    public boolean getAvailableToViolate() {
        return availableToViolate;
    }

    public void setArrested(boolean arrested) {
        this.arrested = arrested;
    }

    public ViolationDetail getViolationDetail() {
        return violationDetail;
    }

    public void clearViolationDetail() {
        this.violationDetail = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LittleMan littleMan = (LittleMan) o;
        return availableToViolate == littleMan.availableToViolate &&
                arrested == littleMan.arrested &&
                (Objects.equals(violationDetail, littleMan.violationDetail));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (availableToViolate ? 1 : 0);
        result = 31 * result + (arrested ? 1 : 0);
        result = 31 * result + (violationDetail != null ? violationDetail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LittleMan{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", type='" + getType() + '\'' +
                ", timeSpeed=" + timeSpeed +
                ", intelligence=" + intelligence +
                ", availableToViolate=" + availableToViolate +
                ", arrested=" + arrested +
                ", violationDetail=" + violationDetail +
                '}';
    }
}
