package com.projarq.biblioteca.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.biblioteca.entities.Usuario;
import com.projarq.biblioteca.mappers.UsuarioRowMapper;

@Repository
public class UsuarioRepository implements IUsuarioRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioRowMapper usuarioRowMapper;

    @Autowired
    public UsuarioRepository(JdbcTemplate jdbcTemplate, UsuarioRowMapper usuarioRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioRowMapper = usuarioRowMapper;
    }

    @Override
    public List<Usuario> getAll() {
        return jdbcTemplate.query("SELECT * FROM usuarios WHERE ativo = true", usuarioRowMapper);
    }

    @Override
    public Optional<Usuario> getById(Long id) {
        try {
            Usuario usuario = jdbcTemplate.queryForObject(
                "SELECT * FROM usuarios WHERE id = ? AND ativo = true", 
                usuarioRowMapper, id);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> getByUsername(String username) {
        try {
            Usuario usuario = jdbcTemplate.queryForObject(
                "SELECT * FROM usuarios WHERE username = ? AND ativo = true", 
                usuarioRowMapper, username);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean save(Usuario usuario) {
        try {
            int rowsAffected = jdbcTemplate.update(
                "INSERT INTO usuarios (username, password, email, perfil, ativo) VALUES (?, ?, ?, ?, ?)", 
                usuario.getUsername(), 
                usuario.getPassword(), 
                usuario.getEmail(), 
                usuario.getPerfil(), 
                usuario.isAtivo());
            return rowsAffected == 1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Usuario usuario) {
        try {
            int rowsAffected = jdbcTemplate.update(
                "UPDATE usuarios SET username = ?, password = ?, email = ?, perfil = ?, ativo = ? WHERE id = ?", 
                usuario.getUsername(), 
                usuario.getPassword(), 
                usuario.getEmail(), 
                usuario.getPerfil(), 
                usuario.isAtivo(), 
                usuario.getId());
            return rowsAffected == 1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            int rowsAffected = jdbcTemplate.update("UPDATE usuarios SET ativo = false WHERE id = ?", id);
            return rowsAffected == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
