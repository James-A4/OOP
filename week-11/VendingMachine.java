import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class VendingMachine {
    private List<Book> shelf;
    private double locationFactor;
    private int cassette;
    private int safe;
    private String password;

    public VendingMachine(double locationFactor, String password) {
        this.shelf = new ArrayList<>();
        this.locationFactor = locationFactor;
        this.cassette = 0;
        this.safe = 0;
        this.password = password;
    }

    public int getCassette() {
        return this.cassette;
    }

    public void insertCoin(int coin) {
        Set<Integer> acceptedCoins = new HashSet<>(Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200));
        if (acceptedCoins.contains(coin)) {
            cassette += coin;
        } else {
            throw new IllegalArgumentException("Invalid coin denomination");
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
            throw new InvalidPasswordException("The provided password is invalid for restocking books");
        }
    }

    public int emptySafe(String password) {
        if (this.password.equals(password)) {
            int oldSafe = this.safe;
            this.safe = 0;
            return oldSafe;
        } else {
            throw new InvalidPasswordException("The provided password is invalid for emptying the safe");
        }
    }

    public List<String> getCatalogue() {
        List<String> catalogue = new ArrayList<>();
        for (Book book : this.shelf) {
            catalogue.add(book.toString());
        }
        return catalogue;
    }

    public int getPrice(int index) {
        if (index < 0 || index >= this.shelf.size()) {
            throw new IndexOutOfBoundsException("Book index invalid: " + index);
        }

        Book book = this.shelf.get(index);
        int price = (int) Math.ceil(book.getPages() * this.locationFactor);
        return price;
    }

    public Book buyBook(int index) {
        if (index < 0 || index >= this.shelf.size()) {
            throw new IndexOutOfBoundsException("Book index invalid: " + index);
        }

        Book book = this.shelf.get(index);
        int price = (int) Math.ceil(book.getPages() * this.locationFactor);
        if (price > this.cassette) {
            throw new CassetteException("Insufficient funds in cassette to purchase book");
        }

        this.shelf.remove(index);
        this.cassette -= price;
        this.safe += price;
        return book;
    }
}



