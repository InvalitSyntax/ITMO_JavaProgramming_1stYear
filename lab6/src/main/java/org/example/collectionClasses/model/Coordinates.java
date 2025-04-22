package org.example.collectionClasses.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс, представляющий координаты.
 *
 * @author ISyntax
 * @version 1.0
 */
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Coordinates {
    private double x;
    private Float y;

    /**
     * Конструктор координат.
     *
     * @param x координата x
     * @param y координата y
     */
    public Coordinates(double x, Float y) {
        this.x = x;
        setY(y);
    }

    /**
     * Конструктор по умолчанию.
     */
    public Coordinates() {
    }

    /**
     * Возвращает координату x.
     *
     * @return координата x
     */
    @XmlElement(required = true)
    public double getX() {
        return x;
    }

    /**
     * Устанавливает координату x.
     *
     * @param x координата x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Возвращает координату y.
     *
     * @return координата y
     */
    @XmlElement(required = true)
    public Float getY() {
        return y;
    }

    /**
     * Устанавливает координату y.
     *
     * @param y координата y
     */
    public void setY(Float y) {
        if (y == null || y <= -501) {
            throw new IllegalArgumentException("Поле Y не может быть null и должно быть больше -501");
        }
        this.y = y;
    }

    /**
     * Метод, вызываемый после десериализации.
     *
     * @param unmarshaller десериализатор
     * @param parent       родительский объект
     */
    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
        setX(this.x);
        setY(this.y);
    }

    @Override
    public String toString() {
        return "Coordinates {\n" +
                "  x: " + x + "\n" +
                "  y: " + y + "\n" +
                "}";
    }
}