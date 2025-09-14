package com.projarq.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projarq.biblioteca.entities.Usuario;
import com.projarq.biblioteca.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username é obrigatório");
            }
            
            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password é obrigatório");
            }
            
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email é obrigatório");
            }

            if (usuario.getPerfil() != null && 
                !usuario.getPerfil().equals("USER") && 
                !usuario.getPerfil().equals("BIBLIOTECARIO")) {
                return ResponseEntity.badRequest().body("Perfil deve ser USER ou BIBLIOTECARIO");
            }

            boolean success = usuarioService.cadastrarUsuario(usuario);
            
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuário cadastrado com sucesso! Perfil: " + 
                          (usuario.getPerfil() != null ? usuario.getPerfil() : "USER"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Usuário já existe ou erro ao cadastrar");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor");
        }
    }
}
