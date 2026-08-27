package zotov.api_movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovieDTORequest(

        @NotBlank(message = "Title is required") String title,

        @NotNull(message = "Year is required") @Positive(message = "Year must be positive") Integer year

) {
}
