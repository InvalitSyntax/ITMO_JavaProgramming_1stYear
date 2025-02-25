package collectionManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Scanner;

public class XMLUtil {

    public static void saveCollectionToFile(SpaceMarineCollection collection, String filePath) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(SpaceMarineCollection.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(collection, stringWriter);
        String xmlContent = stringWriter.toString();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(xmlContent);
        }
    }

    public static SpaceMarineCollection loadCollectionFromFile(String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(SpaceMarineCollection.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StringBuilder xmlContent = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                xmlContent.append(scanner.nextLine()).append("\n");
            }
        }

        return (SpaceMarineCollection) unmarshaller.unmarshal(new java.io.StringReader(xmlContent.toString()));
    }
}