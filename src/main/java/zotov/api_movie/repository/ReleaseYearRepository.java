package zotov.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zotov.api_movie.entity.ReleaseYearEntity;
import java.util.Optional;

public interface ReleaseYearRepository extends JpaRepository<ReleaseYearEntity, Long> {
    Optional<ReleaseYearEntity> findByValue(Integer value);
}