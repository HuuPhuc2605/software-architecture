package library;

public class Main {
    public static void main(String[] args) {

        Library library = Library.getInstance();




        library.setSearchStrategy(new SearchByTitle());
        library.searchBook("Java");
        Observer staff = new Staff();
        Observer userA = new UserA();
        library.addObserver(staff);
        library.addObserver(userA);
        library.addBook("paper", "Java cơ bản", "ABC");
        library.addBook("ebook", "Design Pattern", "ABC");
        library.addBook("paper", "Java nâng cao", "ABC");

        library.setSearchStrategy(new SearchByAuthor());
        library.searchBook("ABC");
    }

    }

