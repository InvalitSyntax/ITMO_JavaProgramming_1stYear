package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.PROPERTY) // Изменено на PROPERTY
public class Coordinates {
    private double x;
    private Float y; // Значение поля должно быть больше -501, поле не может быть null

    // Конструктор
    public Coordinates(double x, Float y) {
        this.x = x;
        setY(y); // Используем сеттер для проверки ограничений
    }

    public Coordinates() {
    }

    // Геттеры
    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public double getX() {
        return x;
    }

    @XmlElement(required = true) // Добавлено для указания, что поле обязательно
    public Float getY() {
        return y;
    }

    // Сеттеры
    public void setX(double x) {
        this.x = x;
    }

    public void setY(Float y) {
        if (y == null || y <= -501) {
            throw new IllegalArgumentException("Поле Y не может быть null и должно быть больше -501");
        }
        this.y = y;
    }

    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
        // Прогоняем все поля через сеттеры
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