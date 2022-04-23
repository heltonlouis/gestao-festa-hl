package com.hame.festa.controller;

import java.io.ByteArrayInputStream;
import java.util.Optional;


import com.hame.festa.model.Convidado;
import com.hame.festa.repository.ConvidadosRepository;
import com.hame.festa.service.GerarPdfService;
import com.itextpdf.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GerarPdfController {

    @Autowired
	private ConvidadosRepository convidados;

    @Autowired
    GerarPdfService gerarPdfService;

    @GetMapping(value = "/convidados/report/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> report(@PathVariable("id") Long id) throws DocumentException {

        Optional<Convidado> convidado  = convidados.findById(id);

		ByteArrayInputStream gerarRelatorio = gerarPdfService.gerarRelatorio(convidado);
        
		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Disposition", "inline; filename=GerarBo.pdf");
	
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(gerarRelatorio));
	}

}
