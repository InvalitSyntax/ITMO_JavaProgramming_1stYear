package org.example.collectionClasses.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс, представляющий главу.
 *
 * @author ISyntax
 * @version 1.0
 */
@XmlRootElement(name = "chapter")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Chapter implements Comparable<Chapter>, Serializable {
    public static final long serialVersionUID = 5L;
    private String name;
    private String world;

    /**
     * Конструктор главы.
     *
     * @param name  имя главы
     * @param world мир главы
     */
    public Chapter(String name, String world) {
        setName(name);
        setWorld(world);
    }

    /**
     * Конструктор по умолчанию.
     */
    public Chapter() {
    }

    /**
     * Возвращает имя главы.
     *
     * @return имя главы
     */
    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя главы.
     *
     * @param name имя главы
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name не может быть null или пустым");
        }
        this.name = name;
    }

    /**
     * Возвращает мир главы.
     *
     * @return мир главы
     */
    @XmlElement(required = true)
    public String getWorld() {
        return world;
    }

    /**
     * Устанавливает мир главы.
     *
     * @param world мир главы
     */
    public void setWorld(String world) {
        if (world == null) {
            throw new IllegalArgumentException("World не может быть null");
        }
        this.world = world;
    }

    /**
     * Метод, вызываемый после десериализации.
     *
     * @param unmarshaller десериализатор
     * @param parent       родительский объект
     */
    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
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
        if (other == null) {
            return 1;
        }
        int nameComparison = this.name.compareTo(other.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }
        return this.world.compareTo(other.getName());
    }
}