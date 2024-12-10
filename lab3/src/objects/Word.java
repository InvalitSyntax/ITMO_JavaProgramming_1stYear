package objects;

import java.util.Objects;

public class Word extends ObjWithName {
    private final String name;
    private boolean isKnowledge = true;

    public Word(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameWithType() {
        return "слово " + getName();
    }

    @Override
    public String getNameCapitalized() {
        return getNameWithType().substring(0, 1).toUpperCase() + getNameWithType().substring(1);
    }

    public void setKnowledge(boolean isKnowledge) {
        this.isKnowledge = isKnowledge;
        if (!isKnowledge) {
            System.out.println(getNameCapitalized() + " было забыто");
        }
    }

    // Переопределение equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Проверка на ссылочное равенство
        if (o == null || getClass() != o.getClass()) return false; // Проверка класса
        Word word1 = (Word) o;
        return isKnowledge == word1.isKnowledge && Objects.equals(name, word1.name); // Сравниваем поля word и isKnowledge
    }

    // Переопределение hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(name, isKnowledge); // Хэш-код на основе полей word и isKnowledge
    }

    // Переопределение toString()
    @Override
    public String toString() {
        return "Word{" +
                "word='" + name + '\'' +
                ", isKnowledge=" + isKnowledge +
                '}';
    }
}

