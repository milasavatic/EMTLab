package mk.ukim.finki.emt.lab.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Enumerated(EnumType.STRING)
    Category category;
    @ManyToOne
    Author author;
    Integer availableCopies;
    Boolean rented;
    Boolean condition;
    @ElementCollection
    List<String> personWhoRented;

    public Book(){}

    public Book(String name, Category category, Author author, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
        this.rented = false;
        this.condition = true;
        this.personWhoRented = new ArrayList<String>();
    }

//    public Book(Long id, String name, Category category, Author author, Integer availableCopies) {
//        this.id = id;
//        this.name = name;
//        this.category = category;
//        this.author = author;
//        this.availableCopies = availableCopies;
//        this.rented = false;
//        this.condition = true;
//        this.personWhoRented = new ArrayList<String>();
//    }

    public void setPersonWhoRented(String person) {
        this.personWhoRented.add(person);
    }
}
