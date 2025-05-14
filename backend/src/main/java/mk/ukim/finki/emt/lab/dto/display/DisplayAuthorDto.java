package mk.ukim.finki.emt.lab.dto.display;

import mk.ukim.finki.emt.lab.model.domain.Author;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayAuthorDto(Long id, String name, String surname, Long countryId) {
    public static DisplayAuthorDto from (Author author) {
        return new DisplayAuthorDto(author.getId(), author.getName(), author.getSurname(), author.getCountry().getId());
    }

    public static List<DisplayAuthorDto> from (List<Author> authors) {
        return authors.stream()
                .map(DisplayAuthorDto::from)
                .collect(Collectors.toList());
    }

//    public Author toAuthor(Country country) {
//        return new Author(name, surname, country);
//    }
}
