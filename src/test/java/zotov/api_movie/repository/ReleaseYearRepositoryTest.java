package zotov.api_movie.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import zotov.api_movie.entity.ReleaseYearEntity;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class ReleaseYearRepositoryTest {

    @Autowired
    private ReleaseYearRepository releaseYearRepository;

    @Test
    void shouldFindReleaseYearByValue() {
        releaseYearRepository.save(
                new ReleaseYearEntity(null, 2003));
        Optional<ReleaseYearEntity> foundYear = releaseYearRepository.findByValue(2003);
        assertTrue(foundYear.isPresent());
        assertEquals(2003, foundYear.get().getValue());
    }
}
