package com.projarq.biblioteca.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WelcomeController {

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok(
            "Bem-vindo ao Sistema de Biblioteca! 📚\n\n" +
            "🔧 Configuração: Sistema configurado via variáveis de ambiente (.env)\n\n" +
            "📋 Endpoints disponíveis:\n" +
            "• GET /api/welcome - Mensagem de boas vindas (público)\n" +
            "• POST /api/usuarios/cadastrar - Cadastrar usuário (público)\n" +
            "• GET /api/livros - Listar todos os livros (autenticado)\n" +
            "• GET /api/livros/{id} - Buscar livro por ID (autenticado)\n" +
            "• GET /api/livros/autor/{autor} - Buscar livros por autor (autenticado)\n" +
            "• GET /api/livros/ano/{ano} - Buscar livros por ano (autenticado)\n" +
            "• POST /api/livros/cadastrar - Cadastrar novo livro (bibliotecário)\n\n" +
            "🔐 Autenticação: Use HTTP Basic Auth para endpoints protegidos\n" +
            "👥 Perfis: USER (usuário comum) | BIBLIOTECARIO (bibliotecário)\n\n" +
            "💡 Dica: Crie um arquivo .env na raiz do projeto para configurar o banco de dados"
        );
    }
}
