package collectionManager;

import collectionObjects.SpaceMarine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;

@XmlRootElement(name = "spaceMarineCollection")
@XmlAccessorType(XmlAccessType.FIELD) // Указываем, что JAXB должен использовать поля
public class SpaceMarineCollection {
    @XmlElement(name = "marine")
    private ArrayDeque<SpaceMarine> marines; // Коллекция для хранения SpaceMarine
    private ZonedDateTime creationDate;

    // Конструктор
    public SpaceMarineCollection() {
        this.marines = new ArrayDeque<>();
        this.creationDate = ZonedDateTime.now();
    }

    // Геттер для коллекции
    public ArrayDeque<SpaceMarine> getMarines() {
        return marines;
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getInfo () {
        StringBuilder info = new StringBuilder();

        // Тип коллекции
        info.append("Тип коллекции: ").append(marines.getClass().getSimpleName()).append("\n");

        // Дата инициализации коллекции
        info.append("Дата инициализации: ").append(creationDate.toString()).append("\n");

        // Количество элементов в коллекции
        info.append("Количество элементов: ").append(marines.size()).append("\n");

        // Дополнительная информация, если коллекция пуста
        if (marines.isEmpty()) {
            info.append("Коллекция пуста.");
        } else {
            // Пример вывода информации о первом и последнем элементах коллекции
            info.append("Первый элемент: ").append(marines.getFirst().toString()).append("\n");
            info.append("Последний элемент: ").append(marines.getLast().toString()).append("\n");
        }

        return info.toString();
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