package collectionManager;

import collectionObjects.SpaceMarine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayDeque;

@XmlRootElement(name = "spaceMarineCollection")
@XmlAccessorType(XmlAccessType.FIELD) // Указываем, что JAXB должен использовать поля
public class SpaceMarineCollection {
    @XmlElement(name = "marine")
    private ArrayDeque<SpaceMarine> marines; // Коллекция для хранения SpaceMarine

    // Конструктор
    public SpaceMarineCollection() {
        this.marines = new ArrayDeque<>();
    }

    // Геттер для коллекции
    public ArrayDeque<SpaceMarine> getMarines() {
        return marines;
    }

    // Сеттер для коллекции
    public void setMarines(ArrayDeque<SpaceMarine> marines) {
        this.marines = marines;
    }

    // Добавление элемента в коллекцию
    public void addMarine(SpaceMarine marine) {
        if (marine == null) {
            throw new IllegalArgumentException("Marine cannot be null");
        }
        marines.add(marine);
    }

    // Удаление элемента из коллекции
    public void removeMarine(SpaceMarine marine) {
        if (marine == null) {
            throw new IllegalArgumentException("Marine cannot be null");
        }
        marines.remove(marine);
    }

    // Красивый toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SpaceMarineCollection {\n");
        for (SpaceMarine marine : marines) {
            sb.append("  ").append(marine.toString().replace("\n", "\n  ")).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}