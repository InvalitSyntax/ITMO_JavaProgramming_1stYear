import objects.*;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Violation.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ArrayList<String> namesList = new ArrayList<>(Arrays.asList(
                "Борислав", "Бранислав", "Велислав", "Владимир", "Владислав", "Всеволод", "Всеслав", "Добромир",
                "Добромил", "Иван", "Игорь", "Любомир", "Милослав", "Мирослав", "Млад", "Мстислав", "Олег", "Радослав",
                "Ростислав", "Рус", "Светозар", "Святослав", "Станислав", "Ярослав", "Благослава", "Бранислава",
                "Варвара", "Велислава", "Вера", "Влада", "Владислава", "Власта", "Вячеслава", "Дарина", "Добромила",
                "Доброслава", "Забава", "Зарина", "Купава", "Лада", "Леля", "Любовь", "Звенислава", "Злата", "Людмила",
                "Марья", "Милослава", "Мирослава", "Млада", "Мстислава", "Надежда", "Рада", "Радослава", "Росава",
                "Ростислава", "Светлана", "Снежана", "Станислава", "Ярослава"
        ));

        World world = new World();

        Random rand = new Random();
        int numberOfLittleMans = 3;
        for (int i = 0; i < numberOfLittleMans; i++) {
            int indexOfName = rand.nextInt(namesList.size());
            world.addObjToList(
                    new LittleMan(
                            namesList.get(indexOfName),
                            rand.nextInt(9))
            );
            namesList.remove(indexOfName);
        }

        int numberOfPoliceman = 2;
        for (int i = 0; i < numberOfPoliceman; i++) {
            int indexOfName = rand.nextInt(namesList.size());
            world.addObjToList(
                    new Policeman(
                            namesList.get(indexOfName),
                            rand.nextInt(18, 45))
            );
            namesList.remove(indexOfName);
        }
        world.addObjToList(new Person("некоторые", 25));
        world.addObjToList(new Word("арест"));

        /*System.out.println(world.getLittleManArrayList());
        System.out.println(world.getListPolicemen());
        System.out.println(world.getPersonArrayList());
        System.out.println(world.getWordArrayList());*/


        world.startSimulation();
    }
}