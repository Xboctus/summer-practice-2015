import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.xml.sax.Attributes;

/**
 * Created by sergej on 10.07.15.
 */
public class SaxHandler extends DefaultHandler {
    public List<Book> books = new ArrayList<>();

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

    public void characters(char ch[], int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();
        if(value.length() == 0) return; // ignore white space

        if(currentElement().equals("author")) {
            Book book = (Book) this.objectStack.peek();
            book.author = value;
        } else if(currentElement().equals("title")) {
            Book book = (Book) this.objectStack.peek();
            book.title = value;
        } else if(currentElement().equals("isbn")) {
            Book book = (Book) this.objectStack.peek();
            book.isbn = value;
        }
    }

    private String currentElement() {
        return this.elementStack.peek();
    }
}
