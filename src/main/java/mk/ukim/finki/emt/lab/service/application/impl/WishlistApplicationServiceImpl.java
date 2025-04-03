package mk.ukim.finki.emt.lab.service.application.impl;

import mk.ukim.finki.emt.lab.dto.DisplayBookDto;
import mk.ukim.finki.emt.lab.dto.WishlistDto;
import mk.ukim.finki.emt.lab.model.domain.User;
import mk.ukim.finki.emt.lab.model.domain.Wishlist;
import mk.ukim.finki.emt.lab.model.exceptions.WishlistNotFoundException;
import mk.ukim.finki.emt.lab.repository.WishlistRepository;
import mk.ukim.finki.emt.lab.service.application.WishlistApplicationService;
import mk.ukim.finki.emt.lab.service.domain.BookService;
import mk.ukim.finki.emt.lab.service.domain.UserService;
import mk.ukim.finki.emt.lab.service.domain.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistApplicationServiceImpl implements WishlistApplicationService {
    private final WishlistService wishlistService;
//    private final UserService userService;
//    private final BookService bookService;

    public WishlistApplicationServiceImpl(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
//        this.userService = userService;
//        this.bookService = bookService;
    }

    @Override
    public List<DisplayBookDto> listAllBooksInWishlist(Long wishlistId) {
        return DisplayBookDto.from(wishlistService.listAllBooksInWishlist(wishlistId));
    }

    @Override
    public Optional<WishlistDto> getActiveWishlist(String username) {
        return wishlistService.getActiveWishlist(username).map(WishlistDto::from);
    }

    @Override
    public Optional<WishlistDto> addBookToWishlist(String username, Long bookId) {
        return wishlistService.addBookToWishlist(username, bookId).map(WishlistDto::from);
    }

    @Override
    public void rentAllBooksInWishlist(String username) {
        wishlistService.rentAllBooksInWishlist(username);
    }
}
