package objects;

import enums.Intelligence;
import enums.Time;
import enums.Violation;
import interfaces.CanDoViolate;
import objects.actions.Randoms;
import records.ViolationDetail;

import java.util.Objects;

public class LittleMan extends Person implements CanDoViolate {
    private Time time;
    private Intelligence intelligence;
    private boolean availableToViolate;
    private ViolationDetail violationDetail = null;
    private boolean arrested = false;

    public LittleMan(String name, int age) {
        super(name, age);
        setAge(super.getAge());
    }

    @Override
    public void setAge(int age) {
        if (age >= 0){
            super.setAge(age);
            updateIntelligence();
            updateTime();
            updateAvailableToViolate();
        }
    }

    public void updateAge(int plusAge) {
        setAge((super.getAge() + plusAge));
    }

    private void updateIntelligence() {
        if (super.getAge() < 10) {
            this.intelligence = Intelligence.LOW;
        } else if (super.getAge() < 18) {
            if (this.intelligence != Intelligence.MEDIUM){
                this.intelligence = Intelligence.MEDIUM;
                System.out.println(getNameCapitalized() + " стал " + this.intelligence);
            }

        } else {
            if (this.intelligence != Intelligence.HIGH){
                this.intelligence = Intelligence.HIGH;
                System.out.println(getNameCapitalized() + " стал " + this.intelligence);
            }
        }
    }

    private void updateTime() {
        switch (this.intelligence) {
            case LOW -> this.time = Time.SLOW;
            case MEDIUM -> this.time = Time.NORMAL;
            case HIGH -> this.time = Time.FAST;
        }
    }

    @Override
    public void doViolate(int timeOfViolate) {
        Violation violation = Randoms.getRandomEnum(Violation.class);
        if (getAvailableToViolate() & !getArrested()){
            System.out.println(getNameCapitalized() + " делать " + violation);
            setViolationDetail(new ViolationDetail(violation, timeOfViolate));
        } else {
            System.out.println(getNameCapitalized() + " больше не делает " + violation);
        }
    }

    @Override
    public void thinkAboutViolation(ViolationDetail violationDetail) {
        if (getArrested()){
            System.out.println(getNameCapitalized() + " думает о том, как же " + getTime() +
                    " тянется время, я правда сожалею о " + getViolationDetail().violation());
        }
    }

    private void updateAvailableToViolate() {
        this.availableToViolate = this.intelligence != Intelligence.HIGH;
    }

    public boolean getAvailableToViolate() {
        return availableToViolate;
    }

    public ViolationDetail getViolationDetail() {
        return violationDetail;
    }

    public void clearViolationDetail() {
        violationDetail = null;
    }

    public void setViolationDetail(ViolationDetail violationDetail) {
        this.violationDetail = violationDetail;
    }

    public void setArrested(boolean arrested) {
        this.arrested = arrested;
    }

    public boolean getArrested() {
        return arrested;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String getNameWithType(){
        return "коротышка " + getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LittleMan littleMan = (LittleMan) o;
        return availableToViolate == littleMan.availableToViolate &&
                arrested == littleMan.arrested &&
                time == littleMan.time &&
                intelligence == littleMan.intelligence &&
                Objects.equals(violationDetail, littleMan.violationDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), time, intelligence, availableToViolate, violationDetail, arrested);
    }

    @Override
    public String toString() {
        return "LittleMan{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", time=" + time +
                ", intelligence=" + intelligence +
                ", availableToViolate=" + availableToViolate +
                ", violationDetail=" + violationDetail +
                ", arrested=" + arrested +
                '}';
    }
}
