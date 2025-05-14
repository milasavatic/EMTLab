package mk.ukim.finki.emt.lab.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mk.ukim.finki.emt.lab.dto.create.CreateCountryDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayCountryDto;
import mk.ukim.finki.emt.lab.model.domain.Country;
import mk.ukim.finki.emt.lab.service.application.CountryApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Countries", description = "REST API for managing countries")
public class CountryRestController {
    private final CountryApplicationService countryApplicationService;

    public CountryRestController(CountryApplicationService countryApplicationService) {
        this.countryApplicationService = countryApplicationService;
    }

    @Operation(summary = "Get all countries", description = "Retrieves a list of all countries")
    @GetMapping
    public List<DisplayCountryDto> findAll() {
        return this.countryApplicationService.findAll();
    }

    @Operation(summary = "Get country by ID", description = "Finds a country by its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Country found", content = @Content(schema = @Schema(implementation = Country.class))),
                    @ApiResponse(responseCode = "404", description = "Country not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayCountryDto> findById(@Parameter(description = "ID of the country") @PathVariable Long id) {
        return this.countryApplicationService.findById(id)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new country", description = "Creates and returns a new country",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Country created", content = @Content(schema = @Schema(implementation = Country.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/add")
    public ResponseEntity<DisplayCountryDto> save(@RequestBody CreateCountryDto createCountryDto) {
        return this.countryApplicationService.save(createCountryDto)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Update an existing country", description = "Modifies a country's information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Country updated", content = @Content(schema = @Schema(implementation = Country.class))),
                    @ApiResponse(responseCode = "404", description = "Country not found")
            })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayCountryDto> update(@PathVariable Long id,
                                        @RequestBody CreateCountryDto createCountryDto) {
        return this.countryApplicationService.update(id, createCountryDto)
                .map(country -> ResponseEntity.ok().body(country))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Delete a country", description = "Removes a country by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Country deleted"),
                    @ApiResponse(responseCode = "404", description = "Country not found")
            })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the country") @PathVariable Long id) {
        this.countryApplicationService.deleteById(id);
        if (this.countryApplicationService.findById(id).isPresent())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
