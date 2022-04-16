package ru.test.library.services;

import ru.test.library.dto.EditionDto;
import ru.test.library.dto.EditionsPage;

public interface EditionService {

    EditionsPage getEditions(int page);

    EditionDto addEdition(EditionDto edition);





}
