package objects.actions;

import exeptions.IllegalArrestException;
import objects.LittleMan;
import objects.Policeman;

public class ArrestActions {
    public static void arrest(Policeman policeman, LittleMan littleMan) throws IllegalArrestException {
        if (policeman.getKnowHowToArrest()){
            if(littleMan.getArrested()){
                throw new IllegalArrestException("Нельзя вновь арестовать уже посаженного коротышку!");
            }
            System.out.println(policeman.getNameCapitalized() +
                    " арестовывает " + littleMan.getName() + " за "
                    + littleMan.getViolationDetail().violation());

            littleMan.setArrested(true);
        } else {
            policeman.getKnowHowToArrestPrint();
        }
    }
    public static void letGo(LittleMan littleMan) {
        littleMan.setArrested(false);
        littleMan.clearViolationDetail();
        System.out.println(littleMan.getNameCapitalized() + " отпускают");
    }
}