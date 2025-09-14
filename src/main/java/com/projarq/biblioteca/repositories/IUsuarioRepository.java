package com.projarq.biblioteca.repositories;

import java.util.List;
import java.util.Optional;

import com.projarq.biblioteca.entities.Usuario;

public interface IUsuarioRepository {
    List<Usuario> getAll();
    Optional<Usuario> getById(Long id);
    Optional<Usuario> getByUsername(String username);
    boolean save(Usuario usuario);
    boolean update(Usuario usuario);
    boolean deleteById(Long id);
}
