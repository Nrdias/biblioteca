package com.projarq.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projarq.biblioteca.entities.Livro;
import com.projarq.biblioteca.repositories.IAcervoRepository;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    
    private final IAcervoRepository acervoRepository;

    @Autowired
    public LivroController(IAcervoRepository acervoRepository) {
        this.acervoRepository = acervoRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Livro>> getLivros(
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) Integer ano) {
        try {
            List<Livro> livros;
            
            if (autor != null && !autor.trim().isEmpty()) {
                livros = acervoRepository.getByAutor(autor);
            } else if (ano != null && ano > 0) {
                livros = acervoRepository.getByAno(ano);
            } else {
                livros = acervoRepository.getAll();
            }
            
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id) {
        try {
            Livro livro = acervoRepository.getById(id);
            if (livro != null) {
                return ResponseEntity.ok(livro);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    public ResponseEntity<String> createLivro(@RequestBody Livro livro) {
        try {
            if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Título é obrigatório");
            }
            
            if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Autor é obrigatório");
            }
            
            if (livro.getAno() <= 0) {
                return ResponseEntity.badRequest().body("Ano deve ser um número positivo");
            }

            boolean success = acervoRepository.save(livro);
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Livro criado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Falha ao criar o livro");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLivro(@PathVariable Long id) {
        try {
            boolean success = acervoRepository.deleteById(id);
            if (success) {
                return ResponseEntity.ok("Livro excluído com sucesso");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor");
        }
    }
}
