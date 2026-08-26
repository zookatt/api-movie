package zotov.api_movie.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.service.MovieService;

@RestController
@RequestMapping(path = "${api-endpoint}/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public List<MovieDTOResponse> index() {
        return movieService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieDTOResponse> getById(@PathVariable Long id) {
        return movieService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<MovieDTOResponse> store(@Valid @RequestBody MovieDTORequest dto) {
        MovieDTOResponse dtoResponse = movieService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<MovieDTOResponse> update(@PathVariable Long id, @Valid @RequestBody MovieDTORequest dto) {
        return movieService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
