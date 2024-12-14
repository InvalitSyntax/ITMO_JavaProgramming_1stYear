package objects;

import objects.alive.LittleMan;
import objects.alive.Policeman;
import objects.alive.Spectator;
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

    private final ArrayList<LittleMan> littleManArrayList = new ArrayList<>();
    private final ArrayList<Policeman> policemanArrayList = new ArrayList<>();
    private final ArrayList<Spectator> spectatorsArrayList = new ArrayList<>();
    private final ArrayList<Word> wordsArrayList = new ArrayList<>();

    public void initWorld(int numberOfLittleMans, int numberOfPoliceman) {
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
            int indexOfName = rand.nextInt(namesList.size());
            instance.littleManArrayList.add(
                    new LittleMan(
                            namesList.get(indexOfName),
                            rand.nextInt(9))
            );
            namesList.remove(indexOfName);
        }

        for (int i = 0; i < numberOfPoliceman; i++) {
            int indexOfName = rand.nextInt(namesList.size());
            instance.policemanArrayList.add(
                    new Policeman(
                            namesList.get(indexOfName),
                            rand.nextInt(18, 45))
            );
            namesList.remove(indexOfName);
        }
        instance.spectatorsArrayList.add(new Spectator("некоторые", 25));
        instance.wordsArrayList.add(new Word("арест", true));
    }

    private World() {
        System.out.println("В мире где живут коротышки..");
    }

    public ArrayList<Policeman> getListPolicemen() {
        return policemanArrayList;
    }

    public ArrayList<LittleMan> getLittleManArrayList() {
        return littleManArrayList;
    }

    public ArrayList<Spectator> getSpectatorsArrayList() {
        return spectatorsArrayList;
    }

    public ArrayList<Word> getWordArrayList() {
        return wordsArrayList;
    }
}
