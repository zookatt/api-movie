package zotov.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zotov.api_movie.entity.GenreEntity;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}