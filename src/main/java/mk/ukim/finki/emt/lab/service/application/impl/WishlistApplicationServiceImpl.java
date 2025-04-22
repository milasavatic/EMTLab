package mk.ukim.finki.emt.lab.service.application.impl;

import mk.ukim.finki.emt.lab.dto.display.DisplayBookDto;
import mk.ukim.finki.emt.lab.dto.WishlistDto;
import mk.ukim.finki.emt.lab.model.views.BooksPerAuthorPerWishlistView;
import mk.ukim.finki.emt.lab.repository.BooksPerAuthorPerWishlistViewRepository;
import mk.ukim.finki.emt.lab.service.application.WishlistApplicationService;
import mk.ukim.finki.emt.lab.service.domain.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WishlistApplicationServiceImpl implements WishlistApplicationService {
    private final WishlistService wishlistService;
    private final BooksPerAuthorPerWishlistViewRepository booksPerAuthorPerWishlistViewRepository;

    public WishlistApplicationServiceImpl(WishlistService wishlistService, BooksPerAuthorPerWishlistViewRepository booksPerAuthorPerWishlistViewRepository) {
        this.wishlistService = wishlistService;
        this.booksPerAuthorPerWishlistViewRepository = booksPerAuthorPerWishlistViewRepository;
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

    @Override
    public Optional<Map<Integer, String>> booksRentedByAuthor(String username){
        return wishlistService.booksRentedByAuthor(username);
    }

    @Override
    public List<BooksPerAuthorPerWishlistView> findAllBooksPerAuthorPerWishlist() {
        return booksPerAuthorPerWishlistViewRepository.findAll();
    }

    @Override
    public BooksPerAuthorPerWishlistView findBooksPerAuthorPerWishlist(Long id) {
        return booksPerAuthorPerWishlistViewRepository.findById(id).orElseThrow();
    }

    @Override
    public void refreshMaterializedView() {
        booksPerAuthorPerWishlistViewRepository.refreshMaterializedView();
    }
}
