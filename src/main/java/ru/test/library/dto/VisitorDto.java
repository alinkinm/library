package ru.test.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.test.library.models.Operation;
import ru.test.library.models.Visitor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitorDto {

    private Long id;

    public static VisitorDto from(Visitor visitor) {
        return VisitorDto.builder()
                .id(visitor.getId())
                .build();
    }

    public static List<VisitorDto> from(List<Visitor> visitors) {
        return visitors.stream().map(VisitorDto::from).collect(Collectors.toList());
    }
}
