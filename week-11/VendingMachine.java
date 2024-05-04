import java.util.List;
import java.util.ArrayList;

public class VendingMachine {
    private List<Book> shelf;
    private double locationFactor;
    private int cassette;
    private int safe;
    private String password;

    public VendingMachine(double locationFactor, String password) {
        this.shelf = new ArrayList<Book>();
        this.locationFactor = locationFactor;
        this.cassette = 0;
        this.safe = 0;
        this.password = password;
    }

    public int getCassette() {
        return this.cassette;
    }

    public void insertCoin(int coin) {
        if (coin == 1 || coin == 2 || coin == 5 || coin == 10 || coin == 20 || coin == 50 || coin == 100 || coin == 200) {
            this.cassette += coin;
        } else {
            throw new IllegalArgumentException("Coin is not of the right denomination: " + coin);
        }
    }

    public int cancel() {
        int oldCassette = this.cassette;
        this.cassette = 0;
        return oldCassette;
    }


    public void restock(List<Book> books, String password) {
        if (this.password.equals(password)) {
            this.shelf.addAll(books);
        } else {
            throw new InvalidPasswordException("Invalid password for restocking");
        }
    }

    public int emptySafe(String password) {
        if (this.password.equals(password)) {
            int oldSafe = this.safe;
            this.safe = 0;
            return oldSafe;
        } else {
            throw new InvalidPasswordException("Invalid password for emptying safe");
        }
    }

    public List<String> getCatalogue() {
        List<String> catalogue = new ArrayList<String>();
        for (Book book : this.shelf) {
            catalogue.add(book.toString());
        }
        return catalogue;
    }

    public int getPrice(int index) {
        if (index < 0 || index >= this.shelf.size()) {
            throw new IndexOutOfBoundsException("Invalid book index: " + index);
        } else {
            Book book = this.shelf.get(index);
            int price = (int) Math.ceil(book.getPages() * this.locationFactor);
            return price;
        }
    }

    public Book buyBook(int index) {
        if (index < 0 || index >= this.shelf.size()) {
            throw new IndexOutOfBoundsException("Invalid book index: " + index);
        } else {
            Book book = this.shelf.get(index);
            int price = (int) Math.ceil(book.getPages() * this.locationFactor);
            if (price <= this.cassette) {
                this.shelf.remove(index);
                this.cassette -= price;
                this.safe += price;
                return book;
            } else {
                throw new CassetteException("Not enough money in cassette to buy book");
            }
        }
    }
}
