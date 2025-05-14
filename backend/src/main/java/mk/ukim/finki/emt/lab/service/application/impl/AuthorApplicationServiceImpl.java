package mk.ukim.finki.emt.lab.service.application.impl;

import mk.ukim.finki.emt.lab.dto.create.CreateAuthorDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayAuthorDto;
import mk.ukim.finki.emt.lab.model.domain.Country;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidCountryId;
import mk.ukim.finki.emt.lab.model.views.BooksPerAuthorView;
import mk.ukim.finki.emt.lab.repository.BooksPerAuthorViewRepository;
import mk.ukim.finki.emt.lab.service.application.AuthorApplicationService;
import mk.ukim.finki.emt.lab.service.domain.AuthorService;
import mk.ukim.finki.emt.lab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {
    private final AuthorService authorService;
    private final CountryService countryService;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;

    public AuthorApplicationServiceImpl(AuthorService authorService, CountryService countryService, BooksPerAuthorViewRepository booksPerAuthorViewRepository) {
        this.authorService = authorService;
        this.countryService = countryService;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
    }

    @Override
    public List<DisplayAuthorDto> findAll() {
        return this.authorService.findAll().stream().map(DisplayAuthorDto::from).toList();
    }

    @Override
    public Optional<DisplayAuthorDto> save(CreateAuthorDto createAuthorDto) {
        Optional<Country> country = countryService.findById(createAuthorDto.countryId());
        if(country.isEmpty()) {
            throw new InvalidCountryId(createAuthorDto.countryId());
        }
        return authorService.save(createAuthorDto.toAuthor(country.get()))
                .map(DisplayAuthorDto::from);
    }

    @Override
    public Optional<DisplayAuthorDto> findById(Long id) {
        return this.authorService.findById(id).map(DisplayAuthorDto::from);
    }

    @Override
    public Optional<DisplayAuthorDto> update(Long id, CreateAuthorDto createAuthorDto) {
        Optional<Country> country = countryService.findById(createAuthorDto.countryId());
        return authorService.update(id, createAuthorDto.toAuthor(country.orElse(null)))
                .map(DisplayAuthorDto::from);
    }

    @Override
    public void deleteById(Long id) {
        this.authorService.deleteById(id);
    }

    @Override
    public List<BooksPerAuthorView> findAllBooksPerAuthor() {
        return booksPerAuthorViewRepository.findAll();
    }

    @Override
    public BooksPerAuthorView findBooksPerAuthor(Long id) {
        return booksPerAuthorViewRepository.findById(id).orElseThrow();
    }

    @Override
    public void refreshMaterializedView() {
        booksPerAuthorViewRepository.refreshMaterializedView();
    }
}
