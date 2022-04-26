package com.hame.festa.utility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.hame.festa.model.Convidado;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class CabecalhoPdf extends PdfPageEventHelper {
    private PdfTemplate template;
    private Image image;

    public CabecalhoPdf(Optional<Convidado> convidado) {
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        template = writer.getDirectContent().createTemplate(36, 16);
        try {
            image = Image.getInstance(template);
            image.setRole(PdfName.ARTIFACT);

        } catch (DocumentException de) {

            throw new ExceptionConverter(de);
        }
    }

    public void onEndPage(PdfWriter writer, Document document) {
        try {

            addHeader(writer);

        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }

        addFooter(writer);
    }

    private void addHeader(PdfWriter writer) throws DocumentException, MalformedURLException, IOException {

        PdfPTable header = new PdfPTable(2);

        // formatação
        header.setWidths(new float[] { 0.1f , 0.9f });
        header.setTotalWidth(250);
        header.setLockedWidth(true);
        // header.getDefaultCell().setFixedHeight(40);
    

        // adiciona logotipo
        // Image logo = Image.getInstance(CabecalhoPdf.class.getResource("/static/logo_estado.jpeg"));

        PdfPCell cell = new PdfPCell();
        // cell.addElement(logo);
        cell.setBorder(Rectangle.BOTTOM);
        header.addCell(cell);

        // adiciona cabeçalho
        PdfPCell text = new PdfPCell();
        text.setPaddingBottom(5);
        text.setPaddingLeft(2);
        text.setBorder(Rectangle.BOTTOM);
        text.setPaddingBottom(5);
        text.addElement(new Phrase("HAME SOFTWARES E ANALISE DE DADOS", new Font(Font.FontFamily.HELVETICA, 10)));
        text.addElement(new Phrase("   EVENTO DE TECNOLOGIA DA INFORMAÇÃO", new Font(Font.FontFamily.HELVETICA, 9)));
        header.addCell(text);

        // write content
        header.writeSelectedRows(0, -1, 180, 820, writer.getDirectContent());
    }

    private void addFooter(PdfWriter writer) {
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setWidths(new int[] { 24, 3, 1 });
            footer.setTotalWidth(480);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // Inserção da data da impressão do bo
            SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String result = out.format(new Date());

            footer.addCell(new Phrase("Lista de Convidados - Impresso em: " + result,
                    new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC)));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Página %d de ", writer.getPageNumber()),
                    new Font(Font.FontFamily.HELVETICA, 8)));

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(image);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 80, 50, canvas);
            canvas.endMarkedContentSequence();

        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {

        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;

        ColumnText.showTextAligned(template, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }
}
