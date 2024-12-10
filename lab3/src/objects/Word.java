package objects;

import interfaces.HasName;

import java.util.Objects;

public class Word implements HasName {
    private final String name;
    private boolean isKnowledge = true;

    public Word(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getWordWithType() {
        return "слово " + getName();
    }

    public String getWordCapitalized() {
        return getWordWithType().substring(0, 1).toUpperCase() + getWordWithType().substring(1);
    }

    public void setKnowledge(boolean isKnowledge) {
        this.isKnowledge = isKnowledge;
        if (!isKnowledge) {
            System.out.println(getWordCapitalized() + " было забыто");
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

