package com.hame.festa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import com.hame.festa.model.Convidado;
import com.hame.festa.repository.ConvidadosRepository;


@Controller
@RequestMapping("/")
public class ConvidadosController {
	
	@Autowired
	private ConvidadosRepository convidados;

	@GetMapping
	public String home() {
		return "home.html";
	}
	
	@GetMapping("/convidados/cadastrar")
	public ModelAndView cadastrar() {
		
		ModelAndView mv = new ModelAndView("CadastroConvidados");
		mv.addObject(new Convidado());
		
		return mv;
	}
	
	@GetMapping("/convidados/listar")
	public ModelAndView listar() {
		
		ModelAndView mv = new ModelAndView("ListaConvidados");
		mv.addObject("convidados", convidados.findAll());
		
		return mv;
	}
	
	@PostMapping("/convidados/salvar")
	public String salvar(Convidado convidado) {
		this.convidados.save(convidado);
		return "redirect:/convidados/listar";
	}
	
	@GetMapping("/convidados/editar/{id}")
	public ModelAndView preEditar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("CadastroConvidados");
		mv.addObject("convidado", convidados.findById(id));
		return mv;
	}
	
	@PostMapping("/convidados/editar/{id}")
	public String editar(Convidado convidado) {
		convidados.save(convidado);
		return "redirect:/convidados/listar";
	}

	@GetMapping("/convidados/excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
		Convidado convidado = convidados.getById(id);
		convidados.delete(convidado);
		return "redirect:/convidados/listar";
	}
	
}
