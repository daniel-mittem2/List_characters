package it.mat.listcharacters.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.mat.listcharacters.domain.Characters;
import it.mat.listcharacters.domain.CharactersForm;
import it.mat.listcharacters.repositories.AnimeCharactersRepository;

@Service
public class AnimeCharactersService {

    // Cartella dove vengono salvate le immagini (dentro il progetto)
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private AnimeCharactersRepository animeCharactersRepository;

    public Characters save(CharactersForm charactersForm) {
        Characters c = mapCharacter(charactersForm);
        return animeCharactersRepository.save(c);
    }

    private Characters mapCharacter(CharactersForm characterForm) {
        Characters c = new Characters();
        c.setNome(characterForm.getNome());
        c.setCognome(characterForm.getCognome());
        c.setOpera(characterForm.getOpera());
        c.setCategoria(characterForm.getCategoria());
        c.setCompleanno(characterForm.getCompleanno());

        // Gestione upload immagine
        MultipartFile file = characterForm.getImmagine();
        if (file != null && !file.isEmpty()) {
            String nomeFile = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path percorso = Paths.get(UPLOAD_DIR + nomeFile);
            try {
                Files.createDirectories(percorso.getParent()); // crea la cartella se non esiste
                Files.write(percorso, file.getBytes());         // scrive il file su disco
                c.setImmagine("/uploads/" + nomeFile);         // salva il path nel DB
            } catch (IOException e) {
                throw new RuntimeException("Errore nel salvataggio dell'immagine", e);
            }
        }

        return c;
    }

    public List<Characters> findAll() {
        return animeCharactersRepository.findAll();
    }

    public Optional<Characters> get(UUID id) {
        return animeCharactersRepository.findById(id);
    }
}