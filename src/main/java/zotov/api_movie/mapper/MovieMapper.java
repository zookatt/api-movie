package zotov.api_movie.mapper;

import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.entity.MovieEntity;

public class MovieMapper {

    public static MovieEntity toEntity(MovieDTORequest dto) {
        return new MovieEntity(null, dto.title(), dto.year());
    }

    public static MovieDTOResponse toDTO(MovieEntity entity) {
        return new MovieDTOResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getYear());
    }
}