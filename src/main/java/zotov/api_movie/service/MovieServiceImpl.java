package zotov.api_movie.service;

import org.springframework.stereotype.Service;

import zotov.api_movie.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private void findAll() {

    }

    private void findById() {

    }
}
