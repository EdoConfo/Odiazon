package it.uniroma3.Odiazon.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.Odiazon.Service.ProdottoService;

@Controller
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("prodotti", prodottoService.findAll());
        return "index";
    }

    @GetMapping("/prodotto/{id}")
    public String getProdotto(@PathVariable("id") Long id, Model model) {
        model.addAttribute("prodotto", prodottoService.findById(id));
        return "prodotto"; // Nome del nuovo file HTML
    }
}
