package zotov.api_movie.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import zotov.api_movie.entity.MovieEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void shouldSaveAndFindMovieById() {
        MovieEntity movie = new MovieEntity(null, "Lost in Translation", 2003);
        MovieEntity savedMovie = movieRepository.save(movie);
        Optional<MovieEntity> foundMovie = movieRepository.findById(savedMovie.getId());
        assertTrue(foundMovie.isPresent());
        assertEquals("Lost in Translation", foundMovie.get().getTitle());
        assertEquals(2003, foundMovie.get().getYear());
    }
}
