package objects;

import java.util.Objects;

public class Word {
    private final String word;
    private boolean isKnowledge = true;

    public Word(String word) {
        this.word = word;
    }

    public void printWord() {
        System.out.println(word);
    }

    public String getWord() {
        return "слово " + word;
    }

    public String getWordCapitalized() {
        return getWord().substring(0, 1).toUpperCase() + getWord().substring(1);
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
        return isKnowledge == word1.isKnowledge && Objects.equals(word, word1.word); // Сравниваем поля word и isKnowledge
    }

    // Переопределение hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(word, isKnowledge); // Хэш-код на основе полей word и isKnowledge
    }

    // Переопределение toString()
    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", isKnowledge=" + isKnowledge +
                '}';
    }
}

