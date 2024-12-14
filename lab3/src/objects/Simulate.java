package objects;

import exeptions.IllegalAgeSetting;
import exeptions.IllegalArrestException;
import interfaces.GetBooleanFromArrayAndFunc;
import interfaces.Utils;
import objects.alive.LittleMan;
import objects.alive.Policeman;
import objects.alive.Spectator;
import objects.lifeless.Word;

import static interfaces.GetBooleanFromArrayAndFunc.getBooleansFromArray;
import static interfaces.Utils.getRandomIntInRange;

public class Simulate {
    public static void startSimulation(World world, int sleepTime) {
        int localTime = 0;
        GetBooleanFromArrayAndFunc<LittleMan> getBooleanFromLittleMan = (arrayList, function) ->
                getBooleansFromArray(arrayList, function, false);

        GetBooleanFromArrayAndFunc<Policeman> getBooleanFromPoliceman = (arrayList, function) ->
                getBooleansFromArray(arrayList, function, false);

        while (true) {
            for (LittleMan littleMan : world.getLittleManArrayList()) {
                if (Utils.isTrueWithChance(20)) {
                    littleMan.doViolate(localTime);
                }
            }
            for (Policeman policeman : world.getListPolicemen()) {
                for (LittleMan littleMan : world.getLittleManArrayList()) {
                    if (littleMan.getViolationDetail() != null) {
                        if (Utils.isTrueWithChance(50)) {
                            try {
                                policeman.arrest(littleMan);
                                for (Spectator spectator : world.getSpectatorsArrayList()) {
                                    spectator.thinkAboutSomebodyViolation(littleMan.getViolationDetail().violation());
                                }
                            } catch (IllegalArrestException e) {
                                System.out.println("Ошибка: " + e.getMessage());
                            }
                        }
                    }
                }
            }
            for (LittleMan littleMan : world.getLittleManArrayList()) {
                if (littleMan.getArrested()) {
                    littleMan.thinkAboutViolation();
                    if (localTime - littleMan.getViolationDetail().timeWhenItDid() >= 5) {
                        Policeman.letGo(littleMan);
                    }
                } else {
                    try {
                        littleMan.updateAge(getRandomIntInRange(1, 6));
                    }
                    catch (IllegalAgeSetting e) {
                        System.out.println("Ошибка: " + e.getMessage());
                        //e.printStackTrace();
                    }

                }

            }

            if (getBooleanFromLittleMan.apply(world.getLittleManArrayList(), LittleMan::getAvailableToViolate)) {
                System.out.println("Нарушителей порядка не осталось");
                for (Policeman policeman : world.getListPolicemen()) {
                    policeman.setKnowHowToArrest(false);
                }
            }

            if (getBooleanFromPoliceman.apply(world.getListPolicemen(), Policeman::getKnowHowToArrest)) {
                for (Word word : world.getWordArrayList()) {
                    word.setKnown(false);
                }
                return;
            }

            System.out.println("""
                    
                    Время шло...
                    
                    """);

            simulationSleep(sleepTime);
            localTime += sleepTime;
        }
    }

    private static void simulationSleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime * 1000L);

        } catch (InterruptedException e) {
            System.out.println("Ожидание прервано: " + e.getMessage());
        }
    }
}
