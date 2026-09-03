package crudjavaspring.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import crudjavaspring.model.Cardapio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class CardapioRelatorioService {
    public static ByteArrayOutputStream cardapio(List<Cardapio> cardapioCompleto) throws DocumentException, MalformedURLException, IOException{
        Document document = new Document();
        Rectangle retangulo = new Rectangle(595.33f, 841.97f);
        document.setPageSize(retangulo);
        document.setMargins(20,20,20,20);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Image imagelogo = Image.getInstance("http://localhost:8080/imagens/info.png");
        Font fonte10 = new Font(Font.FontFamily.HELVETICA,10);
        Font fonte10Bold = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
        Font fonte18Bold = new Font(Font.FontFamily.HELVETICA,18,Font.BOLD);
        PdfPTable tabelaLogo = new PdfPTable(4);
        tabelaLogo.getDefaultCell().setBorder(0);
        tabelaLogo.addCell(imagelogo);
        PdfPCell celula = new PdfPCell(new Phrase("Restaurante Info",fonte18Bold));
        celula.setColspan(3);
        celula.setBorder(0);
        tabelaLogo.addCell(celula);
        document.add(tabelaLogo);
        PdfPTable tabelaProdutos = new PdfPTable(3);
        tabelaProdutos.getDefaultCell().setBorder(0);
        tabelaProdutos.addCell(new Phrase("Prato", fonte10Bold));
        tabelaProdutos.addCell(new Phrase("Descrição", fonte10Bold));
        celula = new PdfPCell(new Phrase("Preço", fonte10Bold));
        celula.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        celula.setBorder(0);
        tabelaProdutos.addCell(celula);
        for (Cardapio cardapio : cardapioCompleto){
            celula = new PdfPCell(new Phrase(cardapio.getPrato(), fonte10));
            celula.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            celula.setBorder(0);
            tabelaProdutos.addCell(celula);
            celula = new PdfPCell(new Phrase(cardapio.getDescricao(), fonte10));
            celula.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            celula.setBorder(0);
            tabelaProdutos.addCell(celula);
            celula = new PdfPCell(new Phrase(String.format("R$%5.2f",cardapio.getPreco()),fonte10));
            celula.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            celula.setBorder(0);
            tabelaProdutos.addCell(celula);
        }
        document.add(tabelaProdutos);
        document.close();
        return outputStream;
    }
}
