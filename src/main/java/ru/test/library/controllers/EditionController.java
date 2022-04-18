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

    @Operation(summary = "Получение изданий с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница со списком всех изданий",
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

    @Operation(summary = "Добавление издания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Добавленное издание",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = EditionDto.class)
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<EditionDto> addEdition(@Valid @RequestBody EditionDto edition) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(editionService.addEdition(edition));
    }

    @Operation(summary = "Взятие книги из библиотеки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись взятия книги\n" +
                    "статус: 0 - книга у посетителя",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = OperationDto.class)
                            )
                    }
            )
    })
    @PostMapping("/take")
    public ResponseEntity<OperationDto> takeBook(@RequestParam("visitorId") long visitorId,
                                                 @RequestParam("editionId") long editionId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(operationService.takeBook(visitorId, editionId));
    }

    @Operation(summary = "Возвращение книги в библиотеку")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись возвращения книги\n" +
                    "статус: 1 - книга в библиотеке",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = OperationDto.class)
                            )
                    }
            )
    })
    @PostMapping("/return")
    public ResponseEntity<OperationDto> returnBook(@RequestParam("visitorId") long visitorId,
                                                   @RequestParam("editionId") long editionId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(operationService.returnBook(visitorId, editionId));
    }

    @Operation(summary = "История операций посетителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех действий с книгами посетителя по айди",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = OperationDto.class)
                            )
                    }
            )
    })
    @GetMapping("/history")
    public ResponseEntity<List<OperationDto>> showHistoryOfAVisitor(
            @Parameter(description = "Идентификатор посетителя") @RequestParam("id") long id) {
        return ResponseEntity
                .ok(operationService.getHistory(id));
    }



    @Operation(summary = "Список посетителей с изданием на руках")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех посетителей, которые взяли книгу" +
                    ", которая на данный момент она находится у них",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = VisitorDto.class)
                            )
                    }
            )
    })
    @GetMapping("/keepers")
    public ResponseEntity<List<VisitorDto>> showVisitorsWithABook(
            @Parameter(description = "Идентификатор издания") @RequestParam("id") long id) {
        return ResponseEntity
                .ok(operationService.showAllBookKeepers(id));
    }

    @Operation(summary = "Статистика книг и посетителей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Все операции в определенный промежуток времени",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = OperationDto.class)
                            )
                    }
            )
    })
    @PostMapping("/statistics")
    public ResponseEntity<List<OperationDto>> showStatistics(@Valid @RequestBody TimeInterval interval) {
        return ResponseEntity
                .ok(operationService.getStatistics(interval));
    }

    @Operation(summary = "Поиск")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с именами и описаниями подходящих изданий",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = String.class)
                            )
                    }
            )
    })

    @GetMapping("/search")
    public ResponseEntity<List<String>> findByNameOrDesc(
            @Parameter(description = "Слово для поиска") @RequestParam("line") String line) {
        return ResponseEntity.ok(editionService.search(line));
    }


}
