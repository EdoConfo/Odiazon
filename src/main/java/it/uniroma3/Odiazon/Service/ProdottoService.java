package it.uniroma3.Odiazon.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.Odiazon.Model.Prodotto;
import it.uniroma3.Odiazon.Repository.ProdottoRepository;

@Service

public class ProdottoService {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    public Iterable<Prodotto> findAll() {
        return prodottoRepository.findAll();
    }

    public Prodotto findById(Long id) {
        return prodottoRepository.findById(id).orElse(null);
    }

    public Prodotto save(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }
    
    public void deleteById(Long id) {
        Prodotto prodotto = findById(id);
        if (prodotto != null) {
            String immagineUrl = prodotto.getImmagineUrl();
            if (immagineUrl != null && !immagineUrl.isEmpty() && immagineUrl.startsWith("/uploads/")) {
                try {
                    // Costruiamo il percorso assoluto al file partendo dalla root del progetto
                    Path imagePath = Paths.get(System.getProperty("user.dir"), immagineUrl.substring(1));
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    // Gestisci l'eccezione, ad esempio loggando l'errore
                    System.err.println("Errore durante l'eliminazione dell'immagine: " + e.getMessage());
                }
            }
            prodottoRepository.deleteById(id);
        }
    }
}