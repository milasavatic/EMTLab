package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.display.DisplayBookDto;
import mk.ukim.finki.emt.lab.dto.WishlistDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WishlistApplicationService {
    List<DisplayBookDto> listAllBooksInWishlist(Long wishlistId);

    Optional<WishlistDto> getActiveWishlist(String username);

    Optional<WishlistDto> addBookToWishlist(String username, Long bookId);

    void rentAllBooksInWishlist(String username);

    Optional<Map<Integer, String>> booksRentedByAuthor(String username);
}
