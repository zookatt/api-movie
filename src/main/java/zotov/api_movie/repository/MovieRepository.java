package zotov.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zotov.api_movie.entity.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}
