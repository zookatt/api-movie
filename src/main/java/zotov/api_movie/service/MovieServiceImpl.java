package zotov.api_movie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.entity.MovieEntity;
import zotov.api_movie.exception.InvalidSearchCriteriaException;
import zotov.api_movie.exception.MoviesNotFoundException;
import zotov.api_movie.mapper.MovieMapper;
import zotov.api_movie.repository.MovieRepository;
import zotov.api_movie.entity.ReleaseYearEntity;
import zotov.api_movie.repository.ReleaseYearRepository;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReleaseYearRepository releaseYearRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ReleaseYearRepository releaseYearRepository) {
        this.movieRepository = movieRepository;
        this.releaseYearRepository = releaseYearRepository;
    }

    @Override
    public List<MovieDTOResponse> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(MovieMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<MovieDTOResponse> findById(Long id) {
        return movieRepository.findById(id)
                .map(MovieMapper::toDTO);
    }

    @Override
    public List<MovieDTOResponse> search(String title, String genre) {
        boolean hasTitle = StringUtils.hasText(title);
        boolean hasGenre = StringUtils.hasText(genre);
        if (hasTitle == hasGenre) {
            throw new InvalidSearchCriteriaException(
                    "Provide exactly one search parameter: title or genre");
        }
        List<MovieEntity> movies = hasTitle
                ? movieRepository.findByTitleContainingIgnoreCase(title.trim())
                : movieRepository.findDistinctByGenresNameContainingIgnoreCase(genre.trim());
        if (movies.isEmpty()) {
            throw new MoviesNotFoundException("No movies match the search criteria");
        }
        return movies.stream()
                .map(MovieMapper::toDTO)
                .toList();
    }

    private ReleaseYearEntity findOrCreateReleaseYear(Integer value) {
        return releaseYearRepository.findByValue(value)
                .orElseGet(() -> releaseYearRepository.save(
                        new ReleaseYearEntity(null, value)));
    }

    @Override
    public MovieDTOResponse create(MovieDTORequest dto) {
        ReleaseYearEntity releaseYear = findOrCreateReleaseYear(dto.year());
        MovieEntity movie = MovieMapper.toEntity(dto, releaseYear);
        MovieEntity savedMovie = movieRepository.save(movie);
        return MovieMapper.toDTO(savedMovie);
    }

    @Override
    public Optional<MovieDTOResponse> update(Long id, MovieDTORequest dto) {
        return movieRepository.findById(id)
                .map(existingMovie -> {
                    ReleaseYearEntity releaseYear = findOrCreateReleaseYear(dto.year());
                    existingMovie.setTitle(dto.title());
                    existingMovie.setReleaseYear(releaseYear);
                    MovieEntity updatedMovie = movieRepository.save(existingMovie);
                    return MovieMapper.toDTO(updatedMovie);
                });
    }

    @Override
    public boolean deleteById(Long id) {
        if (!movieRepository.existsById(id)) {
            return false;
        }
        movieRepository.deleteById(id);
        return true;
    }
}
