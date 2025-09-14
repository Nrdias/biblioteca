package com.projarq.biblioteca.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.projarq.biblioteca.entities.Livro;

@Component
public class LivroRowMapper implements RowMapper<Livro> {
    
    @Override
    public Livro mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Livro(
            rs.getLong("id"),
            rs.getString("titulo"),
            rs.getString("autor"),
            rs.getInt("ano")
        );
    }
}
