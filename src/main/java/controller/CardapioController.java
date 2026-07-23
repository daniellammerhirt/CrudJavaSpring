package controller;


import model.Cardapio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.CardapioRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/cardapio"})
public class CardapioController {

    @Autowired
    CardapioRepository cr;

    List<Cardapio> listaCardapio;

    @GetMapping("/")
    public String listarCardapio(Model model) {
        listaCardapio = cr.findAll();
        model.addAttribute("listacardapio", listaCardapio);
        return "paginas/lista-cardapio";
    }

    @GetMapping("/novo")
    public String novoPrato(Model model) {
        Cardapio cardapio = new Cardapio();
        model.addAttribute("cardapio", cardapio);
        return "paginas/form-cardapio";
    }

    @GetMapping("/edita")
    public String editaPrato(@RequestParam("id") Integer id, Model model) {
        Optional<Cardapio> cardapio = cr.findById(id);
        if (cardapio.isPresent()) {
            model.addAttribute("cardapio", cardapio.get());
            return "paginas/form-cardapio";
        }
        return "/cardapio/";
    }
        @GetMapping("/exclui")
        public String excluiPrato(@RequestParam("id") Integer id){
            cr.deleteById(id);

            return  "redirect:/cardapio/";
        }
        @PostMapping("/salvar")
        public String salvaPrato(@ModelAttribute("cardapio") Cardapio cardapio){
            cr.save(cardapio);
            return "redirect:/cardapio/";
        }

}
