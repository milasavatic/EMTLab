package mk.ukim.finki.emt.lab.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mk.ukim.finki.emt.lab.dto.create.CreateAuthorDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayAuthorDto;
import mk.ukim.finki.emt.lab.model.domain.Author;
import mk.ukim.finki.emt.lab.model.projections.AuthorNameProjection;
import mk.ukim.finki.emt.lab.repository.AuthorRepository;
import mk.ukim.finki.emt.lab.service.application.AuthorApplicationService;
import mk.ukim.finki.emt.lab.service.application.CountryApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "REST API for managing authors")
public class AuthorRestController {
    private final AuthorApplicationService authorApplicationService;
    private final CountryApplicationService countryApplicationService;
    private final AuthorRepository authorRepository;

    public AuthorRestController(AuthorApplicationService authorApplicationService, CountryApplicationService countryApplicationService, AuthorRepository authorRepository) {
        this.authorApplicationService = authorApplicationService;
        this.countryApplicationService = countryApplicationService;
        this.authorRepository = authorRepository;
    }

    @Operation(summary = "Get all authors", description = "Retrieves a list of all authors")
    @GetMapping
    public List<DisplayAuthorDto> findAll() {
        return this.authorApplicationService.findAll();
    }

    @Operation(summary = "Get author by ID", description = "Finds an author by their unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author found", content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "404", description = "Author not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayAuthorDto> findById(@Parameter(description = "ID of the author") @PathVariable Long id) {
        return this.authorApplicationService.findById(id)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new author", description = "Creates and returns a new author",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author created", content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/add")
    public ResponseEntity<DisplayAuthorDto> save(@RequestBody CreateAuthorDto createAuthorDto) {
        return this.authorApplicationService.save(createAuthorDto)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Update an existing author", description = "Modifies an author's information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author updated", content = @Content(schema = @Schema(implementation = Author.class))),
                    @ApiResponse(responseCode = "404", description = "Author not found")
            })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayAuthorDto> update(@Parameter(description = "ID of the author") @PathVariable Long id,
                                         @RequestBody CreateAuthorDto createAuthorDto) {
        return this.authorApplicationService.update(id, createAuthorDto)
                .map(author -> ResponseEntity.ok().body(author))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an author", description = "Removes an author by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Author deleted"),
                    @ApiResponse(responseCode = "404", description = "Author not found")
            })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the author") @PathVariable Long id) {
        this.authorApplicationService.deleteById(id);
        if (this.authorApplicationService.findById(id).isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-country")
    @Operation(
            summary = "Get the total number of authors per country",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the number of authors per country"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<?> findAllNumberOfAuthorsPerCountry() {
        return ResponseEntity.status(HttpStatus.OK).body(countryApplicationService.findAllAuthorsPerCountry());
    }

    @GetMapping("/by-country/{id}")
    @Operation(
            summary = "Get the number of authors for a specific country by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the number of authors for the specified country"),
                    @ApiResponse(responseCode = "404", description = "Country not found with the provided ID"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<?> findNumberOfAuthorsPerCountry(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(countryApplicationService.findAuthorsPerCountry(id));
    }

    @GetMapping("/names")
    @Operation(
            summary = "Get first and last names of all authors",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved author names"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<List<AuthorNameProjection>> getAllAuthorNames() {
        List<AuthorNameProjection> authorNames = authorRepository.findAllBy();
        return ResponseEntity.ok(authorNames);
    }
}
