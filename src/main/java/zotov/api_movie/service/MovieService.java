package zotov.api_movie.service;

import java.util.List;
import java.util.Optional;

import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;

public interface MovieService {
    List<MovieDTOResponse> findAll();
    Optional<MovieDTOResponse> findById(Long id);
    List<MovieDTOResponse> search(String title, String genre);
    MovieDTOResponse create(MovieDTORequest dto);
    Optional<MovieDTOResponse> update(Long id, MovieDTORequest dto);
    boolean deleteById(Long id);
}
