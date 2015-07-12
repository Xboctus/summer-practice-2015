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
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by korcky on 12.07.15.
 */
public class XMLReader {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            Statement statement = con.createStatement();

            String dirPath = "/home/korcky/xml's";
            File dir = new File(dirPath);
            if (!dir.isDirectory())
                throw new Exception("Path:" + dirPath + " doesn't exist");

            File schema = new File(dirPath + "/schema.xsd");
            File[] files = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".xml");
                }
            });

            System.out.print("Choose the parser [SAX/DOM]: ");
            Scanner reader = new Scanner(System.in);
            String line = reader.nextLine();

            switch (line) {
                case "DOM":
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    if (schema.canRead()) {
                        dbFactory.setValidating(false);
                        dbFactory.setNamespaceAware(true);
                        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                        dbFactory.setSchema(schemaFactory.newSchema(schema));
                    }
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                    for (File file:files) {
                        Document doc = dBuilder.parse(file);
                        doc.getDocumentElement().normalize();
                        NodeList nodes = doc.getElementsByTagName("book");
                        for (int i = 0; i < nodes.getLength(); i++) {
                            Node node = nodes.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Book book = new Book();
                                Element element = (Element) node;
                                book.setIsbn(element.getElementsByTagName("isbn").item(0).getTextContent());
                                book.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
                                book.setTitle(element.getElementsByTagName("title").item(0).getTextContent());
                                book.setGenre(element.getElementsByTagName("genre").item(0).getTextContent());
                                book.setPublishDate(element.getElementsByTagName("publish_date").item(0).getTextContent());
                                book.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
                                book.setPrice(Float.parseFloat(element.getElementsByTagName("price").item(0).getTextContent()));

                                book.addToDB(statement);
                            }
                        }
                    }
                    break;
                case "SAX":
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
                            book.addToDB(statement);
                        }
                    }
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
