package org.example.collectionClasses.model;

import org.example.collectionClasses.app.ZonedDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Класс, представляющий космического десантника.
 *
 * @author ISyntax
 * @version 1.0
 */
@XmlRootElement(name = "spaceMarine")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable{
    public static final long serialVersionUID = 3L;
    public static int freeId = 0;
    private int id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Float health;
    private boolean loyal;
    private Weapon weaponType;
    private MeleeWeapon meleeWeapon;
    private Chapter chapter;
    private String userLogin;

    {
        freeId++;
    }

    /**
     * Конструктор космического десантника.
     *
     * @param id          ID десантника
     * @param name        имя десантника
     * @param coordinates координаты десантника
     * @param health      здоровье десантника
     * @param loyal       лояльность десантника
     * @param weaponType  тип оружия
     * @param meleeWeapon тип оружия ближнего боя
     * @param chapter     глава
     */
    public SpaceMarine(int id, String name, Coordinates coordinates, Float health, boolean loyal, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.health = health;
        this.loyal = loyal;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    /**
     * Конструктор по умолчанию.
     */
    public SpaceMarine() {
    }

    /**
     * Возвращает ID десантника.
     *
     * @return ID десантника
     */
    @XmlElement(required = true)
    public int getId() {
        return id;
    }

    /**
     * Устанавливает ID десантника.
     *
     * @param id ID десантника
     */
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть больше 0");
        }
        this.id = id;
    }

    /**
     * Возвращает имя десантника.
     *
     * @return имя десантника
     */
    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return this.userLogin;
    }

    /**
     * Устанавливает имя десантника.
     *
     * @param name имя десантника
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name не может быть null или пустым");
        }
        this.name = name;
    }

    /**
     * Возвращает координаты десантника.
     *
     * @return координаты десантника
     */
    @XmlElement(required = true)
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает координаты десантника.
     *
     * @param coordinates координаты десантника
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates не могут быть null");
        }
        this.coordinates = coordinates;
    }

    /**
     * Возвращает дату создания десантника.
     *
     * @return дата создания десантника
     */
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания десантника.
     *
     * @param creationDate дата создания десантника
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает здоровье десантника.
     *
     * @return здоровье десантника
     */
    @XmlElement(required = true)
    public Float getHealth() {
        return health;
    }

    /**
     * Устанавливает здоровье десантника.
     *
     * @param health здоровье десантника
     */
    public void setHealth(Float health) {
        if (health == null || health <= 0) {
            throw new IllegalArgumentException("Health не может быть null и должно быть больше 0");
        }
        this.health = health;
    }

    /**
     * Возвращает лояльность десантника.
     *
     * @return лояльность десантника
     */
    @XmlElement(required = true)
    public boolean getLoyal() {
        return loyal;
    }

    /**
     * Устанавливает лояльность десантника.
     *
     * @param loyal лояльность десантника
     */
    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    /**
     * Возвращает тип оружия.
     *
     * @return тип оружия
     */
    @XmlElement(required = true)
    public Weapon getWeaponType() {
        return weaponType;
    }

    /**
     * Устанавливает тип оружия.
     *
     * @param weaponType тип оружия
     */
    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    /**
     * Возвращает тип оружия ближнего боя.
     *
     * @return тип оружия ближнего боя
     */
    @XmlElement(required = true)
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    /**
     * Устанавливает тип оружия ближнего боя.
     *
     * @param meleeWeapon тип оружия ближнего боя
     */
    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    /**
     * Возвращает главу.
     *
     * @return глава
     */
    @XmlElement(required = true)
    public Chapter getChapter() {
        return chapter;
    }

    /**
     * Устанавливает главу.
     *
     * @param chapter глава
     */
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    /**
     * Метод, вызываемый после десериализации.
     *
     * @param unmarshaller десериализатор
     * @param parent       родительский объект
     */
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
        return this.name.compareTo(other.getName());
    }
}