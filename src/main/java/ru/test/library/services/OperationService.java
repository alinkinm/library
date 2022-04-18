package ru.test.library.services;


import ru.test.library.dto.OperationDto;
import ru.test.library.dto.TimeInterval;
import ru.test.library.dto.VisitorDto;

import java.util.List;

public interface OperationService {

    OperationDto takeBook(long visitorId, long editionId);

    OperationDto returnBook(long visitorId, long editionId);

    List<OperationDto> getHistory(long id);

    List<VisitorDto> showAllBookKeepers(long editionID);

    List<OperationDto> getStatistics(TimeInterval timeInterval);




}
