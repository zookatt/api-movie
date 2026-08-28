package zotov.api_movie.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import zotov.api_movie.dto.MovieDTORequest;
import zotov.api_movie.dto.MovieDTOResponse;
import zotov.api_movie.service.MovieService;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private MovieService movieService;

        @Test
        void shouldReturnAllMovies() throws Exception {
                MovieDTOResponse movie = new MovieDTOResponse(1L, "Lost in Translation", 2003);
                when(movieService.findAll()).thenReturn(List.of(movie));
                mockMvc.perform(get("/api/v1/movies"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].title").value("Lost in Translation"))
                                .andExpect(jsonPath("$[0].year").value(2003));
        }

        @Test
        void shouldReturnMovieById() throws Exception {
                MovieDTOResponse movie = new MovieDTOResponse(1L, "Lost in Translation", 2003);
                when(movieService.findById(1L)).thenReturn(Optional.of(movie));
                mockMvc.perform(get("/api/v1/movies/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.title").value("Lost in Translation"))
                                .andExpect(jsonPath("$.year").value(2003));
        }

        @Test
        void shouldReturnNotFoundWhenMovieDoesNotExist() throws Exception {
                when(movieService.findById(99L)).thenReturn(Optional.empty());
                mockMvc.perform(get("/api/v1/movies/99"))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldCreateMovie() throws Exception {
                MovieDTOResponse movie = new MovieDTOResponse(1L, "Lost in Translation", 2003);
                when(movieService.create(any(MovieDTORequest.class))).thenReturn(movie);
                mockMvc.perform(post("/api/v1/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "title": "Lost in Translation",
                                                    "year": 2003
                                                }
                                                """))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.title").value("Lost in Translation"))
                                .andExpect(jsonPath("$.year").value(2003));
        }

        @Test
        void shouldUpdateMovie() throws Exception {
                MovieDTOResponse movie = new MovieDTOResponse(1L, "Lost in Translation", 2003);
                when(movieService.update(any(Long.class), any(MovieDTORequest.class))).thenReturn(Optional.of(movie));
                mockMvc.perform(put("/api/v1/movies/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "title": "Lost in Translation",
                                                    "year": 2003
                                                }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.title").value("Lost in Translation"))
                                .andExpect(jsonPath("$.year").value(2003));
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingMissingMovie() throws Exception {
                when(movieService.update(any(Long.class), any(MovieDTORequest.class))).thenReturn(Optional.empty());
                mockMvc.perform(put("/api/v1/movies/99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                {
                                                    "title": "Lost in Translation",
                                                    "year": 2003
                                                }
                                                """))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldDeleteMovie() throws Exception {
                when(movieService.deleteById(1L)).thenReturn(true);
                mockMvc.perform(delete("/api/v1/movies/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        void shouldReturnNotFoundWhenDeletingMissingMovie() throws Exception {
                when(movieService.deleteById(99L)).thenReturn(false);
                mockMvc.perform(delete("/api/v1/movies/99"))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldSearchMoviesByTitle() throws Exception {
                MovieDTOResponse movie = new MovieDTOResponse(1L, "Lost in Translation", 2003);
                when(movieService.search("Lost", null))
                                .thenReturn(List.of(movie));
                mockMvc.perform(
                                get("/api/v1/movies/search")
                                                .param("title", "Lost"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].title")
                                                .value("Lost in Translation"))
                                .andExpect(jsonPath("$[0].year").value(2003));
        }

        @Test
        void shouldSearchMoviesByGenre() throws Exception {
                MovieDTOResponse movie = new MovieDTOResponse(1L, "Lost in Translation", 2003);
                when(movieService.search(null, "Drama"))
                                .thenReturn(List.of(movie));
                mockMvc.perform(
                                get("/api/v1/movies/search")
                                                .param("genre", "Drama"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].title")
                                                .value("Lost in Translation"))
                                .andExpect(jsonPath("$[0].year").value(2003));
        }
}