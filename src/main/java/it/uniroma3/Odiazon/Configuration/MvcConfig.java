package it.uniroma3.Odiazon.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Questo metodo mappa le richieste che iniziano con /uploads/**
        // a file presenti nella cartella 'uploads' nella root del progetto.
        // Usiamo toUri() per creare un percorso compatibile con tutti i sistemi operativi.
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir.toUri().toString());
    }
}
