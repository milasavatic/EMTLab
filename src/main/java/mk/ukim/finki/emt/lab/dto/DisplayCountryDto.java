package mk.ukim.finki.emt.lab.dto;

import mk.ukim.finki.emt.lab.model.domain.Country;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayCountryDto(String name, String continent) {
    public static DisplayCountryDto from (Country country) {
        return new DisplayCountryDto(country.getName(), country.getContinent());
    }

    public static List<DisplayCountryDto> from (List<Country> countries) {
        return countries.stream()
                .map(DisplayCountryDto::from)
                .collect(Collectors.toList());
    }
}
