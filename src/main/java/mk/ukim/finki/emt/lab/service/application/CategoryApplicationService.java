package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.DisplayCategoryDto;
import mk.ukim.finki.emt.lab.model.domain.Category;

import java.util.List;

public interface CategoryApplicationService {
    List<DisplayCategoryDto> findAll();
}
