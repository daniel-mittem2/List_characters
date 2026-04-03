package it.mat.listcharacters.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mappa le richieste /uploads/** sulla cartella fisica uploads/ nella root del progetto
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}