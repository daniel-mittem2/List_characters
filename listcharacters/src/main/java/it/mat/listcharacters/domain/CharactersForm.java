package it.mat.listcharacters.domain;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharactersForm {

    private UUID id;

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

    @NotNull
    public LocalDate compleanno;  

    public MultipartFile immagine; 
    
}
