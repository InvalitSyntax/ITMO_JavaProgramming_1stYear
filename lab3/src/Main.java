import enums.Action;
import objects.Alive;
import objects.People;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Action.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Alive someBody = new Alive("Некоторые");
        People LittleMan = new People("коротышка Петя", 18);
        People LittleMan1 = new People("коротышка Вова", 1);

        someBody.doSomething(Action.THINK);
        LittleMan.getTimePrint();
        LittleMan.getIntelligencePrint();

        LittleMan1.getTimePrint();
        LittleMan1.getIntelligencePrint();
        LittleMan1.updateAge(15);
        System.out.println(LittleMan1.getAge());
        LittleMan1.getTimePrint();
        LittleMan1.getIntelligencePrint();
        LittleMan1.updateAge(-5);
        System.out.println(LittleMan1.getAge());

        LittleMan.doSomething(Action.HIT, "никого");
    }
}