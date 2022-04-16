package ru.test.library.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test.library.dto.*;
import ru.test.library.services.EditionService;
import ru.test.library.services.OperationService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/edition")
public class EditionController {

    private final EditionService editionService;
    private final OperationService operationService;

    @Operation(summary = "Получение авторов с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с авторами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = EditionsPage.class)
                            )
                    }
            )
    })

    @GetMapping
    public ResponseEntity<EditionsPage> getEditions(
            @Parameter(description = "Номер страницы") @RequestParam("page") int page) {
        return ResponseEntity.ok(editionService.getEditions(page));
    }


    @PostMapping
    public ResponseEntity<EditionDto> addAuthor(@Valid @RequestBody EditionDto edition) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(editionService.addEdition(edition));
    }

    @PostMapping("/take")
    public ResponseEntity<OperationDto> takeBook(@RequestParam("visitorId") long visitorId,
                                                 @RequestParam("editionId") long editionId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(operationService.takeBook(visitorId, editionId));
    }

    @PostMapping("/return")
    public ResponseEntity<OperationDto> returnBook(@RequestParam("visitorId") long visitorId,
                                                   @RequestParam("editionId") long editionId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(operationService.returnBook(visitorId, editionId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<OperationDto>> showHistoryOfAVisitor(
            @Parameter(description = "Идентификатор посетителя") @RequestParam("id") long id) {
        return ResponseEntity
                .ok(operationService.getHistory(id));
    }



    @GetMapping("/keepers")
    public ResponseEntity<List<VisitorDto>> showVisitorsWithABook(
            @Parameter(description = "Идентификатор издания") @RequestParam("id") long id) {
        return ResponseEntity
                .ok(operationService.showAllBookKeepers(id));
    }

    @PostMapping("/statistics")
    public ResponseEntity<List<OperationDto>> showStatistics(@Valid @RequestBody TimeInterval interval) {
        return ResponseEntity
                .ok(operationService.getStatistics(interval));
    }

}
