package zotov.api_movie.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.entity.MovieEntity;
import zotov.api_movie.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void shouldFindAllMovies() {
        MovieEntity movie = new MovieEntity(1L, "Lost in Translation", 2003);
        when(movieRepository.findAll()).thenReturn(List.of(movie));
        List<MovieDTOResponse> movies = movieService.findAll();
        assertEquals(1, movies.size());
        assertEquals(1L, movies.get(0).id());
        assertEquals("Lost in Translation", movies.get(0).title());
        assertEquals(2003, movies.get(0).year());
    }

    @Test
    void shouldFindMovieById() {
        MovieEntity movie = new MovieEntity(1L, "Lost in Translation", 2003);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        Optional<MovieDTOResponse> foundMovie = movieService.findById(1L);
        assertTrue(foundMovie.isPresent());
        assertEquals(1L, foundMovie.get().id());
        assertEquals("Lost in Translation", foundMovie.get().title());
        assertEquals(2003, foundMovie.get().year());
    }

    @Test
    void shouldReturnEmptyWhenMovieDoesNotExist() {
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<MovieDTOResponse> foundMovie = movieService.findById(99L);
        assertTrue(foundMovie.isEmpty());
    }

    @Test
    void shouldCreateMovie() {
        MovieDTORequest dto = new MovieDTORequest("Lost in Translation", 2003);
        MovieEntity savedMovie = new MovieEntity(1L, "Lost in Translation", 2003);
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(savedMovie);
        MovieDTOResponse response = movieService.create(dto);
        assertEquals(1L, response.id());
        assertEquals("Lost in Translation", response.title());
        assertEquals(2003, response.year());
    }

    @Test
    void shouldUpdateExistingMovie() {
        MovieDTORequest dto = new MovieDTORequest("Lost in Translation", 2003);
        MovieEntity existingMovie = new MovieEntity(1L, "Old title", 2000);
        MovieEntity savedMovie = new MovieEntity(1L, "Lost in Translation", 2003);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(MovieEntity.class))).thenReturn(savedMovie);
        Optional<MovieDTOResponse> response = movieService.update(1L, dto);
        assertTrue(response.isPresent());
        assertEquals(1L, response.get().id());
        assertEquals("Lost in Translation", response.get().title());
        assertEquals(2003, response.get().year());
    }

    @Test
    void shouldReturnEmptyWhenUpdatingMissingMovie() {
        MovieDTORequest dto = new MovieDTORequest("Lost in Translation", 2003);
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<MovieDTOResponse> response = movieService.update(99L, dto);
        assertTrue(response.isEmpty());
    }

    @Test
    void shouldDeleteExistingMovie() {
        when(movieRepository.existsById(1L)).thenReturn(true);
        boolean deleted = movieService.deleteById(1L);
        assertTrue(deleted);
        verify(movieRepository).deleteById(1L);
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingMovie() {
        when(movieRepository.existsById(99L)).thenReturn(false);
        boolean deleted = movieService.deleteById(99L);
        assertEquals(false, deleted);
        verify(movieRepository, never()).deleteById(99L);
    }

    @Test
    void shouldSearchMoviesByTitle() {
        MovieEntity movie = new MovieEntity(1L, "Lost in Translation", 2003);
        when(movieRepository.findByTitleContainingIgnoreCase("Lost"))
                .thenReturn(List.of(movie));
        List<MovieDTOResponse> result = movieService.search(" Lost ", null);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("Lost in Translation", result.get(0).title());
        assertEquals(2003, result.get(0).year());
        verify(movieRepository)
                .findByTitleContainingIgnoreCase("Lost");
    }

    @Test
    void shouldSearchMoviesByGenre() {
        MovieEntity movie = new MovieEntity(1L, "Lost in Translation", 2003);
        when(movieRepository
                .findDistinctByGenresNameContainingIgnoreCase("Drama"))
                .thenReturn(List.of(movie));
        List<MovieDTOResponse> result = movieService.search(null, " Drama ");
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("Lost in Translation", result.get(0).title());
        assertEquals(2003, result.get(0).year());
        verify(movieRepository)
                .findDistinctByGenresNameContainingIgnoreCase("Drama");
    }
}
