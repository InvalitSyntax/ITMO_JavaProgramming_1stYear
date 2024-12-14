package objects;

import objects.alive.AliveObject;
import objects.alive.LittleMan;
import objects.alive.Policeman;
import objects.alive.Spectator;
import objects.lifeless.LifelessObject;
import objects.lifeless.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class World {
    private static World instance;
    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    private final ArrayList<AliveObject> aliveObjectArrayList = new ArrayList<>();
    private final ArrayList<LifelessObject> lifelessObjectArrayList = new ArrayList<>();

    public void initWorld(int numberOfLittleMans, int numberOfPoliceman) {
        aliveObjectArrayList.clear();
        lifelessObjectArrayList.clear();
        ArrayList<String> namesList = new ArrayList<>(Arrays.asList(
                "Борислав", "Бранислав", "Велислав", "Владимир", "Владислав", "Всеволод", "Всеслав", "Добромир",
                "Добромил", "Иван", "Игорь", "Любомир", "Милослав", "Мирослав", "Млад", "Мстислав", "Олег", "Радослав",
                "Ростислав", "Рус", "Светозар", "Святослав", "Станислав", "Ярослав", "Благослава", "Бранислава",
                "Варвара", "Велислава", "Вера", "Влада", "Владислава", "Власта", "Вячеслава", "Дарина", "Добромила",
                "Доброслава", "Забава", "Зарина", "Купава", "Лада", "Леля", "Любовь", "Звенислава", "Злата", "Людмила",
                "Марья", "Милослава", "Мирослава", "Млада", "Мстислава", "Надежда", "Рада", "Радослава", "Росава",
                "Ростислава", "Светлана", "Снежана", "Станислава", "Ярослава"
        ));

        Random rand = new Random();
        for (int i = 0; i < numberOfLittleMans; i++) {
            try {
                int indexOfName = rand.nextInt(namesList.size());
                instance.aliveObjectArrayList.add(
                        new LittleMan(
                                namesList.get(indexOfName),
                                rand.nextInt(9))
                );
                namesList.remove(indexOfName);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + " установлено максимальное кол-во коротышек");
            }

        }

        for (int i = 0; i < numberOfPoliceman; i++) {
            try {
                int indexOfName = rand.nextInt(namesList.size());
                instance.aliveObjectArrayList.add(
                        new Policeman(
                                namesList.get(indexOfName),
                                rand.nextInt(18, 45))
                );
                namesList.remove(indexOfName);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + " установлено максимальное кол-во полицейских");
            }

        }
        instance.aliveObjectArrayList.add(new Spectator("некоторые", 25));
        instance.lifelessObjectArrayList.add(new Word("арест", true));
    }

    private World() {
        System.out.println("В мире где живут коротышки..");
    }

    public ArrayList<AliveObject> getAliveObjectArrayList() {
        return aliveObjectArrayList;
    }

    public ArrayList<LifelessObject> getLifelessObjectArrayList() {
        return lifelessObjectArrayList;
    }
}
