package library;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static Library instance;
    private SearchStrategy searchStrategy;

    private List<Book> books;
    private Library() {
        books = new ArrayList<>();
        // không cho tạo từ bên ngoài
    }
    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }
    public void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }
    public void searchBook(String keyword) {
        List<Book> result = searchStrategy.search(books, keyword);

        System.out.println("🔍 Kết quả tìm kiếm:");
        for (Book book : result) {
            System.out.println("- " + book.getTitle() + " | " + book.getAuthor());
        }
    }


    public void addBook(String type, String title, String author) {
        Book book = BookFactory.createBook(type, title, author);
        books.add(book);
        System.out.println(" Thêm sách thành công: " + title);

    }

    public void showBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

}
