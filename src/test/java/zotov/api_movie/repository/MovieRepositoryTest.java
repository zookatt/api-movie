package zotov.api_movie.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import zotov.api_movie.entity.GenreEntity;
import zotov.api_movie.entity.MovieEntity;
import zotov.api_movie.entity.ActorEntity;
import zotov.api_movie.entity.ReleaseYearEntity;

import java.util.Optional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class MovieRepositoryTest {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ReleaseYearRepository releaseYearRepository;

    @Test
    void shouldSaveAndFindMovieById() {
        ReleaseYearEntity releaseYear = releaseYearRepository.save(
                new ReleaseYearEntity(null, 2003));
        MovieEntity movie = new MovieEntity(null, "Lost in Translation", releaseYear);
        MovieEntity savedMovie = movieRepository.save(movie);
        Optional<MovieEntity> foundMovie = movieRepository.findById(savedMovie.getId());
        assertTrue(foundMovie.isPresent());
        assertEquals("Lost in Translation", foundMovie.get().getTitle());
        assertEquals(2003, foundMovie.get().getReleaseYear().getValue());
    }

    @Test
    void shouldFindMoviesByGenreIgnoringCase() {
        GenreEntity genre = genreRepository.save(new GenreEntity(null, "Drama"));
        ReleaseYearEntity releaseYear = releaseYearRepository.save(
                new ReleaseYearEntity(null, 2003));
        MovieEntity movie = new MovieEntity(null, "Lost in Translation", releaseYear);
        movie.setGenres(Set.of(genre));
        movieRepository.saveAndFlush(movie);
        List<MovieEntity> movies = movieRepository
                .findDistinctByGenresNameContainingIgnoreCase("dra");
        assertEquals(1, movies.size());
        assertEquals("Lost in Translation", movies.get(0).getTitle());
    }

    @Test
    void shouldSaveMovieWithActorRelationship() {
        ActorEntity actor = actorRepository.save(
                new ActorEntity(null, "Bill Murray"));
        ReleaseYearEntity releaseYear = releaseYearRepository.save(
                new ReleaseYearEntity(null, 2003));
        MovieEntity movie = new MovieEntity(
                null,
                "Lost in Translation",
                releaseYear);
        movie.setActors(Set.of(actor));
        MovieEntity savedMovie = movieRepository.saveAndFlush(movie);
        Optional<MovieEntity> foundMovie = movieRepository.findById(savedMovie.getId());
        assertTrue(foundMovie.isPresent());
        assertEquals(1, foundMovie.get().getActors().size());
        assertEquals(
                "Bill Murray",
                foundMovie.get()
                        .getActors()
                        .iterator()
                        .next()
                        .getName());
    }
}
