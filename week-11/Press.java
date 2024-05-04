import java.io.*;
import java.util.*;


class CassetteException extends RuntimeException {
    public CassetteException(String message) {
        super(message);
    }
}
class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

/**
 * Represents a press that manages books.
 */
public class Press {
    private Map<String, List<Book>> shelf;
    private int shelfSize;
    private Map<String, Integer> edition;

    /**
     * Constructs a new Press.
     *
     * @param pathToBookDir the path to the directory containing the book files
     * @param shelfSize the size of the shelf
     */
    public Press(String pathToBookDir, int shelfSize) {
        this.shelf = new HashMap<>();
        this.edition = new HashMap<>();
        this.shelfSize = shelfSize;

        File bookDir = new File(pathToBookDir);
        File[] bookFiles = bookDir.listFiles();
        if (bookFiles == null) {
            bookFiles = new File[0];
        }
        for (File bookFile : bookFiles) {
            String bookID = bookFile.getName();
            if (bookID.endsWith(".txt")) {
                bookID = bookID.substring(0, bookID.length() - 4);

                try {
                    Book book = extractBook(bookFile, 0);
                    edition.put(bookID, 0);
                    shelf.put(bookID, new ArrayList<Book>(Collections.nCopies(shelfSize, null)));
                    List<Book> bookShelf = shelf.get(bookID);
                    bookShelf.set(0, book);

                } catch (IOException e) {
                    System.err.println("Book file could not be read: " + bookFile.getAbsolutePath());
                }
            }
        }
    }

    /**
     * Prints a new edition of a book.
     *
     * @param bookID the ID of the book to print
     * @param numEditions the number of new editions to print
     * @return the new book
     */
    protected Book print(String bookID, int numEditions) {
        int updatedEdition = this.edition.get(bookID) + numEditions;
        this.edition.put(bookID, updatedEdition);
        String filename = bookID + ".txt";
        File bookFile = new File(filename);
        try {
            Book book = extractBook(bookFile, updatedEdition);
            List<Book> bookShelf = shelf.get(bookID);
            if (bookShelf.size() < shelfSize) {
                bookShelf.add(book);
            } else {
                bookShelf.remove(0);
                bookShelf.add(book);
            }
            return book;
        } catch (IOException e) {
            System.err.println("Book file could not be read: " + bookFile.getAbsolutePath());
            return null;
        }
    }

    /**
     * Returns a list of books in the catalogue.
     *
     * @return a list of books in the catalogue
     */
    public List<String> getCatalogue() {
        List<String> catalogue = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : edition.entrySet()) {
            catalogue.add(entry.getKey() + " (" + entry.getValue() + ")");
        }
        return catalogue;
    }

    /**
     * Requests a number of books.
     *
     * @param bookID the ID of the book to request
     * @param numBooks the number of books to request
     * @return a list of the requested books
     */
    public List<Book> request(String bookID, int numBooks) {
        List<Book> books = new ArrayList<>();
        List<Book> bookShelf = shelf.get(bookID);
        int booksOnShelf = bookShelf.size() - Collections.frequency(bookShelf, null);
        int numBooksToPrint = Math.max(0, numBooks - booksOnShelf);
    
        // Remove books from the shelf and add them to the list
        for (int i = 0; i < Math.min(numBooks, booksOnShelf); i++) {
            books.add(bookShelf.remove(0));
        }
    
        // Print new books and add them to the list
        for (int i = 0; i < numBooksToPrint; i++) {
            Book book = print(bookID, 1);
            if (book != null) {
                books.add(book);
            }
        }
    
        return books;
    }

    /**
     * Extracts a book from a file.
     *
     * @param bookFile the file containing the book
     * @param edition the edition of the book
     * @return the extracted book
     * @throws IOException if an I/O error occurs
     */
    private static Book extractBook(File bookFile, int edition) throws IOException {
        String title = null;
        String author = null;
        StringBuilder content = new StringBuilder();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(bookFile))) {
            String line = reader.readLine();
    
            while (line != null) {
                if (line.startsWith("Title: ")) {
                    title = line.substring(7).trim();
                } else if (line.startsWith("Author: ")) {
                    author = line.substring(8).trim();
                } else if (line.startsWith("*** START OF")) {
                    line = reader.readLine();
                    break;
                }
                line = reader.readLine();
            }
    
            while (line != null) {
                content.append(line).append("\n");
                line = reader.readLine();
            }
        }
    
        return new Book(title, author, content.toString(), edition);
    }
}




