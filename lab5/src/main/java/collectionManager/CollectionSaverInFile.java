package collectionManager;
import java.io.*;

import collectionObjects.SpaceMarine;
import com.thoughtworks.xstream.XStream;

import java.util.Deque;

public class CollectionSaverInFile {
    private static final XStream xstream = new XStream();

    public static void save(String fileName, Deque<SpaceMarine> marines) throws IOException {
        xstream.alias("SpaceMarine", SpaceMarine.class);
        xstream.alias("SpaceMarineCollection", SpaceMarineCollection.class);
        xstream.registerConverter(new ArrayDequeConverter());

        String xml = xstream.toXML(marines);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(xml);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }
}