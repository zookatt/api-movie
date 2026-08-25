package zotov.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zotov.api_movie.entity.ReleaseYearEntity;

public interface ReleaseYearRepository extends JpaRepository<ReleaseYearEntity, Long> {
}