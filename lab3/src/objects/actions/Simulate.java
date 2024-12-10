package objects.actions;

import exeptions.IllegalArrestException;
import objects.*;

public class Simulate {
    public static void startSimulation(World world, int sleepTime) {
        int localTime = 0;
        while (true) {
            for (LittleMan littleMan : world.getLittleManArrayList()) {
                if (Randoms.isTrueWithChance(20)) {
                    littleMan.doViolate(localTime);
                }
            }
            for (Policeman policeman : world.getListPolicemen()) {
                if (Randoms.isTrueWithChance(50)) {
                    for (LittleMan littleMan : world.getLittleManArrayList()) {
                        if (littleMan.getViolationDetail() != null) {
                            try {
                                policeman.arrest(littleMan);
                                for (Person person : world.getPersonArrayList()) {
                                    person.thinkAboutViolation(littleMan.getViolationDetail());
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
                    littleMan.thinkAboutViolation(littleMan.getViolationDetail());
                    if (localTime - littleMan.getViolationDetail().timeWhenDid() >= 5) {
                        Policeman.letGo(littleMan);
                    }
                } else {
                    littleMan.updateAge(Randoms.getRandomIntInRange(3, 7));
                }

            }

            int countOfNotViolated = 0;
            for (LittleMan littleMan : world.getLittleManArrayList()) {
                if (!littleMan.getAvailableToViolate()) {
                    countOfNotViolated++;
                }
            }
            if (countOfNotViolated == world.getLittleManArrayList().size()) {
                System.out.println("Нарушителей порядка не осталось");
                for (Policeman policeman : world.getListPolicemen()) {
                    policeman.setKnowHowToArrest(false);
                }
            }
            int countOfPolicemanWhoCantArrested = 0;
            for (Policeman policeman : world.getListPolicemen()) {
                if (!policeman.getKnowHowToArrest()) {
                    countOfPolicemanWhoCantArrested++;
                }
            }
            if (countOfPolicemanWhoCantArrested == world.getListPolicemen().size()) {
                for (Word word : world.getWordArrayList()) {
                    word.setKnowledge(false);
                }
                return;
            }

            System.out.println("""
                    
                    Время шло...
                    
                    """);
            try {
                Thread.sleep(sleepTime * 1000L);
                localTime += sleepTime;
            } catch (InterruptedException e) {
                System.out.println("Ожидание прервано: " + e.getMessage());
            }
        }
    }
}
