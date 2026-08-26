package zotov.api_movie.mapper;

import org.junit.jupiter.api.Test;
import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.entity.MovieEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MovieMapperTest {

    @Test
    void shouldConvertRequestDtoToEntity() {
        MovieDTORequest dto = new MovieDTORequest("Lost in Translation", 2003);
        MovieEntity entity = MovieMapper.toEntity(dto);
        assertNull(entity.getId());
        assertEquals("Lost in Translation", entity.getTitle());
        assertEquals(2003, entity.getYear());
    }

    @Test
    void shouldConvertEntityToResponseDto() {
        MovieEntity entity = new MovieEntity(1L, "Lost in Translation", 2003);
        MovieDTOResponse dto = MovieMapper.toDTO(entity);
        assertEquals(1L, dto.id());
        assertEquals("Lost in Translation", dto.title());
        assertEquals(2003, dto.year());
    }
}
