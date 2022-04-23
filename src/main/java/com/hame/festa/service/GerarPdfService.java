package com.hame.festa.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import com.hame.festa.model.Convidado;
import com.hame.festa.utility.CabecalhoPdf;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

@Service
public class GerarPdfService {

    public ByteArrayInputStream gerarRelatorio(Optional<Convidado> convidado) throws DocumentException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);

        Font fLabel = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font fInfo = new Font(Font.FontFamily.HELVETICA, 9);
        Font fHeader = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
        Font fCell = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC);

        try {

            PdfWriter writer = PdfWriter.getInstance(document, out);

            CabecalhoPdf cabecalhoPdf = new CabecalhoPdf(convidado);

            writer.setPageEvent(cabecalhoPdf);

            document.open();

            document.getPageSize();

            adicionaTitle(writer);
            adicionaConvidados(writer);

            document.close();

        } catch (DocumentException de) {

            System.out.println(de);
            document.close();

        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void adicionaTitle(PdfWriter writer) {

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
        Paragraph title = new Paragraph("Lista de Convidados", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, title, 300, 750, 0);
    }

    private void adicionaConvidados(PdfWriter writer) throws DocumentException {

        PdfPTable convidados = new PdfPTable(2);
        
        convidados.setWidths(new int[]{ 20, 14});
        convidados.setTotalWidth(500);

        PdfPCell nome = new PdfPCell();
        nome.setBorder(Rectangle.BOX);
        nome.addElement(new Phrase("Nome do Convidado: "));
        convidados.addCell(nome);

        PdfPCell acompanhantes = new PdfPCell();
        acompanhantes.setBorder(Rectangle.BOX);
        acompanhantes.addElement(new Phrase("Número de Acompanhantes: "));
        convidados.addCell(acompanhantes);

        convidados.writeSelectedRows(0, -1, 50, 720, writer.getDirectContent());
    }

}
