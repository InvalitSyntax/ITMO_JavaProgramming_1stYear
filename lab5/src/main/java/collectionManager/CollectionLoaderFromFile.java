package collectionManager;

import collectionObjects.SpaceMarine;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class CollectionLoaderFromFile {
    private static final XStream xstream = new XStream();

    public static ArrayDeque<SpaceMarine> load(String fileName) throws IOException {
        xstream.alias("SpaceMarine", SpaceMarine.class); // Настраиваем псевдоним для класса
        xstream.alias("marines", ArrayDeque.class); // Настраиваем псевдоним для коллекции
        xstream.registerConverter(new ArrayDequeConverter());

        // Чтение XML из файла с помощью Scanner
        try (Scanner scanner = new Scanner(new File(fileName))) {
            StringBuilder xmlContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                xmlContent.append(scanner.nextLine());
            }

            // Преобразование XML в коллекцию
            return (ArrayDeque<SpaceMarine>) xstream.fromXML(xmlContent.toString());
        }
    }
}
