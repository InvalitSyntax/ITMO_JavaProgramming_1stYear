package objects.alive;

public class AliveObjectWithType extends AliveObject{
    private String type;

    public AliveObjectWithType(String name, int age, String type) {
        super(name, age);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
