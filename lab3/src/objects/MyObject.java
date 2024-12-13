package objects;

public abstract class MyObject {
    private final String name;
    public MyObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
