package model;

import app.ZonedDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;

@XmlRootElement(name = "spaceMarine")
@XmlAccessorType(XmlAccessType.PROPERTY) // Изменено на PROPERTY
public class SpaceMarine implements Comparable<SpaceMarine>{
    private int id; // Значение поля должно быть больше 0, уникальное, генерируется автоматически
    private String name; // Поле не может быть null, строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; // Поле не может быть null, генерируется автоматически
    private Float health; // Поле не может быть null, значение должно быть больше 0
    private boolean loyal;
    private Weapon weaponType; // Поле может быть null
    private MeleeWeapon meleeWeapon; // Поле может быть null
    private Chapter chapter; // Поле может быть null

    // Конструктор
    public SpaceMarine(int id, String name, Coordinates coordinates, Float health, boolean loyal, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now(); // Автоматическая генерация времени создания
        this.health = health;
        this.loyal = loyal;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine() {
    }

    // Геттеры
    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public int getId() {
        return id;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public String getName() {
        return name;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public Float getHealth() {
        return health;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public boolean getLoyal() {
        return loyal;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public Weapon getWeaponType() {
        return weaponType;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public Chapter getChapter() {
        return chapter;
    }

    // Сеттеры
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть больше 0");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name не может быть null или пустым");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates не могут быть null");
        }
        this.coordinates = coordinates;
    }

    public void setHealth(Float health) {
        if (health == null || health <= 0) {
            throw new IllegalArgumentException("Health не может быть null и должно быть больше 0");
        }
        this.health = health;
    }

    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
        setId(this.id);
        setName(this.name);
        setCoordinates(this.coordinates);
        setCreationDate(this.creationDate);
        setHealth(this.health);
        setLoyal(this.loyal);
        setWeaponType(this.weaponType);
        setMeleeWeapon(this.meleeWeapon);
        setChapter(this.chapter);
    }

    @Override
    public String toString() {
        return "SpaceMarine {\n" +
                "  id: " + id + "\n" +
                "  name: " + name + "\n" +
                "  coordinates: " + coordinates.toString().replace("\n", "\n  ") + "\n" +
                "  creationDate: " + creationDate + "\n" +
                "  health: " + health + "\n" +
                "  loyal: " + loyal + "\n" +
                "  weaponType: " + (weaponType != null ? weaponType : "null") + "\n" +
                "  meleeWeapon: " + (meleeWeapon != null ? meleeWeapon : "null") + "\n" +
                "  chapter: " + (chapter != null ? chapter.toString().replace("\n", "\n  ") : "null") + "\n" +
                "}";
    }

    @Override
    public int compareTo(SpaceMarine other) {
        return Integer.compare(this.id, other.id);
    }
}