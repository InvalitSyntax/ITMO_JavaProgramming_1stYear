package org.example.collectionClasses.app;

import org.example.collectionClasses.model.SpaceMarine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Потокобезопасный менеджер коллекции космических десантников.
 * Обеспечивает безопасное многопоточное управление коллекцией объектов SpaceMarine.
 * 
 * @author ISyntax
 * @version 2.0
 */
@XmlRootElement(name = "spaceMarineCollection")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SpaceMarineCollectionManager {
    private final Deque<SpaceMarine> marines = new ConcurrentLinkedDeque<>();
    private final AtomicInteger freeId = new AtomicInteger(0);
    private ZonedDateTime creationDate;

    /**
     * Конструктор менеджера коллекции.
     * Инициализирует коллекцию и устанавливает текущую дату создания.
     */
    public SpaceMarineCollectionManager() {
        this.creationDate = ZonedDateTime.now();
    }

    /**
     * Возвращает копию коллекции космических десантников.
     * 
     * @return потокобезопасная копия коллекции космических десантников
     */
    @XmlElement(name = "marine")
    public Deque<SpaceMarine> getMarines() {
        return new ConcurrentLinkedDeque<>(marines);
    }

    /**
     * Устанавливает новую коллекцию космических десантников.
     * 
     * @param marines новая коллекция космических десантников
     * @throws IllegalArgumentException если переданная коллекция содержит повторяющиеся ID
     */
    public synchronized void setMarines(Deque<SpaceMarine> marines) {
        this.marines.clear();
        this.marines.addAll(marines);
        updateFreeId();
    }

    /**
     * Возвращает дату создания коллекции.
     * 
     * @return дата создания коллекции
     */
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    public synchronized ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания коллекции.
     * 
     * @param creationDate дата создания коллекции
     */
    private synchronized void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Добавляет космического десантника в коллекцию.
     * 
     * @param marine добавляемый космический десантник
     * @throws IllegalArgumentException если marine равен null
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
     * @param marine удаляемый космический десантник
     * @throws IllegalArgumentException если marine равен null
     */
    public void removeMarine(SpaceMarine marine) {
        if (marine == null) {
            throw new IllegalArgumentException("Marine cannot be null");
        }
        marines.remove(marine);
    }

    /**
     * Очищает коллекцию и сбрасывает счётчик ID.
     */
    public synchronized void clearMarines() {
        marines.clear();
        freeId.set(0);
    }

    /**
     * Обновляет значение свободного ID на основе максимального ID в коллекции.
     */
    public synchronized void updateFreeId() {
        freeId.set(marines.stream()
            .mapToInt(SpaceMarine::getId)
            .max()
            .orElse(0));
    }

    /**
     * Возвращает следующий свободный ID и увеличивает счётчик.
     * 
     * @return следующий доступный ID
     */
    public int getFreeId() {
        return freeId.incrementAndGet();
    }

    /**
     * Заменяет космического десантника с указанным ID на нового.
     * 
     * @param id ID заменяемого десантника
     * @param newMarine новый десантник
     */
    public synchronized void replaceMarineById(int id, SpaceMarine newMarine) {
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

    public synchronized boolean checkLogin(int id, String login) {
        Iterator<SpaceMarine> iterator = marines.iterator();
        while (iterator.hasNext()) {
            SpaceMarine current = iterator.next();
            if (id == current.getId()) {
                return (current.getUserLogin().equals(login));
            }
        }
        return false;
    }

    /**
     * Удаляет космического десантника по указанному ID.
     * 
     * @param id ID удаляемого десантника
     */
    public synchronized void removeMarineById(int id) {
        marines.removeIf(marine -> marine.getId() == id);
    }

    /**
     * Проверяет уникальность ID в коллекции.
     * 
     * @throws IllegalArgumentException если обнаружены дублирующиеся ID
     */
    private synchronized void checkIds() {
        Set<Integer> ids = ConcurrentHashMap.newKeySet();
        for (SpaceMarine marine : marines) {
            if (!ids.add(marine.getId())) {
                throw new IllegalArgumentException("Коллекция содержит повторяющиеся ID!");
            }
        }
    }

    /**
     * Метод, вызываемый после десериализации XML.
     * Проверяет целостность данных коллекции.
     * 
     * @param unmarshaller десериализатор
     * @param parent родительский объект
     * @throws IllegalArgumentException если обнаружены проблемы с данными
     */
    void afterUnmarshal(javax.xml.bind.Unmarshaller unmarshaller, Object parent) {
        setMarines(this.marines);
        setCreationDate(this.creationDate);
        checkIds();
    }

    /**
     * Возвращает информацию о коллекции.
     * 
     * @return строковое представление информации о коллекции
     */
    public synchronized String getInfo() {
        StringBuilder info = new StringBuilder();

        info.append("Тип коллекции: ").append(marines.getClass().getSimpleName()).append("\n");
        info.append("Дата инициализации: ").append(creationDate.toString()).append("\n");
        info.append("Количество элементов: ").append(marines.size()).append("\n");

        if (marines.isEmpty()) {
            info.append("Коллекция пуста.");
        } else if (marines.size() == 1) {
            info.append("Единственный элемент: ").append(marines.getFirst()).append("\n");
        } else {
            info.append("Первый элемент: ").append(marines.getFirst()).append("\n");
            info.append("Последний элемент: ").append(marines.getLast()).append("\n");
        }
        return info.toString();
    }

    /**
     * Возвращает строковое представление коллекции.
     * 
     * @return строковое представление всех элементов коллекции
     */
    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder("SpaceMarineCollection {\n");
        for (SpaceMarine marine : marines) {
            sb.append("  ").append(marine.toString().replace("\n", "\n  ")).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}