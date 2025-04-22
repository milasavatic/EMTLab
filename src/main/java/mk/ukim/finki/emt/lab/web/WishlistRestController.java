package mk.ukim.finki.emt.lab.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.emt.lab.dto.WishlistDto;
import mk.ukim.finki.emt.lab.security.JwtHelper;
import mk.ukim.finki.emt.lab.service.application.WishlistApplicationService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist API", description = "Endpoints for managing the wishlist")
@Profile("dev")
public class WishlistRestController {

    private final WishlistApplicationService wishlistApplicationService;
    private final JwtHelper jwtHelper;

    public WishlistRestController(WishlistApplicationService wishlistApplicationService, JwtHelper jwtHelper) {
        this.wishlistApplicationService = wishlistApplicationService;
        this.jwtHelper = jwtHelper;
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
    public ResponseEntity<WishlistDto> getActiveWishlist(@RequestHeader("Authorization") String token, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!jwtHelper.isValid(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtHelper.extractUsername(token);
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
            @RequestHeader("Authorization") String token,
            Authentication authentication
    ) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (!jwtHelper.isValid(token, userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String username = jwtHelper.extractUsername(token);
            return wishlistApplicationService.addBookToWishlist(username, id)
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
    public ResponseEntity<String> rentAllBooksInWishlist(@RequestHeader("Authorization") String token, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!jwtHelper.isValid(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtHelper.extractUsername(token);
        wishlistApplicationService.rentAllBooksInWishlist(username);
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
    public ResponseEntity<Map<Integer, String>> getPopularity(@RequestHeader("Authorization") String token, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!jwtHelper.isValid(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = jwtHelper.extractUsername(token);
        return wishlistApplicationService.booksRentedByAuthor(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-author")
    @Operation(
            summary = "Get the total number of books per author per wishlist",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the number of books per author per wishlist"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<?> findAllNumberOfAuthorsPerCountry() {
        return ResponseEntity.status(HttpStatus.OK).body(wishlistApplicationService.findAllBooksPerAuthorPerWishlist());
    }

    @GetMapping("/by-author/{id}")
    @Operation(
            summary = "Get the number of books per author for a specific user's wishlist by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the number of books per author for the specified wishlist"),
                    @ApiResponse(responseCode = "404", description = "Wishlist not found with the provided ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<?> findNumberOfAuthorsPerCountry(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(wishlistApplicationService.findBooksPerAuthorPerWishlist(id));
    }
}
