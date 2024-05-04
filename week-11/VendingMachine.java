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

/**
 * Represents a vending machine that sells books.
 */
public class VendingMachine {
    private List<Book> shelf;
    private double locationFactor;
    private int cassette;
    private int safe;
    private String password;

    /**
     * Constructs a new VendingMachine with the given location factor and password.
     *
     * @param locationFactor the factor that affects the price of the books
     * @param password the password for restocking and emptying the safe
     */
    public VendingMachine(double locationFactor, String password) {
        this.shelf = new ArrayList<>();
        this.locationFactor = locationFactor;
        this.cassette = 0;
        this.safe = 0;
        this.password = password;
    }

    /**
     * Returns the current amount in the cassette.
     *
     * @return the amount in the cassette
     */
    public int getCassette() {
        return this.cassette;
    }

    /**
     * Inserts a coin into the vending machine. Only certain denominations are accepted.
     *
     * @param coin the denomination of the coin
     * @throws IllegalArgumentException if the coin denomination is not accepted
     */
    public void insertCoin(int coin) {
        Set<Integer> acceptedCoins = new HashSet<>(Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200));
        if (acceptedCoins.contains(coin)) {
            cassette += coin;
        } else {
            throw new IllegalArgumentException("Invalid coin denomination");
        }
    }

    /**
    * Cancels the current transaction and returns the amount in the cassette.
    *
    * @return the amount that was in the cassette
    */
    public int cancel() {
        int oldCassette = this.cassette;
        this.cassette = 0;
        return oldCassette;
    }

    /**
    * Restocks the vending machine with books.
    *
    * @param books the list of books to add
    * @param password the password for restocking
    * @throws InvalidPasswordException if the provided password is incorrect
    */
    public void restock(List<Book> books, String password) {
        if (this.password.equals(password)) {
            this.shelf.addAll(books);
        } else {
            throw new InvalidPasswordException("The provided password is invalid for restocking books");
        }
    }

    /**
    * Empties the safe and returns the amount that was in it.
    *
    * @param password the password for emptying the safe
    * @return the amount that was in the safe
    * @throws InvalidPasswordException if the provided password is incorrect
    */
    public int emptySafe(String password) {
        if (this.password.equals(password)) {
            int oldSafe = this.safe;
            this.safe = 0;
            return oldSafe;
        } else {
            throw new InvalidPasswordException("The provided password is invalid for emptying the safe");
        }
    }

    /**
    * Returns a list of the books in the vending machine.
    *
    * @return a list of the books in the vending machine
    */
    public List<String> getCatalogue() {
        List<String> catalogue = new ArrayList<>();
        for (Book book : this.shelf) {
            catalogue.add(book.toString());
        }
        return catalogue;
    }

    /**
    * Returns the price of the book at the specified index.
    *
    * @param index the index of the book
    * @return the price of the book
    * @throws IndexOutOfBoundsException if the index is out of bounds
    */
    public int getPrice(int index) {
        if (index < 0 || index >= this.shelf.size()) {
            throw new IndexOutOfBoundsException("Book index invalid: " + index);
        }
    
        Book book = this.shelf.get(index);
        return (int) Math.ceil(book.getPages() * this.locationFactor);
    
    }
    

    /**
    * Buys the book at the specified index.
    *
    * @param index the index of the book
    * @return the book that was bought
    * @throws IndexOutOfBoundsException if the index is out of bounds
    * @throws CassetteException if there are insufficient funds in the cassette
    */
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




