package mk.ukim.finki.emt.lab.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.emt.lab.repository.AuthorsPerCountryViewRepository;
import mk.ukim.finki.emt.lab.repository.BooksPerAuthorPerWishlistViewRepository;
import mk.ukim.finki.emt.lab.repository.BooksPerAuthorViewRepository;
import org.springframework.stereotype.Component;

@Component
public class MaterializedViewRefresher {
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;
    private final BooksPerAuthorPerWishlistViewRepository booksPerAuthorPerWishlistViewRepository;

    public MaterializedViewRefresher(BooksPerAuthorViewRepository booksPerAuthorViewRepository, AuthorsPerCountryViewRepository authorsPerCountryViewRepository, BooksPerAuthorPerWishlistViewRepository booksPerAuthorPerWishlistViewRepository) {
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
        this.booksPerAuthorPerWishlistViewRepository = booksPerAuthorPerWishlistViewRepository;
    }

    @PostConstruct
    public void init() {
        booksPerAuthorViewRepository.refreshMaterializedView();
        authorsPerCountryViewRepository.refreshMaterializedView();
        booksPerAuthorPerWishlistViewRepository.refreshMaterializedView();
    }
}
