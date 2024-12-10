package objects.actions;

import objects.Person;
import records.ViolationDetail;


public class Think {
    public static void apply(Person alive) {
        System.out.println(alive.getNameCapitalized() + " думать");
    }

    public static void apply(Person alive, String about) {
        System.out.println(alive.getNameCapitalized() + " думать " + about);
    }

    public static void apply(Person alive, ViolationDetail violationDetail) {
        System.out.println(alive.getNameCapitalized() +
                " думать, что пятнадцать суток ареста - это слишком небольшой срок за "
                + violationDetail.violation());
    }
}
