package mk.ukim.finki.emt.lab.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mk.ukim.finki.emt.lab.dto.create.CreateBookDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayBookDto;
import mk.ukim.finki.emt.lab.model.domain.Book;
import mk.ukim.finki.emt.lab.service.application.AuthorApplicationService;
import mk.ukim.finki.emt.lab.service.application.BookApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "REST API for managing books")
public class BookRestController {
    private final BookApplicationService bookApplicationService;
    private final AuthorApplicationService authorApplicationService;

    public BookRestController(BookApplicationService bookApplicationService, AuthorApplicationService authorApplicationService) {
        this.bookApplicationService = bookApplicationService;
        this.authorApplicationService = authorApplicationService;
    }

    @Operation(summary = "Get all books", description = "Retrieves a list of all books")
    @GetMapping
    public List<DisplayBookDto> findAll() {
        return this.bookApplicationService.findAll();
    }

    @Operation(summary = "Get book by ID", description = "Finds a book by its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book found", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayBookDto> findById(@Parameter(description = "ID of the book") @PathVariable Long id) {
        return this.bookApplicationService.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new book", description = "Creates and returns a new book",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book created", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/add")
    public ResponseEntity<DisplayBookDto> save(@RequestBody CreateBookDto bookDto) {
        return this.bookApplicationService.save(bookDto)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Update an existing book", description = "Modifies a book's information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book updated", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayBookDto> update(@Parameter(description = "ID of the book") @PathVariable Long id,
                                       @RequestBody CreateBookDto bookDto) {
        return this.bookApplicationService.update(id, bookDto)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a book", description = "Removes a book by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book deleted"),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the book") @PathVariable Long id) {
        this.bookApplicationService.deleteById(id);
        if (this.bookApplicationService.findById(id).isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Mark book as rented", description = "Marks a book as rented",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book marked as rented", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            })
    @PutMapping("/mark/{id}")
    public ResponseEntity<DisplayBookDto> markAsRented(@Parameter(description = "ID of the book") @PathVariable Long id, @RequestParam String person) {
        return this.bookApplicationService.markAsRented(id, person)
                .map(book -> ResponseEntity.ok().body(book))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all people who rented book with given id", description = "Retrieves a list of all people who rented book with given id")
    @GetMapping("/peopleWhoRented/{id}")
    public List<String> allWhoRented(@Parameter(description = "ID of the book") @PathVariable Long id) {
        return this.bookApplicationService.allWhoRented(id);
    }

    @Operation(summary = "Mark book as bad condition", description = "Marks a book as in bad condition",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book marked as in bad condition", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            })
    @PutMapping("/condition/{id}")
    public ResponseEntity<DisplayBookDto> markBookAsBadCondition(@Parameter(description = "ID of the book") @PathVariable Long id) {
        return bookApplicationService.markAsBadCondition(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-author")
    @Operation(summary = "Get number of books per author for every author")
    public ResponseEntity<?> findAllNumberOfBooksPerAuthor() {
        return ResponseEntity.status(HttpStatus.OK).body(authorApplicationService.findAllBooksPerAuthor());
    }

    @GetMapping("/by-author/{id}")
    @Operation(summary = "Get number of books per author for a given author")
    public ResponseEntity<?> findNumberOfBooksPerAuthor(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(authorApplicationService.findBooksPerAuthor(id));
    }
}
