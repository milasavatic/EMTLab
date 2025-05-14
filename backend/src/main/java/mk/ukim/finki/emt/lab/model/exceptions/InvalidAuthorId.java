package mk.ukim.finki.emt.lab.model.exceptions;

public class InvalidAuthorId extends RuntimeException{
    public InvalidAuthorId(Long id) {
        super(id.toString());
    }
}
