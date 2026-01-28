package library;

public class PaperBook extends Book {
    public PaperBook(String title, String author) {
        super(title, author);
    }

    @Override
    public void read() {
        System.out.println("Đọc sách giấy: " + title);

    }
}
