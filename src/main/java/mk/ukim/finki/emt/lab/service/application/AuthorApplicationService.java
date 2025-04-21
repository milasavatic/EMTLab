package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.create.CreateAuthorDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayAuthorDto;
import mk.ukim.finki.emt.lab.model.views.BooksPerAuthorView;

import java.util.List;
import java.util.Optional;

public interface AuthorApplicationService {
    List<DisplayAuthorDto> findAll();

    Optional<DisplayAuthorDto> save(CreateAuthorDto createAuthorDto);

    Optional<DisplayAuthorDto> findById(Long id);

    Optional<DisplayAuthorDto> update(Long id, CreateAuthorDto createAuthorDto);

    void deleteById(Long id);

    List<BooksPerAuthorView> findAllBooksPerAuthor();

    BooksPerAuthorView findBooksPerAuthor(Long id);

    void refreshMaterializedView();
}
