package mk.ukim.finki.emt.lab.service.domain;

import mk.ukim.finki.emt.lab.model.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Page<Book> findAll(Pageable pageable);

    Optional<Book> save(Book book);

    //Optional<Book> save(BookDto bookDto);

    Optional<Book> findById(Long id);

    Optional<Book> update(Long id, Book book);

    //Optional<Book> update(Long id, BookDto bookDto);

    void deleteById(Long id);

    Optional<Book> markAsRented(Long id, String person);

    List<String> allWhoRented(Long id);

    Optional<Book> markAsBadCondition(Long id);

    void refreshMaterializedView();
}
