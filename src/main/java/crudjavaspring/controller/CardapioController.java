package crudjavaspring.controller;


import com.itextpdf.text.DocumentException;
import crudjavaspring.model.Cardapio;
import crudjavaspring.service.CardapioRelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import crudjavaspring.repository.CardapioRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/cardapio"})
public class CardapioController {

    @Autowired
    CardapioRepository cr;

    List<Cardapio> listaCardapio;

    @GetMapping("")
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
        return "/cardapio";
    }
        @GetMapping("/exclui")
        public String excluiPrato(@RequestParam("id") Integer id){
            cr.deleteById(id);

            return  "redirect:/cardapio";
        }
        @PostMapping("/salvar")
        public String salvaPrato(@ModelAttribute("cardapio") Cardapio cardapio){
            cr.save(cardapio);
            return "redirect:/cardapio";
        }

        @GetMapping("/relatorio")
        public ResponseEntity<byte[]> exportPdf() throws IOException, DocumentException{
        listaCardapio = cr.findAll();
            ByteArrayOutputStream pdfStream = CardapioRelatorioService.cardapio(listaCardapio);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename = cardapio.pdf");
            headers.setContentLength(pdfStream.size());
            return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
        }

}
