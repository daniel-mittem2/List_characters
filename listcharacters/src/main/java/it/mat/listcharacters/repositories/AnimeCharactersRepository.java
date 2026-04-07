package it.mat.listcharacters.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.mat.listcharacters.domain.Characters;

public interface AnimeCharactersRepository extends JpaRepository<Characters, UUID> {
    
    @Query("SELECT c FROM Characters c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(c.cognome) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Characters> searchByNomeOrCognome(@Param("searchTerm") String searchTerm);
}
