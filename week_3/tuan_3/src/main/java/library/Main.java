package library;

public class Main {
    public static void main(String[] args) {

        Library library = Library.getInstance();

        library.addBook("paper", "Java cơ bản", "ABC");
        library.addBook("ebook", "Design Pattern", "ABC");
        library.addBook("paper", "Java nâng cao", "ABC");

        // 🔍 Tìm theo tên
        library.setSearchStrategy(new SearchByTitle());
        library.searchBook("Java");

        // 🔍 Tìm theo tác giả
        library.setSearchStrategy(new SearchByAuthor());
        library.searchBook("Phúc");
    }

    }

