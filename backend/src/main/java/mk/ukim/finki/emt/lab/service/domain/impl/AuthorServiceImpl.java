package mk.ukim.finki.emt.lab.service.domain.impl;

import mk.ukim.finki.emt.lab.model.domain.Author;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidAuthorId;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidCountryId;
import mk.ukim.finki.emt.lab.model.exceptions.UnfilledArgumentsException;
import mk.ukim.finki.emt.lab.repository.AuthorRepository;
import mk.ukim.finki.emt.lab.repository.AuthorsPerCountryViewRepository;
import mk.ukim.finki.emt.lab.service.domain.AuthorService;
import mk.ukim.finki.emt.lab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CountryService countryService;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryService countryService, AuthorsPerCountryViewRepository authorsPerCountryViewRepository) {
        this.authorRepository = authorRepository;
        this.countryService = countryService;
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> save(Author author) {
        Optional<Author> savedAuthor = Optional.empty();
        if(author.getName().isEmpty() || author.getSurname().isEmpty()) {
            throw new UnfilledArgumentsException();
        }
        if(this.countryService.findById(author.getCountry().getId()).isEmpty()) {
            throw new InvalidCountryId(author.getCountry().getId());
        }
        if(countryService.findById(author.getCountry().getId()).isPresent()) {
            savedAuthor = Optional.of(this.authorRepository.save(new Author(author.getName(),
                    author.getSurname(), countryService.findById(author.getCountry().getId()).get())));
        }
        this.refreshMaterializedView();
        return savedAuthor;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public Optional<Author> update(Long id, Author author) {
        if(this.authorRepository.findById(id).isEmpty()) {
            throw new InvalidAuthorId(author.getId());
        }
        if(this.countryService.findById(author.getCountry().getId()).isEmpty()) {
            throw new InvalidCountryId(author.getCountry().getId());
        }
        return this.authorRepository.findById(id).map(existingAuthor -> {
            if(author.getName() != null) {
                existingAuthor.setName(author.getName());
            }
            if(author.getSurname() != null) {
                existingAuthor.setSurname(author.getSurname());
            }
            if(author.getCountry() != null && countryService.findById(author.getCountry().getId()).isEmpty()) {
                existingAuthor.setCountry(countryService.findById(author.getCountry().getId()).get());
            }
            Author updatedAuthor = this.authorRepository.save(existingAuthor);
            this.refreshMaterializedView();
            return updatedAuthor;
        });

    }

    @Override
    public void deleteById(Long id) {
        if(this.findById(id).isEmpty())
            throw new InvalidAuthorId(id);
        this.authorRepository.deleteById(id);
    }

    @Override
    public void refreshMaterializedView(){
        authorsPerCountryViewRepository.refreshMaterializedView();
    }
}
