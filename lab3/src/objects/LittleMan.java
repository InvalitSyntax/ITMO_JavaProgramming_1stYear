package objects;

import enums.Intelligence;
import records.ViolationDetail;

public class LittleMan extends Person {
    private Intelligence intelligence;
    private boolean availableToViolate;
    private ViolationDetail violationDetail;

    public LittleMan(String name, int age) {
        super(name, age);
        updateIntelligence();
        updateAvailableToViolate();
    }

    private void updateIntelligence() {
        if (getAge() < 10) {
            intelligence = Intelligence.LOW;
        } else if (getAge() < 18) {
            intelligence = Intelligence.MEDIUM;
        } else {
            intelligence = Intelligence.HIGH;
        }
    }

    private void updateAvailableToViolate() {
        availableToViolate = intelligence != Intelligence.HIGH;
    }

    public boolean isAvailableToViolate() {
        return availableToViolate;
    }

    public void setViolationDetail(ViolationDetail detail) {
        this.violationDetail = detail;
    }

    @Override
    public void performAction() {
        if (availableToViolate) {
            System.out.println(getName() + " нарушил правила!");
        } else {
            System.out.println(getName() + " ведет себя хорошо.");
        }
    }
}

