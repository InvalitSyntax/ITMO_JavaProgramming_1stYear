package objects.alive;


import exeptions.IllegalArrestException;
import interfaces.CanArrest;

import static interfaces.Utils.capitalized;

public final class Policeman extends AliveObjectWithType implements CanArrest {
    private boolean knowHowToArrest = true;

    public Policeman(String name, int age) {
        super(name, age, "полицейский");
    }

    public Policeman(String name, int age, String type) {
        super(name, age, type);
    }

    public void setKnowHowToArrest(boolean knowHowToArrest) {
        if (this.knowHowToArrest != knowHowToArrest) {
            if (knowHowToArrest) {
                System.out.printf("%s %s узнает как арестовывать\n", capitalized(getType()), capitalized(getName()));
            } else {
                System.out.printf("%s %s забывает как арестовывать\n", capitalized(getType()), capitalized(getName()));
            }
        }
        this.knowHowToArrest = knowHowToArrest;
    }

    @Override
    public void arrest(LittleMan littleMan) throws IllegalArrestException {
        if (this.knowHowToArrest) {
            if (littleMan.getArrested()) {
                throw new IllegalArrestException(
                        String.format("Нельзя вновь арестовать уже посаженного %s %s\n",
                                capitalized(littleMan.getType()), capitalized(littleMan.getName())));
            }
            System.out.printf("%s %s арестовывает %s %s за %s\n",
                    getType(), capitalized(getName()),
                    capitalized(littleMan.getType()), capitalized(littleMan.getName()), littleMan.getViolationDetail().violation());

            littleMan.setArrested(true);
        } else {
            System.out.printf("%s %s не знает как арестовывать\n", getType(), capitalized(getName()));
        }
    }
    public boolean getKnowHowToArrest() {
        return knowHowToArrest;
    }

    public static void letGo(LittleMan littleMan) {
        littleMan.setArrested(false);
        littleMan.clearViolationDetail();
        System.out.printf("%s %s отпускают\n", capitalized(littleMan.getType()), capitalized(littleMan.getName()));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Policeman policeman = (Policeman) o;
        return knowHowToArrest == policeman.knowHowToArrest;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + (knowHowToArrest ? 1 : 0);
    }

    @Override
    public String toString() {
        return "Policeman{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", type='" + getType() + '\'' +
                ", knowHowToArrest=" + knowHowToArrest +
                '}';
    }
}
