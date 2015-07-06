import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by sergej on 03.07.15.
 */
public class ReadXMLFile {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "123456");
            File xmlFile = new File("test.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList list = doc.getElementsByTagName("xsd:element");

            for (int i = 0; i < list.getLength(); i++) {
                Element first = (Element)list.item(i);
                if (first.hasAttributes()) {

                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
