package app;

import model.SpaceMarine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Менеджер коллекции космических десантников.
 *
 * @author ISyntax
 * @version 1.0
 */
@XmlRootElement(name = "spaceMarineCollection")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SpaceMarineCollectionManager {
    private ArrayDeque<SpaceMarine> marines;
    private ZonedDateTime creationDate;

    /**
     * Конструктор менеджера коллекции.
     */
    public SpaceMarineCollectionManager() {
        this.marines = new ArrayDeque<>();
        this.creationDate = ZonedDateTime.now();
    }

    /**
     * Возвращает коллекцию космических десантников.
     *
     * @return коллекция космических десантников
     */
    @XmlElement(name = "marine")
    public ArrayDeque<SpaceMarine> getMarines() {
        return marines;
    }

    /**
     * Устанавливает коллекцию космических десантников.
     *
     * @param marines коллекция космических десантников
     */
    public void setMarines(ArrayDeque<SpaceMarine> marines) {
        this.marines = marines;
    }

    /**
     * Возвращает дату создания коллекции.
     *
     * @return дата создания коллекции
     */
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания коллекции.
     *
     * @param creationDate дата создания коллекции
     */
    private void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает информацию о коллекции.
     *
     * @return информация о коллекции
     */
    public String getInfo() {
        StringBuilder info = new StringBuilder();

        info.append("Тип коллекции: ").append(marines.getClass().getSimpleName()).append("\n");
        info.append("Дата инициализации: ").append(creationDate.toString()).append("\n");
        info.append("Количество элементов: ").append(marines.size()).append("\n");

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

    /**
     * Добавляет космического десантника в коллекцию.
     *
     * @param marine космический десантник
     */
    public void addMarine(SpaceMarine marine) {
        if (marine == null) {
            throw new IllegalArgumentException("Marine cannot be null");
        }
        marines.add(marine);
    }

    /**
     * Удаляет космического десантника из коллекции.
     *
     * @param marine космический десантник
     */
    public void removeMarine(SpaceMarine marine) {
        if (marine == null) {
            throw new IllegalArgumentException("Marine cannot be null");
        }
        marines.remove(marine);
    }

    /**
     * Очищает коллекцию.
     */
    public void clearMarines() {
        marines.clear();
    }

    /**
     * Обновляет свободный ID.
     */
    public void updateFreeId() {
        int min = 0;
        for (SpaceMarine marine : marines) {
            if (marine.getId() >= min) {
                min = marine.getId();
            }
        }
        SpaceMarine.freeId = min;
    }

    /**
     * Проверяет уникальность ID в коллекции.
     */
    private void checkIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (SpaceMarine marine : marines) {
            if (ids.contains(marine.getId())) {
                throw new IllegalArgumentException("Коллекция содержит повторяющиеся ID!");
            }
            ids.add(marine.getId());
        }
    }

    /**
     * Заменяет космического десантника по ID.
     *
     * @param id       ID десантника
     * @param newMarine новый десантник
     */
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

    /**
     * Удаляет космического десантника по ID.
     *
     * @param id ID десантника
     */
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

    /**
     * Метод, вызываемый после десериализации.
     *
     * @param unmarshaller десериализатор
     * @param parent       родительский объект
     */
    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
        setMarines(this.marines);
        setCreationDate(this.creationDate);
        checkIds();
    }

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