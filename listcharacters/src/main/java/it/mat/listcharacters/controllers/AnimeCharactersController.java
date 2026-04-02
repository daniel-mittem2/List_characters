package it.mat.listcharacters.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AnimeCharactersController {

    @GetMapping("/")
    public ModelAndView showAnimeCharactersList() {
        return new ModelAndView("characters-list");
    }
    
}
