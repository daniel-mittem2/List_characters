package it.mat.listcharacters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import it.mat.listcharacters.domain.Characters;
import it.mat.listcharacters.domain.CharactersForm;
import it.mat.listcharacters.services.AnimeCharactersService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/new")
    public ModelAndView newAnimeCharacters(){
        return new ModelAndView("characters-form").addObject("characterForm", new CharactersForm());
    }

    @PostMapping("/new")
    public ModelAndView handleNewAnimeCharacters(@ModelAttribute CharactersForm charactersForm) {
        
        Characters c = animeCharactersService.save(charactersForm);

        // Usa la stessa vista per mostrare il form dopo il salvataggio o rimanda alla lista.
        return new ModelAndView("characters-form").addObject("characterForm", new CharactersForm());
    }
    
    
}
