package mk.ukim.finki.emt.lab.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mk.ukim.finki.emt.lab.dto.display.DisplayCategoryDto;
import mk.ukim.finki.emt.lab.service.application.CategoryApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "REST API for managing categories")
public class CategoryRestController {
    private final CategoryApplicationService categoryApplicationService;

    public CategoryRestController(CategoryApplicationService categoryApplicationService) {
        this.categoryApplicationService = categoryApplicationService;
    }

    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categories found")
            })
    @GetMapping
    public List<DisplayCategoryDto> findAll() {
        return this.categoryApplicationService.findAll();
    }
}
