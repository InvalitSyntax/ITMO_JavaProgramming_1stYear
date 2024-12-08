package objects;

import enums.Action;

public class Alive extends Obj {
    public Alive(String name) {
        super(name);
    }

    public void doSomething(Action action) {
        System.out.println(getName() + " " + action);
    }
    public void doSomething(Action action, String whatToDo) {
        System.out.println(getName() + " " + action + " " + whatToDo);
    }
}