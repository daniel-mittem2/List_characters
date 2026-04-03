package it.mat.listcharacters.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharactersForm {

    public String nome;
    public String cognome;
    public String opera;
    public String categoria;
    public String compleanno;  
    public String immagine; 
    
}
