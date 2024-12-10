import exeptions.IllegalArrestException;
import objects.LittleMan;
import objects.Policeman;

public class Main {
    public static void main(String[] args) {
        LittleMan john = new LittleMan("Джон", 12);
        LittleMan mike = new LittleMan("Майк", 18);
        Policeman officer = new Policeman();

        officer.monitorOrder();
        try {
            officer.arrest(john, "Кража яблока", 15);
            officer.arrest(mike, "Драка", 10);
        } catch (IllegalArrestException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
