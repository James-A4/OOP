public class Book {
    private String title;
    private String author;
    private String content;
    private int edition;

    /**
     * Constructs a new Book with the given title, author, content, and edition.
     *
     * @param t the title of the book
     * @param a the author of the book
     * @param c the content of the book
     * @param e the edition of the book
     */
    public Book(String t, String a, String c, int e) {
        this.title = t;
        this.author = a;
        this.content = c;
        this.edition = e;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the content of the book.
     *
     * @return the content of the book
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the edition of the book.
     *
     * @return the edition of the book
     */
    public int getEdition() {
        return edition;
    }

    /**
     * Returns the number of pages in the book, assuming each page can hold 750 characters.
     *
     * @return the number of pages in the book
     */
    public int getPages() {
        double contentLength = content.length();
        return (int) Math.ceil(contentLength / 750);
    }

    /**
     * Returns a string representation of the book, including its title, author, and edition.
     *
     * @return a string representation of the book
     */
    @Override
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nEdition: " + edition + "\n";
    }
}
