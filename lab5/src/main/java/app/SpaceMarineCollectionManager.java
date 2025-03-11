package app;

import model.SpaceMarine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Iterator;

@XmlRootElement(name = "spaceMarineCollection")
@XmlAccessorType(XmlAccessType.PROPERTY) // Используем геттеры и сеттеры
public class SpaceMarineCollectionManager {
    private int id=0;
    private ArrayDeque<SpaceMarine> marines; // Коллекция для хранения SpaceMarine
    private ZonedDateTime creationDate; // Дата создания коллекции

    // Конструктор
    public SpaceMarineCollectionManager() {
        this.marines = new ArrayDeque<>();
        this.creationDate = ZonedDateTime.now();
    }

    // Геттер для коллекции
    @XmlElement(name = "marine") // Указываем имя элемента в XML
    public ArrayDeque<SpaceMarine> getMarines() {
        return marines;
    }

    // Сеттер для коллекции
    public void setMarines(ArrayDeque<SpaceMarine> marines) {
        this.marines = marines;
    }

    // Геттер для даты создания
    @XmlElement(required = true) // Поле обязательно
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class) // Адаптер для ZonedDateTime
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    // Сеттер для даты создания
    private void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    // Метод для получения информации о коллекции
    public String getInfo() {
        StringBuilder info = new StringBuilder();

        // Тип коллекции
        info.append("Тип коллекции: ").append(marines.getClass().getSimpleName()).append("\n");

        // Дата инициализации коллекции
        info.append("Дата инициализации: ").append(creationDate.toString()).append("\n");

        // Количество элементов в коллекции
        info.append("Количество элементов: ").append(marines.size()).append("\n");

        // Дополнительная информация, если коллекция пуста
        switch (marines.size()) {
            case 0: info.append("Коллекция пуста."); break;
            case 1: info.append("Единственный элемент: ").append(marines.getFirst()).append("\n"); break;
            default: {
                info.append("Первый элемент: ").append(marines.getFirst()).append("\n");
                info.append("Последний элемент: ").append(marines.getLast()).append("\n");
            }
        }
        return info.toString();
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

    // Очистка коллекции
    public void clearMarines() {
        marines.clear();
    }

    public void updateFreeId() {
        int min = 0;
        for (SpaceMarine marine : marines) {
            if (marine.getId() >= min) {
                min = marine.getId();
            }
        }
        SpaceMarine.freeId = min;
    }

    public void replaceMarineById(int id, SpaceMarine newMarine) {
        Iterator<SpaceMarine> iterator = marines.iterator();
        while (iterator.hasNext()) {
            SpaceMarine current = iterator.next();
            if (id == current.getId()) {
                iterator.remove();
                marines.add(newMarine);
                break;
            }
        }
    }

    public void removeMarineById(int id) {
        Iterator<SpaceMarine> iterator = marines.iterator();
        while (iterator.hasNext()) {
            SpaceMarine current = iterator.next();
            if (id == current.getId()) {
                iterator.remove();
                break;
            }
        }
    }

    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
        // Прогоняем все поля через сеттеры
        setMarines(this.marines);
        setCreationDate(this.creationDate);
    }

    // Переопределение метода toString для красивого вывода
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