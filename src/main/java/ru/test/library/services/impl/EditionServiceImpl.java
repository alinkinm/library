package ru.test.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.test.library.dto.EditionDto;
import ru.test.library.dto.EditionsPage;
import ru.test.library.models.Edition;
import ru.test.library.repositories.EditionRepository;
import ru.test.library.services.EditionService;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
@Service
public class EditionServiceImpl implements EditionService {

    private final EditionRepository editionRepository;

    @Value("3")
    private int defaultPageSize;


    @Override
    public EditionDto addEdition(EditionDto edition) {
        return EditionDto.from(editionRepository.save(
                Edition.builder()
                        .name(edition.getName())
                        .description(edition.getDescription())
                        .numberOfBooks(edition.getNumberOfBooks())
                        .booksAvailable(edition.getBooksAvailable())
                        .build()));
    }

    @Override
    public EditionsPage getEditions(int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Edition> editionPage = editionRepository.findAll(pageRequest);
        return EditionsPage.builder()
                .editions(EditionDto.from(editionPage.getContent()))
                .totalPages(editionPage.getTotalPages())
                .build();
    }

}
