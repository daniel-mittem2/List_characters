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

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private AnimeCharactersRepository animeCharactersRepository;

    public Characters save(CharactersForm form) {
        Characters c = new Characters();
        mapFields(form, c);
        return animeCharactersRepository.save(c);
    }

    public Characters update(CharactersForm form) {
        Characters c = animeCharactersRepository.findById(form.getId())
            .orElseThrow(() -> new RuntimeException("Personaggio non trovato"));
        mapFields(form, c);
        return animeCharactersRepository.save(c);
    }

    private void mapFields(CharactersForm form, Characters c) {
        c.setNome(form.getNome());
        c.setCognome(form.getCognome());
        c.setOpera(form.getOpera());
        c.setCategoria(form.getCategoria());
        c.setCompleanno(form.getCompleanno());
        c.setSesso(form.getSesso());
        handleImageUpload(form.getImmagine(), c);
    }

    private void handleImageUpload(MultipartFile file, Characters c) {
        if (file != null && !file.isEmpty()) {
            String nomeFile = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path percorso = Paths.get(UPLOAD_DIR + nomeFile);
            try {
                Files.createDirectories(percorso.getParent());
                Files.write(percorso, file.getBytes());
                c.setImmagine("/uploads/" + nomeFile);
            } catch (IOException e) {
                throw new RuntimeException("Errore nel salvataggio dell'immagine", e);
            }
        }
    }

    public CharactersForm convertToForm(Characters character) {
        CharactersForm form = new CharactersForm();
        form.setId(character.getId());
        form.setNome(character.getNome());
        form.setCognome(character.getCognome());
        form.setOpera(character.getOpera());
        form.setCategoria(character.getCategoria());
        form.setCompleanno(character.getCompleanno());
        form.setImmagineAttuale(character.getImmagine());
        form.setSesso(character.getSesso());
        return form;
    }

    public List<Characters> findAll() {
        return animeCharactersRepository.findAll();
    }

    public Optional<Characters> get(UUID id) {
        return animeCharactersRepository.findById(id);
    }

    public void deleteAll() {
        animeCharactersRepository.deleteAll();
    }

    public void deletebyId(UUID id) {
        animeCharactersRepository.deleteById(id);
    }

    public List<Characters> searchByNomeOrCognome(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return animeCharactersRepository.findAll();
        }
        return animeCharactersRepository.searchByNomeOrCognome(searchTerm.trim());
    }
}