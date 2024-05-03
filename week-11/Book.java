public class Book {
    private String title;
    private String author;
    private String content;
    private int edition;

    public Book(String t, String a, String c, int e) {


        this.title = t;
        this.author = a;
        this.content = c;
        this.edition = e;
    }




    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public int getEdition() {
        return edition;
    }

    public int getPages() {
        int contentLength = content.length();
        int pages = contentLength / 700;
        if (contentLength % 700 != 0) {
            pages++;
    
        }
        return pages;
    }


    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nEdition: " + edition + "\n";
    }
}
