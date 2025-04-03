package mk.ukim.finki.emt.lab.service.application.impl;

import mk.ukim.finki.emt.lab.dto.DisplayAuthorDto;
import mk.ukim.finki.emt.lab.dto.DisplayCategoryDto;
import mk.ukim.finki.emt.lab.repository.CategoryRepository;
import mk.ukim.finki.emt.lab.service.application.CategoryApplicationService;
import mk.ukim.finki.emt.lab.service.domain.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryApplicationServiceImpl implements CategoryApplicationService {
    private final CategoryService categoryService;

    public CategoryApplicationServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public List<DisplayCategoryDto> findAll() {
        return this.categoryService.findAll().stream().map(DisplayCategoryDto::from).toList();
    }
}
