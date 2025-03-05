package model;

import storage.ZonedDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;

@XmlRootElement(name = "spaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarine {
    @XmlElement(required = true)
    private int id; // Значение поля должно быть больше 0, уникальное, генерируется автоматически
    @XmlElement(required = true)
    private String name; // Поле не может быть null, строка не может быть пустой
    @XmlElement(required = true)
    private Coordinates coordinates; // Поле не может быть null
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private ZonedDateTime creationDate; // Поле не может быть null, генерируется автоматически
    @XmlElement(required = true)
    private Float health; // Поле не может быть null, значение должно быть больше 0
    @XmlElement(required = true)
    private boolean loyal;
    @XmlElement(required = true)
    private Weapon weaponType; // Поле может быть null
    @XmlElement(required = true)
    private MeleeWeapon meleeWeapon; // Поле может быть null
    @XmlElement(required = true)
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
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Float getHealth() {
        return health;
    }

    public boolean isLoyal() {
        return loyal;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    // Сеттеры
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    public void setHealth(Float health) {
        if (health == null || health <= 0) {
            throw new IllegalArgumentException("Health cannot be null and must be greater than 0");
        }
        this.health = health;
    }

    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
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
}
