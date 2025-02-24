package collectionObjects;

public class Chapter {
    private String name; // Поле не может быть null, строка не может быть пустой
    private String world; // Поле не может быть null

    // Конструктор
    public Chapter(String name, String world) {
        setName(name); // Используем сеттер для проверки ограничений
        setWorld(world); // Используем сеттер для проверки ограничений
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    // Сеттеры
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setWorld(String world) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null");
        }
        this.world = world;
    }
    @Override
    public String toString() {
        return "Chapter {\n" +
                "  name: " + name + "\n" +
                "  world: " + world + "\n" +
                "}";
    }
}