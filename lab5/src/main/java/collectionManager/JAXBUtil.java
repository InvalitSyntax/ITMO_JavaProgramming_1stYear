package collectionManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/*
TODO переделать на
 java.util.Scanner
 java.io.BufferedWriter
 */

public class JAXBUtil {

    public static void saveCollectionToFile(SpaceMarineCollection collection, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SpaceMarineCollection.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(collection, new File(filePath));
    }

    public static SpaceMarineCollection loadCollectionFromFile(String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(SpaceMarineCollection.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (SpaceMarineCollection) unmarshaller.unmarshal(new File(filePath));
    }
}