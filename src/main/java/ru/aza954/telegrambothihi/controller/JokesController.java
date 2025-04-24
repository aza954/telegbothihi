package ru.aza954.telegrambothihi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aza954.telegrambothihi.model.Jokes;
import ru.aza954.telegrambothihi.model.dto.JokeSaveDTO;
import ru.aza954.telegrambothihi.service.JokesService;

import java.util.List;

@RequestMapping("/api/jokes")
@RequiredArgsConstructor
@RestController
public class JokesController {

    private final JokesService jokesService;

    @Operation(
            summary = "Добавить шутку",
            description = "Позволяет добавить новую шутку в базу данных. Возвращает созданный объект шутки.",
            tags = {"jokes", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Шутка успешно создана",
                    content = @Content(schema = @Schema(implementation = Jokes.class), mediaType = "application/json")),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Jokes> addJokes(@RequestBody JokeSaveDTO joke) {
        Jokes saved = jokesService.addJokes(joke);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "Получить все шутки",
            description = "Возвращает список всех шуток из базы. При указании параметра title — выполняет фильтрацию по заголовку.",
            tags = {"jokes", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список шуток успешно получен",
                    content = @Content(schema = @Schema(implementation = Jokes.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<Jokes>> getAllJokes(
            @RequestParam(value = "title", required = false) String title) {
        List<Jokes> jokes = jokesService.getAllJokes(title);
        return ResponseEntity.ok(jokes);
    }

    @Operation(
            summary = "Получить шутку по ID",
            description = "Возвращает шутку по её уникальному идентификатору. Если шутка не найдена — возвращает 404.",
            tags = {"jokes", "get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно получена",
                    content = @Content(schema = @Schema(implementation = Jokes.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Jokes> getJokesById(@PathVariable("id") Long id) {
        Jokes joke = jokesService.getJokesById(id);
        return ResponseEntity.ok(joke);
    }

    @Operation(
            summary = "Редактировать шутку",
            description = "Позволяет изменить содержимое существующей шутки по её ID. При некорректных данных — 400, при отсутствии — 404.",
            tags = {"jokes", "put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно обновлена",
                    content = @Content(schema = @Schema(implementation = Jokes.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Jokes> editJokes(
            @PathVariable("id") Long id,
            @RequestBody JokeSaveDTO joke) {
        Jokes editedJoke = jokesService.editJokes(id, joke);
        return ResponseEntity.ok(editedJoke);
    }

    @Operation(
            summary = "Удалить шутку",
            description = "Удаляет шутку по указанному ID. Возвращает подтверждение удаления.",
            tags = {"jokes", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Шутка успешно удалена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Шутка с указанным ID не найдена",
                    content = @Content(schema = @Schema(implementation = String.class), mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJokes(@PathVariable("id") Long id) {
        jokesService.deleteJokes(id);
        return ResponseEntity.ok("Шутка успешно удалена");
    }
}
