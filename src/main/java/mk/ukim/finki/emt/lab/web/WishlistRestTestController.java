package mk.ukim.finki.emt.lab.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.emt.lab.dto.WishlistDto;
import mk.ukim.finki.emt.lab.model.domain.User;
import mk.ukim.finki.emt.lab.service.application.WishlistApplicationService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist API", description = "Endpoints for managing the wishlist")
@Profile("test")
public class WishlistRestTestController {

    private final WishlistApplicationService wishlistApplicationService;

    public WishlistRestTestController(WishlistApplicationService wishlistApplicationService) {
        this.wishlistApplicationService = wishlistApplicationService;
    }

    @Operation(
            summary = "Get active wishlist",
            description = "Retrieves the active wishlist for the logged-in user"
    )
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "Wishlist retrieved successfully"
            ), @ApiResponse(responseCode = "404", description = "Wishlist not found")}
    )
    @GetMapping
    public ResponseEntity<WishlistDto> getActiveWishlist(HttpServletRequest req) {
        String username = req.getRemoteUser();
        return wishlistApplicationService.getActiveWishlist(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add book to wishlist",
            description = "Adds a book to the wishlist for the logged-in user"
    )
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200", description = "Book added to wishlist successfully"
            ), @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ), @ApiResponse(responseCode = "404", description = "Book not found")}
    )
    @PostMapping("/add-book/{id}")
    public ResponseEntity<WishlistDto> addBookToWishlist(
            @PathVariable Long id,
            Authentication authentication
    ) {
        try {
            User user = (User) authentication.getPrincipal();
            return wishlistApplicationService.addBookToWishlist(user.getUsername(), id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Rent all books from wishlist",
            description = "Rents all books from the wishlist for the logged-in user"
    )
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200", description = "All books rented from wishlist successfully"
            ), @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ), @ApiResponse(responseCode = "404", description = "Wishlist not found")}
    )
    @PostMapping("/rent-all")
    public ResponseEntity<String> rentAllBooksInWishlist(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        wishlistApplicationService.rentAllBooksInWishlist(user.getUsername());
        return ResponseEntity.ok("All books in your wishlist have been rented successfully.");
    }

    @Operation(
            summary = "Book popularity",
            description = "Returns book popularity"
    )
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "Popularity retrieved successfully"
            ), @ApiResponse(responseCode = "404", description = "Wishlist not found")}
    )
    @GetMapping("/popularity")
    public ResponseEntity<Map<Integer, String>> getPopularity(HttpServletRequest req) {
        String username = req.getRemoteUser();
        return wishlistApplicationService.booksRentedByAuthor(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
