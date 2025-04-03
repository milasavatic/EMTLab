package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.BookDto;
import mk.ukim.finki.emt.lab.dto.CreateBookDto;
import mk.ukim.finki.emt.lab.dto.DisplayBookDto;
import mk.ukim.finki.emt.lab.model.domain.Book;
import mk.ukim.finki.emt.lab.model.domain.Category;

import java.util.List;
import java.util.Optional;

public interface BookApplicationService {
    List<DisplayBookDto> findAll();

    //Optional<DisplayBookDto> save(String name, Category category, Long authorId, Integer availableCopies);

    Optional<DisplayBookDto> save(CreateBookDto bookDto);

    Optional<DisplayBookDto> findById(Long id);

    //Optional<DisplayBookDto> update(Long id, String name, Category category, Long authorId, Integer availableCopies);

    Optional<DisplayBookDto> update(Long id, CreateBookDto bookDto);

    void deleteById(Long id);

    Optional<DisplayBookDto> markAsRented(Long id, String person);

    List<String> allWhoRented(Long id);

    Optional<DisplayBookDto> markAsBadCondition(Long id);
}
