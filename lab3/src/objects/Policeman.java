package objects;

import exeptions.IllegalArrestException;
import interfaces.CanArrest;

import java.util.Objects;

public class Policeman extends Person implements CanArrest {
    private boolean knowHowToArrest = true;

    public Policeman(String name, int age) {
        super(name, age);
    }

    public void arrest(LittleMan littleMan) throws IllegalArrestException {
        if (getKnowHowToArrest()) {
            if (littleMan.getArrested()) {
                throw new IllegalArrestException("Нельзя вновь арестовать уже посаженного " + littleMan);
            }
            System.out.println(getNameCapitalized() +
                    " арестовывает " + littleMan.getName() + " за "
                    + littleMan.getViolationDetail().violation());

            littleMan.setArrested(true);
        } else {
            System.out.println(getNameCapitalized() + " не знает как арестовывать");
        }
    }

    public static void letGo(LittleMan littleMan) {
        littleMan.setArrested(false);
        littleMan.clearViolationDetail();
        System.out.println(littleMan.getNameCapitalized() + " отпускают");
    }

    @Override
    public boolean getKnowHowToArrest() {
        return knowHowToArrest;
    }

    @Override
    public void setKnowHowToArrest(boolean knowHowToArrest) {
        this.knowHowToArrest = knowHowToArrest;
        if (!knowHowToArrest) {
            System.out.println(getNameCapitalized() + " не знает как арестовывать");
        }
    }

    @Override
    public String getNameWithType() {
        return "полицейский " + getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Проверяем ссылочное равенство
        if (o == null || getClass() != o.getClass()) return false; // Проверка класса
        if (!super.equals(o)) return false; // Проверка родительского класса
        Policeman policeman = (Policeman) o;
        return knowHowToArrest == policeman.knowHowToArrest; // Сравниваем поле knowHowToArrest
    }

    // Переопределение hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), knowHowToArrest); // Учет поля knowHowToArrest
    }

    // Переопределение toString()
    @Override
    public String toString() {
        return "Policeman{" +
                "name='" + getNameCapitalized() + '\'' +
                ", age=" + getAge() +
                ", knowHowToArrest=" + knowHowToArrest +
                '}';
    }
}
