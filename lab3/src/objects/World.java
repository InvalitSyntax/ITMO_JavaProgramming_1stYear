package objects;

import enums.Violation;
import exeptions.IllegalArrestException;
import interfaces.Awaiting;
import objects.actions.ArrestActions;
import objects.actions.Randoms;
import objects.actions.Do;
import objects.actions.Think;

import java.util.ArrayList;

public class World implements Awaiting {
    public World() {
        System.out.println("В мире где живут коротышки..");
    }

    private int time = 0;

    private final ArrayList<LittleMan> littleManArrayList = new ArrayList<>();
    private final ArrayList<Policeman> policemanArrayList = new ArrayList<>();
    private final ArrayList<Person> personArrayList = new ArrayList<>();
    private final ArrayList<Word> wordArrayList = new ArrayList<>();

    public void addObjToList(LittleMan littleMan) {
        littleManArrayList.add(littleMan);
    }

    public void addObjToList(Policeman policeman) {
        policemanArrayList.add(policeman);
    }

    public void addObjToList(Person person) {
        personArrayList.add(person);
    }

    public void addObjToList(Word word) {
        wordArrayList.add(word);
    }

    public ArrayList<Policeman> getListPolicemen() {
        return policemanArrayList;
    }

    public ArrayList<LittleMan> getLittleManArrayList() {
        return littleManArrayList;
    }

    public ArrayList<Person> getPersonArrayList() {
        return personArrayList;
    }

    public ArrayList<Word> getWordArrayList() {
        return wordArrayList;
    }

    public void startSimulation() {
        while (true) {
            for (LittleMan littleMan : littleManArrayList) {
                if (Randoms.isTrueWithChance(20)) {
                    Do.apply(littleMan, Randoms.getRandomEnum(Violation.class), time);
                }
            }
            for (Policeman policeman : policemanArrayList) {
                if (Randoms.isTrueWithChance(50)) {
                    for (LittleMan littleMan : littleManArrayList) {
                        if (littleMan.getViolationDetail() != null) {
                            try {
                                ArrestActions.arrest(policeman, littleMan);
                                for (Person person : personArrayList) {
                                    Think.apply(person, littleMan.getViolationDetail());
                                }
                            } catch (IllegalArrestException e) {
                                System.out.println("Ошибка: " + e.getMessage());
                            }
                        }
                    }
                }
            }
            for (LittleMan littleMan : littleManArrayList) {
                if (littleMan.getArrested()) {
                    Think.apply(littleMan, "как же " + littleMan.getTime() +
                            " тянется время, я правда сожалею о " + littleMan.getViolationDetail().violation());
                    if (time - littleMan.getViolationDetail().timeWhenDid() >= 5) {
                        ArrestActions.letGo(littleMan);
                    }
                } else {
                    littleMan.updateAge(Randoms.getRandomIntInRange(3, 7));
                }

            }

            int countOfNotViolated = 0;
            for (LittleMan littleMan : littleManArrayList) {
                if (!littleMan.getAvailableToViolate()) {
                    countOfNotViolated++;
                }
            }
            if (countOfNotViolated == littleManArrayList.size()) {
                System.out.println("Нарушителей порядка не осталось");
                for (Policeman policeman : policemanArrayList) {
                    policeman.setKnowHowToArrest(false);
                }
            }
            int countOfPolicemanWhoCantArrested = 0;
            for (Policeman policeman : policemanArrayList) {
                if (!policeman.getKnowHowToArrest()) {
                    countOfPolicemanWhoCantArrested++;
                }
            }
            if (countOfPolicemanWhoCantArrested == policemanArrayList.size()) {
                for (Word word : wordArrayList) {
                    word.setKnowledge(false);
                }
                return;
            }

            System.out.println("Время шло..." + "\n");
            sleep(4);

        }

    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            time += seconds;
        } catch (InterruptedException e) {
            System.out.println("Ожидание прервано: " + e.getMessage());
        }
    }
}
