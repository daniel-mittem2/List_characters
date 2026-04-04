package it.mat.listcharacters.domain;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharactersForm {

    @NotEmpty
    @Size(max = 50)
    public String nome;

    @NotEmpty
    @Size(max = 50)
    public String cognome;

    @NotEmpty
    @Size(max = 100)
    public String opera;

    @NotEmpty
    @Size(max = 100)
    public String categoria;

    @NotEmpty
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "{gg/mm/yyyy}")
    public String compleanno;  

    @NotNull

    public MultipartFile immagine; 
    
}
