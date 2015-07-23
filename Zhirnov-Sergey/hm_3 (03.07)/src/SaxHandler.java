import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.xml.sax.Attributes;

/**
 * Created by korcky on 12.07.15.
 */
public class SaxHandler extends org.xml.sax.helpers.DefaultHandler {
    public List<Book> books = new ArrayList<Book>();
    private Stack<String> elementStack = new Stack<String>();
    private Stack<Object> objectStack  = new Stack<Object>();


    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.elementStack.push(qName);

        if(qName.equals("book")){
            Book book = new Book();
            this.objectStack.push(book);
            this.books.add(book);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.elementStack.pop();

        if(qName.equals("book")){
            this.objectStack.pop();
        }
    }

    private String currentElement() {
        return this.elementStack.peek();
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if(value.length() == 0) return; // ignore white space

        Book book = (Book) this.objectStack.peek();
        switch(currentElement()) {
            case "isbn":
                book.setIsbn(value);
                break;
            case "author":
                book.setAuthor(value);
                break;
            case "title":
                book.setTitle(value);
                break;
            case "genre":
                book.setGenre(value);
                break;
            case "price":
                book.setPrice(Float.parseFloat(value));
                break;
            case "publish_date":
                book.setPublishDate(value);
                break;
            case "description":
                book.setDescription(value);
                break;
        }
    }
}
