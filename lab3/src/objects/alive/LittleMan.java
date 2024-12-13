package objects.alive;

import enums.Intelligence;
import enums.TimeSpeed;
import enums.Violation;
import interfaces.*;
import records.ViolationDetail;

import static interfaces.Utils.capitalized;


public final class LittleMan extends AliveObjectWithType implements HasIntelligence, HasTimeSpeed, CanDoViolate {
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
            throw new IllegalArgumentException("Возраст" + getAge() +  "должен быть положительным");
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
                System.out.println(capitalized(getType()) + capitalized(getName()) + " стал " + this.intelligence);
            }

        } else {
            if (this.intelligence != Intelligence.HIGH) {
                this.intelligence = Intelligence.HIGH;
                System.out.println(capitalized(getType()) + capitalized(getName()) + " стал " + this.intelligence);
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
        Violation violation = Utils.getRandomEnum(Violation.class);
        if (this.availableToViolate & !this.arrested) {
            System.out.println(capitalized(getType()) + capitalized(getName()) + " делать " + violation);
            violationDetail = new ViolationDetail(violation, timeOfViolate);
        } else {
            System.out.println(capitalized(getType()) + capitalized(getName()) + " больше не делает " + violation);
        }
    }

    public void thinkAboutViolation() {
        if (this.arrested){
            System.out.println(capitalized(getType()) + capitalized(getName()) + " думает о том, как же " + this.timeSpeed +
                    " тянется время, я правда сожалею о " + this.violationDetail.violation());
        }
    }

    public boolean getArrested() {
        return arrested;
    }

    public void setArrested(boolean arrested) {
        this.arrested = arrested;
    }

    public ViolationDetail getViolationDetail() {
        return violationDetail;
    }
}
