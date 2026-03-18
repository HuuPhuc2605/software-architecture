package library;

public class EBook extends Book{
    public EBook(String title, String author) {
        super(title, author);
    }

    @Override
    public void read() {
        System.out.println("Đọc ebook trên thiết bị: " + title);

    }
}
