package collectionObjects;

public class Coordinates {
    private double x;
    private Float y; // Значение поля должно быть больше -501, поле не может быть null

    // Конструктор
    public Coordinates(double x, Float y) {
        this.x = x;
        setY(y); // Используем сеттер для проверки ограничений
    }

    // Геттеры
    public double getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    // Сеттеры
    public void setX(double x) {
        this.x = x;
    }

    public void setY(Float y) {
        if (y == null || y <= -501) {
            throw new IllegalArgumentException("Y cannot be null and must be greater than -501");
        }
        this.y = y;
    }
    @Override
    public String toString() {
        return "Coordinates {\n" +
                "  x: " + x + "\n" +
                "  y: " + y + "\n" +
                "}";
    }
}