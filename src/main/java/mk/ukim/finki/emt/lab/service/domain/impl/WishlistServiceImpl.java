package mk.ukim.finki.emt.lab.service.domain.impl;

import mk.ukim.finki.emt.lab.model.domain.Author;
import mk.ukim.finki.emt.lab.model.domain.Book;
import mk.ukim.finki.emt.lab.model.domain.User;
import mk.ukim.finki.emt.lab.model.domain.Wishlist;
import mk.ukim.finki.emt.lab.model.exceptions.BookAlreadyInWishlistException;
import mk.ukim.finki.emt.lab.model.exceptions.BookNotFoundException;
import mk.ukim.finki.emt.lab.model.exceptions.NoAvailableCopies;
import mk.ukim.finki.emt.lab.model.exceptions.WishlistNotFoundException;
import mk.ukim.finki.emt.lab.repository.WishlistRepository;
import mk.ukim.finki.emt.lab.service.domain.AuthorService;
import mk.ukim.finki.emt.lab.service.domain.BookService;
import mk.ukim.finki.emt.lab.service.domain.UserService;
import mk.ukim.finki.emt.lab.service.domain.WishlistService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserService userService;
    private final BookService bookService;
    private final AuthorService authorService;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, UserService userService, BookService bookService, AuthorService authorService) {
        this.wishlistRepository = wishlistRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public List<Book> listAllBooksInWishlist(Long wishlistId) {
        if(wishlistRepository.findById(wishlistId).isEmpty())
            throw new WishlistNotFoundException(wishlistId.toString());
        return wishlistRepository.findById(wishlistId).get().getBooks();
    }

    @Override
    public Optional<Wishlist> getActiveWishlist(String username) {
        User user = userService.findByUsername(username);
        return Optional.of(wishlistRepository.findByUser(user)
                .orElseGet(() -> wishlistRepository.save(new Wishlist(user))));
    }

    @Override
    public Optional<Wishlist> addBookToWishlist(String username, Long bookId) {
        if(getActiveWishlist(username).isPresent()) {
            Wishlist wishlist = getActiveWishlist(username).get();
            Book book = bookService.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException(bookId));
            if(!wishlist.getBooks().stream().filter(i -> i.getId().equals(bookId)).toList().isEmpty())
                throw new BookAlreadyInWishlistException(bookId, username);
            if(book.getAvailableCopies() <= 0){
                throw new NoAvailableCopies(bookId);
            }
            wishlist.getBooks().add(book);
            Author author = book.getAuthor();
            author.setCounter(book.getAuthor().getCounter() + 1);
            authorService.update(author.getId(), author);
            return Optional.of(wishlistRepository.save(wishlist));
        }
        return Optional.empty();
    }

    @Override
    public void rentAllBooksInWishlist(String username) {
        User user = userService.findByUsername(username);
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));
        List<Book> allBooks = wishlist.getBooks();
        allBooks.forEach(book -> {
            if (book.getAvailableCopies() <= 0) {
                throw new NoAvailableCopies(book.getId());
            }
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookService.save(book);
        });
        wishlist.getBooks().clear();
        wishlistRepository.save(wishlist);
    }

    @Override
    public Optional<Map<Integer, String>> booksRentedByAuthor(String username){
        User user = userService.findByUsername(username);
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found"));
        List<Book> allBooks = wishlist.getBooks();
        Map<Integer, String> popularity = new TreeMap<>();
        allBooks.forEach(book -> {
            popularity.put(book.getAuthor().getCounter(), book.getAuthor().getName() + " " + book.getAuthor().getSurname());
        });
        return Optional.of(popularity);
    }
}
