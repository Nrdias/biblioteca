package com.projarq.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projarq.biblioteca.entities.Usuario;
import com.projarq.biblioteca.repositories.IUsuarioRepository;

@Service
public class UsuarioService {
    
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.getAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.getById(id);
    }

    public Optional<Usuario> getUsuarioByUsername(String username) {
        return usuarioRepository.getByUsername(username);
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        try {
            if (usuarioRepository.getByUsername(usuario.getUsername()).isPresent()) {
                return false;
            }
            
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            
            if (usuario.getPerfil() == null || usuario.getPerfil().isEmpty()) {
                usuario.setPerfil("USER");
            }
            
            usuario.setAtivo(true);
            
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean atualizarUsuario(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.update(usuario);
    }

    public boolean deletarUsuario(Long id) {
        return usuarioRepository.deleteById(id);
    }
}
