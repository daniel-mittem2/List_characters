package it.mat.listcharacters.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;
import it.mat.listcharacters.domain.Characters;
import it.mat.listcharacters.domain.CharactersForm;
import it.mat.listcharacters.services.AnimeCharactersService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class AnimeCharactersController {

    @Autowired
    private AnimeCharactersService animeCharactersService;

    @GetMapping("/")
    public ModelAndView showAnimeCharactersList() {
    return new ModelAndView("characters-list")
        .addObject("characters", animeCharactersService.findAll());
    }

    @GetMapping("/search")
    public ModelAndView searchCharacters(@RequestParam(value = "q", required = false, defaultValue = "") String searchTerm) {
        return new ModelAndView("characters-list")
            .addObject("characters", animeCharactersService.searchByNomeOrCognome(searchTerm));
    }

    @GetMapping("/new")
    public ModelAndView newAnimeCharacters(){
        return new ModelAndView("characters-form").addObject("characterForm", new CharactersForm());
    }

    @PostMapping("/new")
    public ModelAndView handleNewAnimeCharacters(@ModelAttribute @Valid CharactersForm charactersForm, BindingResult br, RedirectAttributes attr) {

        if(br.hasErrors()) {
            return new ModelAndView("characters-form")
                .addObject("characterForm", charactersForm)
                .addObject("org.springframework.validation.BindingResult.characterForm", br);
        }
        attr.addFlashAttribute("newCharacters", true);
        
        Characters c = animeCharactersService.save(charactersForm);
        // Usa la stessa vista per mostrare il form dopo il salvataggio o rimanda alla lista.
        return new ModelAndView("redirect:/characters?id=" + c.getId());
    }

    @GetMapping(path = "characters", params = "id")
    public ModelAndView showContact(@RequestParam("id") UUID characterId) {

        Optional<Characters> opCharacter = animeCharactersService.get(characterId);

        // controllo se il dato è presente
        if (opCharacter.isPresent()) {
            return new ModelAndView("characters-detail")
                .addObject("characters", opCharacter.get());
        }
        else 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personaggio non trovato");
    }

    @GetMapping("character/edit/{id}")
    public ModelAndView editCharacter(@PathVariable("id") UUID characterId) {
        Optional<Characters> opCharacter = animeCharactersService.get(characterId);

        if (opCharacter.isPresent()) {
            CharactersForm form = animeCharactersService.convertToForm(opCharacter.get());
            
            return new ModelAndView("characters-form")
                .addObject("characterForm", form)
                .addObject("isEdit", true);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personaggio non trovato");
        }
    }

    @PostMapping("character/edit/{id}")
    public ModelAndView handleEditAnimeCharacters(@PathVariable("id") UUID characterId,
                                                   @ModelAttribute @Valid CharactersForm charactersForm,
                                                   BindingResult br,
                                                   RedirectAttributes attr) {

        if(br.hasErrors()) {
            return new ModelAndView("characters-form")
                .addObject("characterForm", charactersForm)
                .addObject("isEdit", true)
                .addObject("org.springframework.validation.BindingResult.characterForm", br);
        }

        charactersForm.setId(characterId);
        attr.addFlashAttribute("newCharacters", true);
        
        animeCharactersService.update(charactersForm);
        return new ModelAndView("redirect:/characters?id=" + characterId);
    }

    @GetMapping("character/delete/{id}")
    public ModelAndView deleteCharacter(@PathVariable("id") UUID characterId, RedirectAttributes attr) {
            animeCharactersService.deletebyId(characterId);

            attr.addFlashAttribute("deleteCharacter", true);
            return new ModelAndView("redirect:/");
    }

    @GetMapping("/deleteAll")
    public ModelAndView deleteAllCharacters() {
        animeCharactersService.deleteAll();
        return new ModelAndView("redirect:/");
    }
}