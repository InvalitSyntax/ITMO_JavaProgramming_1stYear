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

    void setKnowHowToArrest(boolean knowHowToArrest) {
        if (this.knowHowToArrest != knowHowToArrest) {
            if (knowHowToArrest) {
                System.out.println(capitalized(getType()) + capitalized(getName()) + " узнает как арестовывать ");
            } else {
                System.out.println(capitalized(getType()) + capitalized(getName()) + " забывает как арестовывать ");
            }
        }
        this.knowHowToArrest = knowHowToArrest;
    }

    @Override
    public void arrest(LittleMan littleMan) throws IllegalArrestException {
        if (this.knowHowToArrest) {
            if (littleMan.getArrested()) {
                throw new IllegalArrestException("Нельзя вновь арестовать уже посаженного " + littleMan);
            }
            System.out.printf("%s %s арестовывает %s %s за %s",
                    getType(), capitalized(getName()),
                    capitalized(littleMan.getType()), capitalized(littleMan.getName()), littleMan.getViolationDetail().violation());

            littleMan.setArrested(true);
        } else {
            System.out.printf("%s %s не знает как арестовывать", getType(), capitalized(getName()));
        }
    }

    @Override
    public void letGo(LittleMan littleMan) {

    }
}
