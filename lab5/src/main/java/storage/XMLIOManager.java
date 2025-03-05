package storage;

import collection.SpaceMarineCollectionManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;

// TODO: фикс неправильную загрузку коллекции - ебаные FIELD убрать нахуй просто и заменить на хуйпизда сеттерыгеттеры

public class XMLIOManager {
    private String filePath;

    public XMLIOManager(String filePath) {
        this.filePath = filePath;
    }

    public void saveCollectionToFile(SpaceMarineCollectionManager collection) {
        String xmlContent = null;
        try {
            JAXBContext context = JAXBContext.newInstance(SpaceMarineCollectionManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(collection, stringWriter);
            xmlContent = stringWriter.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filePath))) {
            bufferedWriter.write(xmlContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SpaceMarineCollectionManager loadCollectionFromFile() {
        Unmarshaller unmarshaller = null;
        try {
            JAXBContext context = JAXBContext.newInstance(SpaceMarineCollectionManager.class);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка создания JAXBContext", e);
        }

        StringBuilder xmlContent = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(this.filePath))) {
            while (scanner.hasNextLine()) {
                xmlContent.append(scanner.nextLine()).append("\n");
            }

            // Логируем содержимое XML для отладки
            System.out.println("Содержимое XML:\n" + xmlContent.toString());

            SpaceMarineCollectionManager out = (SpaceMarineCollectionManager) unmarshaller.unmarshal(new java.io.StringReader(xmlContent.toString()));
            System.out.println("Коллекция успешно загружена");
            return out;

        } catch (FileNotFoundException e) {
            System.out.println("Файл коллекции не найден, загружена пустая коллекция");
            return new SpaceMarineCollectionManager();
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при загрузке коллекции из XML", e);
        }
    }
}