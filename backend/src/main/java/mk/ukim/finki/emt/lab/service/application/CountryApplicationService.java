package mk.ukim.finki.emt.lab.service.application;

import mk.ukim.finki.emt.lab.dto.create.CreateCountryDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayCountryDto;
import mk.ukim.finki.emt.lab.model.views.AuthorsPerCountryView;

import java.util.List;
import java.util.Optional;

public interface CountryApplicationService {
    List<DisplayCountryDto> findAll();

    Optional<DisplayCountryDto> save(CreateCountryDto createCountryDto);

    Optional<DisplayCountryDto> findById(Long id);

    Optional<DisplayCountryDto> update(Long id, CreateCountryDto createCountryDto);

    void deleteById(Long id);

    List<AuthorsPerCountryView> findAllAuthorsPerCountry();

    AuthorsPerCountryView findAuthorsPerCountry(Long id);

    void refreshMaterializedView();
}
