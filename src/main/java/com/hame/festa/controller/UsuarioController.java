package com.hame.festa.controller;

import com.hame.festa.model.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @RequestMapping("")
    public String usuarios(Usuario usuario) {
        System.out.println("Usuario: " + usuario.getNome());
        return "usuarios";
    }
}
