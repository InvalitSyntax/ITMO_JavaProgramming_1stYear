package objects.actions;

import enums.Violation;
import objects.LittleMan;
import objects.Person;
import records.ViolationDetail;

public class Do {
    public static void apply(Person person) {
        System.out.println(person.getNameCapitalized() + " делать");
    }

    public static void apply(Person person, String what) {
        System.out.println(person.getNameCapitalized() + " делать " + what);
    }

    public static void apply(LittleMan littleMan, Violation violation, int timeWhenItWas) {
        if (littleMan.getAvailableToViolate() & !littleMan.getArrested()){
            System.out.println(littleMan.getNameCapitalized() + " делать " + violation);
            littleMan.setViolationDetail(new ViolationDetail(violation, timeWhenItWas));
        }
        else {
            System.out.println(littleMan.getNameCapitalized() + " больше не делает " + violation);
        }
    }
}
