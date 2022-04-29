package com.hame.festa.controller;

import com.hame.festa.model.Usuario;
import com.hame.festa.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @RequestMapping("")
    public String usuarios(Usuario usuario) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            
            usuario = usuarioRepository.findByLogin(username);
        }
        System.out.println("Usuario: " + usuario.getSenha());
        return "usuarios";
    }
}
