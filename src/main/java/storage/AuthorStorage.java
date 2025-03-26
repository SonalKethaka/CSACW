package storage;

import model.Author;

import java.util.*;

public class AuthorStorage {
    private static final Map<Integer, Author> authorMap = new HashMap<>();
    private static int currentId = 1;

    public static Author addAuthor(Author author) {
        author.setId(currentId++);
        authorMap.put(author.getId(), author);
        return author;
    }

    public static List<Author> getAllAuthors() {
        return new ArrayList<>(authorMap.values());
    }

    public static Author getAuthorById(int id) {
        return authorMap.get(id);
    }

    public static Author updateAuthor(int id, Author updatedAuthor) {
        if (authorMap.containsKey(id)) {
            updatedAuthor.setId(id);
            authorMap.put(id, updatedAuthor);
            return updatedAuthor;
        }
        return null;
    }

    public static boolean deleteAuthor(int id) {
        return authorMap.remove(id) != null;
    }

    public static boolean exists(int id) {
        return authorMap.containsKey(id);
    }
}