package ru.test.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.test.library.models.Edition;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditionDto {

    private Long id;

    private String name;
    private String description;
    private int numberOfBooks;
    private int booksAvailable;

    public static EditionDto from(Edition edition) {
        return EditionDto.builder()
                .id(edition.getId())
                .name(edition.getName())
                .description(edition.getDescription())
                .numberOfBooks(edition.getNumberOfBooks())
                .booksAvailable(edition.getBooksAvailable())
                .build();

    }

    public static List<EditionDto> from(List<Edition> authors) {
        return authors.stream().map(EditionDto::from).collect(Collectors.toList());
    }

    public static String forSearch(Edition edition) {
        return edition.getName() + " " + edition.getDescription();
    }

}
