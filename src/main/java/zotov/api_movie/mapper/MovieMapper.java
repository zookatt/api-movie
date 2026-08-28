package zotov.api_movie.mapper;

import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.entity.MovieEntity;
import zotov.api_movie.entity.ReleaseYearEntity;

public class MovieMapper {

    public static MovieEntity toEntity(MovieDTORequest dto, ReleaseYearEntity releaseYear) {
        return new MovieEntity(null, dto.title(), releaseYear);
    }

    public static MovieDTOResponse toDTO(MovieEntity entity) {
        return new MovieDTOResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getReleaseYear().getValue());
    }
}
