package mk.ukim.finki.emt.lab.service.application.impl;

import mk.ukim.finki.emt.lab.dto.create.CreateCountryDto;
import mk.ukim.finki.emt.lab.dto.display.DisplayCountryDto;
import mk.ukim.finki.emt.lab.model.views.AuthorsPerCountryView;
import mk.ukim.finki.emt.lab.repository.AuthorsPerCountryViewRepository;
import mk.ukim.finki.emt.lab.service.application.CountryApplicationService;
import mk.ukim.finki.emt.lab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {
    private final CountryService countryService;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;

    public CountryApplicationServiceImpl(CountryService countryService, AuthorsPerCountryViewRepository authorsPerCountryViewRepository) {
        this.countryService = countryService;
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
    }

    @Override
    public List<DisplayCountryDto> findAll() {
        return this.countryService.findAll().stream().map(DisplayCountryDto::from).toList();
    }

    @Override
    public Optional<DisplayCountryDto> save(CreateCountryDto createCountryDto) {
        return this.countryService.save(createCountryDto.toCountry())
                .map(DisplayCountryDto::from);
    }

    @Override
    public Optional<DisplayCountryDto> findById(Long id) {
        return this.countryService.findById(id).map(DisplayCountryDto::from);
    }

    @Override
    public Optional<DisplayCountryDto> update(Long id, CreateCountryDto createCountryDto) {
        return countryService.update(id, createCountryDto.toCountry())
                .map(DisplayCountryDto::from);
    }

    @Override
    public void deleteById(Long id) {
        this.countryService.deleteById(id);
    }

    @Override
    public List<AuthorsPerCountryView> findAllAuthorsPerCountry() {
        return authorsPerCountryViewRepository.findAll();
    }

    @Override
    public AuthorsPerCountryView findAuthorsPerCountry(Long id) {
        return authorsPerCountryViewRepository.findById(id).orElseThrow();
    }

    @Override
    public void refreshMaterializedView() {
        authorsPerCountryViewRepository.refreshMaterializedView();
    }
}
