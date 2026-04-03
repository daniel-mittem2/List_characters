package it.mat.listcharacters.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mat.listcharacters.domain.Characters;
import it.mat.listcharacters.domain.CharactersForm;
import it.mat.listcharacters.repositories.AnimeCharactersRepository;

@Service
public class AnimeCharactersService {

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
        c.setImmagine(characterForm.getImmagine());
        return c;
    }
    
    public List<Characters> findAll() {
        return animeCharactersRepository.findAll();
    }
}
