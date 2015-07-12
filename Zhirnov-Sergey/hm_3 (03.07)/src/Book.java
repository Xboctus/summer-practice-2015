import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by korcky on 11.07.15.
 */
public class Book {
    private String isbn;
    private String author;
    private String title;
    private String genre;
    private Float price;
    private String publishDate;
    private String description;

    protected void setIsbn(String line) {
        this.isbn = line;
    }

    protected void setAuthor(String line) {
        this.author = line;
    }

    protected void setTitle(String line) {
        this.title = line;
    }

    protected void setGenre(String line) {
        this.genre = line;
    }

    protected void setPublishDate(String line) {
        this.publishDate = line;
    }

    protected void setDescription(String line) {
        this.description = line;
    }

    protected void setPrice(Float num) {
        this.price = num;
    }

    protected void addToDB(Statement statement) throws SQLException {
        if (this.isbn == null) {
            this.isbn = "null";
        }

        String query = "select * " +
                "from Books " +
                "where ISBN = \"" + this.isbn + "\" and Author = \"" + this.author + "\" and Title = \"" + this.title + "\";";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            query = "update Books " +
                    "set Genre = \"" + this.genre +"\", PublishDate = \"" + this.publishDate +
                    "\", Description =\"" + this.description + "\", Price = " + this.price.toString() +
                    " where ISBN = \"" + this.isbn + "\" and Author = \"" + this.author + "\" and Title = \"" + this.title + "\";";
        } else {
            query = "insert into Books values (\"" + this.isbn + "\", \"" + this.author + "\", \"" + this.title + "\"," +
                    " \"" + this.genre + "\", \"" + this.publishDate + "\", \"" + this.description + "\", " + this.price.toString() + " );";
        }
        statement.executeUpdate(query);
    }
}
