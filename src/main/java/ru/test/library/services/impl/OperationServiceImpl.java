package ru.test.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.test.library.dto.OperationDto;
import ru.test.library.dto.TimeInterval;
import ru.test.library.dto.VisitorDto;
import ru.test.library.exceptions.BookIsAlreadyReturnedException;
import ru.test.library.exceptions.BookIsAlreadyTakenException;
import ru.test.library.exceptions.NoBooksLeftException;
import ru.test.library.models.Operation;
import ru.test.library.repositories.EditionRepository;
import ru.test.library.repositories.OperationRepository;
import ru.test.library.repositories.VisitorRepository;
import ru.test.library.services.OperationService;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final EditionRepository editionRepository;
    private final VisitorRepository visitorRepository;

    @Override
    public OperationDto takeBook(long visitorId, long editionId) {
        if (editionRepository.checkAvailability(editionId)>0) {

            if (operationRepository.getByVisitorAndEditionId(editionId,
                    visitorId, 0).isEmpty()) {
                operationRepository.minusBooksAvailable(editionId);
                Operation newOperation = Operation.builder()
                        .visitorId(visitorId)
                        .editionId(editionId)
                        .time(Instant.now())
                        .status(0)
                        .build();
                return OperationDto.from(operationRepository.save(newOperation));
            }
            else {
                throw new BookIsAlreadyTakenException();
            }
        }
        else {
            throw new NoBooksLeftException();
        }
    }

    @Override
    public OperationDto returnBook(long visitorId, long editionId) {
        if (operationRepository.getByVisitorAndEditionId(editionId,
                visitorId, 1).isEmpty()) {
            operationRepository.plusBooksAvailable(editionId);
            Operation newOperation = Operation.builder()
                    .visitorId(visitorId)
                    .editionId(editionId)
                    .time(Instant.now())
                    .status(1)
                    .build();
            return OperationDto.from(operationRepository.save(newOperation));
        }
        else {
            throw new BookIsAlreadyReturnedException();
        }
    }

    @Override
    public List<OperationDto> getHistory(long id) {
        return OperationDto.from(operationRepository.findAllByVisitorId(id));
    }

    @Override
    public List<VisitorDto> showAllBookKeepers(long editionID) {
        return VisitorDto.from(visitorRepository.getKeepersByBookId(editionID));
    }

    @Override
    public List<OperationDto> getStatistics(TimeInterval timeInterval) {
        return OperationDto.from(operationRepository.getStatistics(timeInterval.getStart(),
                timeInterval.getFinish()));
    }


}
