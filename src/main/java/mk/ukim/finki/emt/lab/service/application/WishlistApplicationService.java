package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.DisplayBookDto;
import mk.ukim.finki.emt.lab.dto.WishlistDto;
import mk.ukim.finki.emt.lab.model.domain.Author;
import mk.ukim.finki.emt.lab.model.domain.Book;
import mk.ukim.finki.emt.lab.model.domain.User;
import mk.ukim.finki.emt.lab.model.domain.Wishlist;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface WishlistApplicationService {
    List<DisplayBookDto> listAllBooksInWishlist(Long wishlistId);

    Optional<WishlistDto> getActiveWishlist(String username);

    Optional<WishlistDto> addBookToWishlist(String username, Long bookId);

    void rentAllBooksInWishlist(String username);

    Optional<Map<Integer, String>> booksRentedByAuthor(String username);
}
