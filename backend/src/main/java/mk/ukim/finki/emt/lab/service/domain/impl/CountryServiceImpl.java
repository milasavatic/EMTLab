package mk.ukim.finki.emt.lab.service.domain.impl;

import mk.ukim.finki.emt.lab.model.domain.Author;
import mk.ukim.finki.emt.lab.model.domain.Country;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidCountryId;
import mk.ukim.finki.emt.lab.model.exceptions.UnfilledArgumentsException;
import mk.ukim.finki.emt.lab.repository.CountryRepository;
import mk.ukim.finki.emt.lab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        return this.countryRepository.findAll();
    }

    @Override
    public Optional<Country> save(Country country) {
        if(country.getName().isEmpty() || country.getContinent().isEmpty())
            throw new UnfilledArgumentsException();
        return Optional.of(this.countryRepository.save(new Country(country.getName(), country.getContinent())));
    }

    @Override
    public Optional<Country> findById(Long id) {
        return this.countryRepository.findById(id);
    }

    @Override
    public Optional<Country> update(Long id, Country country) {
        if(this.countryRepository.findById(id).isEmpty()){
            throw new InvalidCountryId(id);
        }
        return this.countryRepository.findById(id).map(existingCountry -> {
            if(country.getName() != null) {
                existingCountry.setName(country.getName());
            }
            if(country.getContinent() != null) {
                existingCountry.setContinent(country.getContinent());
            }
            return countryRepository.save(existingCountry);

        });
    }

    @Override
    public void deleteById(Long id) {
        if(this.findById(id).isEmpty())
            throw new InvalidCountryId(id);
        this.countryRepository.deleteById(id);
    }
}
