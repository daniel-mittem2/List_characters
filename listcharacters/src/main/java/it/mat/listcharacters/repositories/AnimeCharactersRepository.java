package it.mat.listcharacters.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mat.listcharacters.domain.Characters;

public interface AnimeCharactersRepository extends JpaRepository<Characters, UUID> {
    
}
