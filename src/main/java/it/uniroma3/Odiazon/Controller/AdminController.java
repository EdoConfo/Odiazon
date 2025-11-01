package it.uniroma3.Odiazon.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.Odiazon.Model.Prodotto;
import it.uniroma3.Odiazon.Service.ProdottoService;

@Controller
public class AdminController {

    @Autowired
    private ProdottoService prodottoService;

    // Mostra il form per aggiungere un nuovo prodotto
    @GetMapping("/admin/prodotto/nuovo")
    public String showNewProdottoForm(Model model) {
        model.addAttribute("prodotto", new Prodotto());
        return "admin/formNuovoProdotto"; // Nome del nuovo file HTML
    }

    // Processa i dati del form e salva il nuovo prodotto
    @PostMapping("/admin/prodotto/nuovo")
    public String saveNewProdotto(@ModelAttribute Prodotto prodotto, @RequestParam("immagineFile") MultipartFile immagineFile) throws IOException {
        if (!immagineFile.isEmpty()) {
            // Definiamo la directory di upload in modo robusto, partendo dalla root del progetto
            Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads");

            // Crea la directory se non esiste
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Genera un nome file univoco e pulito
            String originalFileName = immagineFile.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String snakeCaseNome = toSnakeCase(prodotto.getNome());
            
            String fileName = snakeCaseNome + fileExtension;

            // Salva il file sul server
            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, immagineFile.getBytes());

            // Imposta il percorso dell'immagine nel prodotto
            prodotto.setImmagineUrl("/uploads/" + fileName);
        }
        // Salva il prodotto e recupera l'istanza con l'ID aggiornato
        Prodotto savedProdotto = prodottoService.save(prodotto);
        return "redirect:/prodotto/" + savedProdotto.getId();
    }

    @PostMapping("/admin/prodotto/delete/{id}")
    public String deleteProdotto(@PathVariable("id") Long id) {
        // Prima di eliminare il prodotto dal DB, recuperiamolo per ottenere l'URL dell'immagine
        Prodotto prodotto = prodottoService.findById(id);
        if (prodotto != null && prodotto.getImmagineUrl() != null && !prodotto.getImmagineUrl().isEmpty()) {
            try {
                // Estraiamo il nome del file dall'URL (es. da "/uploads/nome_file.jpg" a "nome_file.jpg")
                String fileName = Paths.get(prodotto.getImmagineUrl()).getFileName().toString();
                Path filePath = Paths.get(System.getProperty("user.dir"), "uploads", fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Logghiamo l'errore ma non blocchiamo l'operazione di eliminazione dal DB
                System.err.println("Errore durante l'eliminazione del file immagine: " + e.getMessage());
            }
        }

        prodottoService.deleteById(id); // Eliminiamo il prodotto dal database
        return "redirect:/";
    }

    private String toSnakeCase(String text) {
        if (text == null) {
            return "";
        }
        return text.trim().toLowerCase().replaceAll("\s+", "_");
    }
}
