package ru.carcassonne.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.carcassonne.entity.dto.FieldRequest;
import ru.carcassonne.entity.dto.ReportDto;
import ru.carcassonne.usecases.FieldUseCases;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping(value = "/field")
@Tag(name = "fields", description = "Методы для работы с игровым полем")
public class RestController {

    private final FieldUseCases fieldUseCases;

    @Operation(operationId = "getResult",
            summary = "Подсчитать количество игровых элементов на игровом поле",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReportDto.class))),
                    @ApiResponse(responseCode = "400", description = "BadRequest, неправильный запрос",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
            }
    )
    @PostMapping(value = "/result", produces = "application/json;charset=UTF-8")
    ReportDto getResult(@RequestBody FieldRequest fieldRequest) {
        return fieldUseCases.calculate(fieldRequest);
    }

}
