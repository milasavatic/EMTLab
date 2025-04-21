package mk.ukim.finki.emt.lab.dto.display;

import mk.ukim.finki.emt.lab.model.domain.Category;

public record DisplayCategoryDto(String category) {
    public static DisplayCategoryDto from (Category category) {
        return new DisplayCategoryDto(category.name());
    }
}
