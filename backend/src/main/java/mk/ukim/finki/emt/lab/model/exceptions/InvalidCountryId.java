package mk.ukim.finki.emt.lab.model.exceptions;

public class InvalidCountryId extends RuntimeException{
    public InvalidCountryId(Long id){
        super(id.toString());
    }
}
