package zotov.api_movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zotov.api_movie.entity.ActorEntity;

public interface ActorRepository extends JpaRepository<ActorEntity, Long>{
    
}
