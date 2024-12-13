package objects.alive;

import enums.Violation;

import static interfaces.Utils.capitalized;

public final class Spectator extends AliveObjectWithType {

    public Spectator(String name, int age) {
        super(name, age, "наблюдатель");
    }

    public Spectator(String name, int age, String type) {
        super(name, age, type);
    }

    public void thinkAboutSomebodyViolation(Violation violation) {
        System.out.println(capitalized(getType()) +
                " думает о том, что пятнадцать суток ареста слишком мало за "
                + violation);
    }
}
