package zotov.api_movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zotov.api_movie.entity.GenreEntity;
import zotov.api_movie.entity.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByTitleContainingIgnoreCase(String title);

    List<GenreEntity> findDistinctByGenresNameContainingIgnoreCase(String genre);
}
