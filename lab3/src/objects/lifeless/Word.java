package objects.lifeless;

import interfaces.CanBeKnown;

import static interfaces.Utils.capitalized;

public class Word extends LifelessObject implements CanBeKnown {
    private boolean known;
    public Word(String name, String type, boolean known) {
        super(name, type);
        this.known = known;
    }
    public Word(String name, boolean known) {
        super(name, "слово");
        this.known = known;
    }

    @Override
    public void setKnown(boolean known) {
        if (this.known != known){
            if (known){
                System.out.printf("%s %s снова знают\n", capitalized(getType()), getName());
            } else {
                System.out.printf("%s %s было забыто\n", capitalized(getType()), getName());
            }
            this.known = known;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Word word = (Word) o;
        return known == word.known && getName().equals(word.getName()) && getType().equals(word.getType());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (known ? 1 : 0);
        result = 31 * result + getName().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "name='" + getName() + '\'' +
                ", type='" + getType() + '\'' +
                ", known=" + known +
                '}';
    }
}
