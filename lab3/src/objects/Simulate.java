package objects;

import exeptions.IllegalAgeSetting;
import exeptions.IllegalArrestException;
import interfaces.GetBooleanFromArrayAndFunc;
import objects.alive.AliveObject;
import objects.alive.LittleMan;
import objects.alive.Policeman;
import objects.alive.Spectator;
import objects.lifeless.LifelessObject;
import objects.lifeless.Word;

import static interfaces.GetBooleanFromArrayAndFunc.getBooleansFromArray;
import static objects.Utils.getRandomIntInRange;

public class Simulate {
    public static void startSimulation(World world, int sleepTime) {
        int localTime = 0;
        GetBooleanFromArrayAndFunc<AliveObject,LittleMan>
                getBooleanFromLittleMan = (arrayList, function)->
                getBooleansFromArray(arrayList, LittleMan.class, function, false);

        GetBooleanFromArrayAndFunc<AliveObject, Policeman>
                getBooleanFromPoliceman = (arrayList, function) ->
                getBooleansFromArray(arrayList, Policeman.class, function, false);

        while (true) {
            for (AliveObject aliveObject : world.getAliveObjectArrayList()) {
                if (aliveObject instanceof LittleMan) {
                    if (Utils.isTrueWithChance(20)) {
                        ((LittleMan) aliveObject).doViolate(localTime);
                    }
                }
            }
            for (AliveObject aliveObject1 : world.getAliveObjectArrayList()) {
                if (aliveObject1 instanceof Policeman) {
                    for (AliveObject aliveObject2 : world.getAliveObjectArrayList()) {
                        if (aliveObject2 instanceof LittleMan) {
                            if (((LittleMan)aliveObject2).getViolationDetail() != null) {
                                if (Utils.isTrueWithChance(50)) {
                                    try {
                                        ((Policeman) aliveObject1).arrest((LittleMan)aliveObject2);
                                        for (AliveObject aliveObject3 : world.getAliveObjectArrayList()) {
                                            if (aliveObject3 instanceof Spectator) {
                                                ((Spectator)aliveObject3).thinkAboutSomebodyViolation(
                                                        ((LittleMan)aliveObject2).getViolationDetail().violation());
                                            }
                                        }
                                    } catch (IllegalArrestException e) {
                                        System.out.println("Ошибка: " + e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (AliveObject aliveObject : world.getAliveObjectArrayList()) {
                if (aliveObject instanceof LittleMan) {
                    if (((LittleMan)aliveObject).getArrested()) {
                        ((LittleMan)aliveObject).thinkAboutViolation();
                        if (localTime - ((LittleMan)aliveObject).getViolationDetail().timeWhenItDid() >= 5) {
                            Policeman.letGo(((LittleMan)aliveObject));
                        }
                    } else {
                        try {
                            ((LittleMan)aliveObject).updateAge(getRandomIntInRange(1, 6));
                        } catch (IllegalAgeSetting e) {
                            System.out.println("Ошибка: " + e.getMessage());
                            //e.printStackTrace();
                        }
                    }
                }
            }

            if (getBooleanFromLittleMan.apply(world.getAliveObjectArrayList(), LittleMan::getAvailableToViolate)) {
                System.out.println("Нарушителей порядка не осталось");
                for (AliveObject aliveObject : world.getAliveObjectArrayList()) {
                    if (aliveObject instanceof Policeman) {
                        ((Policeman)aliveObject).setKnowHowToArrest(false);
                    }
                }
            }

            if (getBooleanFromPoliceman.apply(world.getAliveObjectArrayList(), Policeman::getKnowHowToArrest)) {
                for (LifelessObject lifelessObject : world.getLifelessObjectArrayList()) {
                    if (lifelessObject instanceof Word){
                        ((Word)lifelessObject).setKnown(false);
                    }
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
