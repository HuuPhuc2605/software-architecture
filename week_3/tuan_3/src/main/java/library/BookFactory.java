package library;

public class BookFactory {
    public static Book createBook(String type, String title, String author) {

        if (type.equalsIgnoreCase("paper")) {
            return new PaperBook(title, author);
        }

        if (type.equalsIgnoreCase("ebook")) {
            return new EBook(title, author);
        }

        throw new IllegalArgumentException("Loại sách không hợp lệ");
    }
}
