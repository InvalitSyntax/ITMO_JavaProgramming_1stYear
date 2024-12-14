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
        System.out.printf("%s %s думает о том, что пятнадцать суток ареста слишком мало за %s\n",
                capitalized(getType()), capitalized(getName()), violation);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Spectator spectator = (Spectator) o;
        return getName().equals(spectator.getName()) && getAge() == spectator.getAge() && getType().equals(spectator.getType());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getAge();
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Spectator{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", type='" + getType() + '\'' +
                '}';
    }
}
