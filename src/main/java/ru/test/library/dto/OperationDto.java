package ru.test.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.test.library.models.Edition;
import ru.test.library.models.Operation;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDto {

    private Long id;

    private Long visitorId;
    private Long editionId;
    private Instant time;
    private int status;

    public static OperationDto from(Operation operation) {
        return OperationDto.builder()
                .id(operation.getId())
                .visitorId(operation.getVisitorId())
                .editionId(operation.getEditionId())
                .time(operation.getTime())
                .status(operation.getStatus())
                .build();
    }

    public static List<OperationDto> from(List<Operation> operations) {
        return operations.stream().map(OperationDto::from).collect(Collectors.toList());
    }
}
