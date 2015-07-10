import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by sergej on 10.07.15.
 */
public class Book {
    public String isbn;
    public String author;
    public String title;

    public void insertToDB(Statement statement) throws SQLException {
        if (this.isbn == null) {
            this.isbn = "null";
        }

        String query = "SELECT * FROM books WHERE author = \"" + this.author + "\" AND title = \"" + this.title + "\"";
        ResultSet rs = statement.executeQuery(query);
        if(rs.next()) {
            query = "UPDATE books SET isbn = \"" + this.isbn + "\" WHERE author = \"" + this.author + "\" AND title = \"" + this.title + "\"";
        }
        else {
            query = "INSERT INTO books VALUES (\"" + this.author + "\", \"" + this.title + "\", \"" + this.isbn + "\")";
        }
        statement.executeUpdate(query);
    }
}
