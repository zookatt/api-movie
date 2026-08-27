-- Genres
INSERT INTO genres (id, name)
VALUES (DEFAULT, 'Drama');
INSERT INTO genres (id, name)
VALUES (DEFAULT, 'Comedy');
INSERT INTO genres (id, name)
VALUES (DEFAULT, 'Science Fiction');
INSERT INTO genres (id, name)
VALUES (DEFAULT, 'Crime');
INSERT INTO genres (id, name)
VALUES (DEFAULT, 'Animation');

-- Movies
INSERT INTO movies (id, title, release_year)
VALUES (DEFAULT, 'Lost in Translation', 2003);
INSERT INTO movies (id, title, release_year)
VALUES (DEFAULT, 'The Godfather', 1972);
INSERT INTO movies (id, title, release_year)
VALUES (DEFAULT, 'Interstellar', 2014);
INSERT INTO movies (id, title, release_year)
VALUES (DEFAULT, 'The Grand Budapest Hotel', 2014);
INSERT INTO movies (id, title, release_year)
VALUES (DEFAULT, 'Spirited Away', 2001);


-- Relationship between movies and genres
-- Lost in Translation → Drama + Comedy
INSERT INTO movie_genres (movie_id, genre_id) VALUES (1, 1);
INSERT INTO movie_genres (movie_id, genre_id) VALUES (1, 2);

-- The Godfather → Drama + Crime
INSERT INTO movie_genres (movie_id, genre_id) VALUES (2, 1);
INSERT INTO movie_genres (movie_id, genre_id) VALUES (2, 4);

-- Interstellar → Drama + Science Fiction
INSERT INTO movie_genres (movie_id, genre_id) VALUES (3, 1);
INSERT INTO movie_genres (movie_id, genre_id) VALUES (3, 3);

-- The Grand Budapest Hotel → Comedy
INSERT INTO movie_genres (movie_id, genre_id) VALUES (4, 2);

-- Spirited Away → Animation
INSERT INTO movie_genres (movie_id, genre_id) VALUES (5, 5);