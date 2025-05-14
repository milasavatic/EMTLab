package mk.ukim.finki.emt.lab.model.exceptions;

public class BookAlreadyInWishlistException extends RuntimeException {
    public BookAlreadyInWishlistException(Long id, String message) {
        super(id.toString() + " " + message);
    }
}
