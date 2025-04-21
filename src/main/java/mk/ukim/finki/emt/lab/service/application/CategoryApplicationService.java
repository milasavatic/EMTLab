package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.display.DisplayCategoryDto;

import java.util.List;

public interface CategoryApplicationService {
    List<DisplayCategoryDto> findAll();
}
