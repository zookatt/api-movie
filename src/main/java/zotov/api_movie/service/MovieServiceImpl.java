package zotov.api_movie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.mapper.MovieMapper;
import zotov.api_movie.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
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
    public MovieDTOResponse create(MovieDTORequest dto) {
        return MovieMapper.toDTO(
                movieRepository.save(MovieMapper.toEntity(dto))
        );
    }

    @Override
    public Optional<MovieDTOResponse> update(Long id, MovieDTORequest dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
