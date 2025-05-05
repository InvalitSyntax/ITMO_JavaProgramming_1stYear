package org.example.collectionClasses.app;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;

/**
 * Менеджер для работы с XML-файлами, обеспечивающий сохранение и загрузку коллекции.
 * Использует JAXB для сериализации и десериализации объектов.
 *
 * @author ISyntax
 * @version 1.0
 */
public class XMLIOManager {
    private final String filePath;

    /**
     * Конструктор XMLIOManager.
     *
     * @param filePath путь к файлу, с которым будет работать менеджер
     */
    public XMLIOManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Сохраняет коллекцию космических десантников в XML-файл.
     *
     * @param collection коллекция для сохранения
     * @throws RuntimeException если произошла ошибка при сериализации или записи в файл
     */
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
            System.out.println("Коллекция успешно сохранена");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Загружает коллекцию космических десантников из XML-файла.
     *
     * @return загруженная коллекция
     * @throws RuntimeException если произошла ошибка при десериализации или чтении файла
     */
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

            SpaceMarineCollectionManager out = (SpaceMarineCollectionManager) unmarshaller.unmarshal(new StringReader(xmlContent.toString()));
            System.out.println("Коллекция успешно загружена");
            out.updateFreeId();
            return out;

        } catch (FileNotFoundException e) {
            System.out.println("Файл коллекции не найден, загружена пустая коллекция");
            return new SpaceMarineCollectionManager();
        } catch (JAXBException e) {
            System.out.println("Ошибка при загрузке коллекции из XML:");
            Throwable cause = e.getCause();
            while (cause != null) {
                if (cause instanceof IllegalArgumentException) {
                    System.out.println(cause.getMessage());
                    break;
                }
                cause = cause.getCause();
            }
            System.out.println("\nИсправьте ошибку в файле и запустите программу заново, а пока что загружена пустая коллекция");
            System.out.println("Для выхода из программы можете использовать команду exit\n");
            return new SpaceMarineCollectionManager();
        }
    }
}