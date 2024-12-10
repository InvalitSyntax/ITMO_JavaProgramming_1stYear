package objects;

import java.util.Objects;

public class Policeman extends Person {
    private boolean knowHowToArrest = true;

    public Policeman(String name, int age) {
        super(name, age);
    }

    public void getKnowHowToArrestPrint() {
        if (knowHowToArrest) {
            System.out.println(getNameCapitalized() + " знает как арестовывать.");
        } else {
            System.out.println(getNameCapitalized() + " не знает как арестовывать");
        }
    }

    public boolean getKnowHowToArrest() {
        return knowHowToArrest;
    }

    public void setKnowHowToArrest(boolean knowHowToArrest) {
        this.knowHowToArrest = knowHowToArrest;
        if (!knowHowToArrest) {
            getKnowHowToArrestPrint();
        }
    }

        @Override
        public String getName() {
            return "полицейский " + super.getName();
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
