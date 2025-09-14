package com.projarq.biblioteca.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.projarq.biblioteca.entities.Usuario;

@Component
public class UsuarioRowMapper implements RowMapper<Usuario> {
    
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Usuario(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("perfil"),
            rs.getBoolean("ativo")
        );
    }
}
