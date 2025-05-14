package mk.ukim.finki.emt.lab.model.exceptions;

public class NoAvailableCopies extends RuntimeException {
    public NoAvailableCopies(Long id) {
        super("No available copies of book with following id: " + id.toString());
    }
}
