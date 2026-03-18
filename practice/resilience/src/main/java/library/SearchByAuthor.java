package library;

import java.util.ArrayList;
import java.util.List;

public class SearchByAuthor implements SearchStrategy {

    @Override
    public List<Book> search(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}