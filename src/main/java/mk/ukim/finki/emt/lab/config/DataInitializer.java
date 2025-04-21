package mk.ukim.finki.emt.lab.config;

import mk.ukim.finki.emt.lab.model.domain.*;
import mk.ukim.finki.emt.lab.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import mk.ukim.finki.emt.lab.model.enumerations.Role;

import java.util.ArrayList;
import java.util.List;


@Component
public class DataInitializer {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BookRepository bookRepository, AuthorRepository authorRepository, CountryRepository countryRepository, WishlistRepository wishlistRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //@PostConstruct
    public void init() {
        Country us = countryRepository.save(new Country("United States","North America"));
        Country e = countryRepository.save(new Country("England","Europe"));
        Country j = countryRepository.save(new Country("Japan","Asia"));

        Author sk = authorRepository.save(new Author("Stephen","King",us));
        Author ws = authorRepository.save(new Author("William","Shakespeare",e));
        Author hm = authorRepository.save(new Author("Haruki","Murakami",j));

        Book ham = bookRepository.save(new Book("Hamlet",Category.DRAMA,ws,10));
        Book rom = bookRepository.save(new Book("Romeo and Juliet",Category.DRAMA,ws,15));
        Book shi = bookRepository.save(new Book("The Shining",Category.THRILER,sk,7));
        Book it = bookRepository.save(new Book("It",Category.HISTORY,sk,8));
        Book nor = bookRepository.save(new Book("Norwegian wood",Category.NOVEL,hm,6));
        Book aft = bookRepository.save(new Book("After dark",Category.FANTASY,hm,3));

        User librarian = userRepository.save(new User(
                "librarian",
                passwordEncoder.encode("librarian"),
                "librarian",
                "librarian",
                Role.ROLE_ADMIN
        ));

        User user = userRepository.save(new User(
                "user",
                passwordEncoder.encode("user"),
                "user",
                "user",
                Role.ROLE_USER
        ));

        List<Book> books = new ArrayList<>();
        books.add(ham);
        books.add(rom);
        ws.setCounter(ws.getCounter() + 1);
        books.add(shi);
        Wishlist lib = wishlistRepository.save(new Wishlist(librarian));
        lib.setBooks(books);
        wishlistRepository.save(lib);

        books = new ArrayList<>();
        books.add(it);
        books.add(nor);
        books.add(aft);
        hm.setCounter(hm.getCounter() + 1);
        Wishlist usr = wishlistRepository.save(new Wishlist(user));
        usr.setBooks(books);
        wishlistRepository.save(usr);
    }

}
