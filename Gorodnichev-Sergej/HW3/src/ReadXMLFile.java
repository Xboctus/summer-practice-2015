import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

/**
 * Created by sergej on 03.07.15.
 */
public class ReadXMLFile {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
            Statement statement = conn.createStatement();

            File dir = new File("xmlpath");
            if (!dir.isDirectory())
                throw new Exception("Can't find directory");

            File schema = new File("xmlpath/schema.xsd");

            File[] files = dir.listFiles((dir1, name) -> {
                return name.toLowerCase().endsWith("xml");
            });

            System.out.println("Choose the parser:");
            System.out.println("1 - SAX");
            System.out.println("2 - DOM");

            Scanner reader = new Scanner(System.in);
            String line = reader.nextLine();

            switch(line) {
                case "1":
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    if (schema.canRead()) {
                        factory.setValidating(false);
                        factory.setNamespaceAware(true);
                        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                        factory.setSchema(schemaFactory.newSchema(schema));
                    }
                    SAXParser saxParser = factory.newSAXParser();
                    SaxHandler handler = new SaxHandler();

                    for (File file : files) {
                        saxParser.parse(file, handler);
                        for (Book book : handler.books) {
                            book.insertToDB(statement);
                        }
                    }
                    break;
                case "2":
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    if (schema.canRead()) {
                        dbFactory.setValidating(false);
                        dbFactory.setNamespaceAware(true);
                        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                        dbFactory.setSchema(schemaFactory.newSchema(schema));
                    }
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                    for (File file : files) {
                        Document document = dBuilder.parse(file);
                        document.getDocumentElement().normalize();
                        NodeList nodes = document.getElementsByTagName("book");
                        for (int i = 0; i < nodes.getLength(); i++) {
                            Node node = nodes.item(i);
                            Book book = new Book();
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) node;
                                book.isbn = element.getElementsByTagName("isbn").item(0).getTextContent();
                                book.author = element.getElementsByTagName("author").item(0).getTextContent();
                                book.title = element.getElementsByTagName("title").item(0).getTextContent();

                                book.insertToDB(statement);
                            }
                        }
                    }
                    break;
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
