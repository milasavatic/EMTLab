package mk.ukim.finki.emt.lab.dto;

import lombok.Data;
import mk.ukim.finki.emt.lab.model.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto {
    String name;
    Category category;
    Long authorId;
    Integer availableCopies;
    Boolean rented;
    Boolean condition;
    List<String> personWhoRented;

    public BookDto(String name, Category category, Long authorId, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.authorId = authorId;
        this.availableCopies = availableCopies;
        this.rented = false;
        this.condition = true;
        this.personWhoRented = new ArrayList<String>();
    }

    public void setPersonWhoRented(String person) {
        this.personWhoRented.add(person);
    }
}
