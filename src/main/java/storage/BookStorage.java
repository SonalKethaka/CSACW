package storage;

import model.Book;

import java.util.*;

public class BookStorage {
    private static final Map<Integer, Book> bookMap = new HashMap<>();
    private static int currentId = 1;

    public static Book addBook(Book book) {
        book.setId(currentId++);
        bookMap.put(book.getId(), book);
        return book;
    }

    public static List<Book> getAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    public static Book getBookById(int id) {
        return bookMap.get(id);
    }

    public static Book updateBook(int id, Book updatedBook) {
        if (bookMap.containsKey(id)) {
            updatedBook.setId(id);
            bookMap.put(id, updatedBook);
            return updatedBook;
        }
        return null;
    }

    public static boolean deleteBook(int id) {
        return bookMap.remove(id) != null;
    }

    public static boolean exists(int id) {
        return bookMap.containsKey(id);
    }
}