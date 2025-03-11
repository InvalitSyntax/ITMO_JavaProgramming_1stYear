package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "chapter")
@XmlAccessorType(XmlAccessType.PROPERTY) // Изменено на PROPERTY
public class Chapter implements Comparable<Chapter> {
    private String name; // Поле не может быть null, строка не может быть пустой
    private String world; // Поле не может быть null

    // Конструктор
    public Chapter(String name, String world) {
        setName(name); // Используем сеттер для проверки ограничений
        setWorld(world); // Используем сеттер для проверки ограничений
    }

    public Chapter() {
    }

    // Геттеры
    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public String getName() {
        return name;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public String getWorld() {
        return world;
    }

    // Сеттеры
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name не может быть null или пустым");
        }
        this.name = name;
    }

    public void setWorld(String world) {
        if (world == null) {
            throw new IllegalArgumentException("World не может быть null");
        }
        this.world = world;
    }

    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
        // Прогоняем все поля через сеттеры
        setName(this.name);
        setWorld(this.world);
    }

    @Override
    public String toString() {
        return "Chapter {\n" +
                "  name: " + name + "\n" +
                "  world: " + world + "\n" +
                "}";
    }

    @Override
    public int compareTo(Chapter other) {
        // Сравниваем по полю name
        int nameComparison = this.name.compareTo(other.name);
        if (nameComparison != 0) {
            return nameComparison;
        }
        // Если name одинаковые, сравниваем по полю world
        return this.world.compareTo(other.world);
    }
}