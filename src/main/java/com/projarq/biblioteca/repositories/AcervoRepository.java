package com.projarq.biblioteca.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.projarq.biblioteca.entities.Livro;
import com.projarq.biblioteca.mappers.LivroRowMapper;

@Repository
public class AcervoRepository implements IAcervoRepository {
  
  private final JdbcTemplate jdbcTemplate;
  private final LivroRowMapper livroRowMapper;

  @Autowired
  public AcervoRepository(JdbcTemplate jdbcTemplate, LivroRowMapper livroRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.livroRowMapper = livroRowMapper;
  }

  @Override
  public List<Livro> getAll() {
    return jdbcTemplate.query("SELECT * FROM livros", livroRowMapper);
  }

  @Override
  public Livro getById(Long id) {
    try {
      return jdbcTemplate.queryForObject("SELECT * FROM livros WHERE id = ?", livroRowMapper, id);
    } catch (org.springframework.dao.EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public boolean save(Livro livro) {
    int rowsAffected = jdbcTemplate.update("INSERT INTO livros (titulo, autor, ano) VALUES (?, ?, ?)", 
        livro.getTitulo(), livro.getAutor(), livro.getAno());
    return rowsAffected == 1;
  }

  @Override
  public List<Livro> getByAutor(String autor) {
    return jdbcTemplate.query("SELECT * FROM livros WHERE LOWER(autor) LIKE LOWER(?)", 
        livroRowMapper, "%" + autor + "%");
  }

  @Override
  public List<Livro> getByAno(int ano) {
    return jdbcTemplate.query("SELECT * FROM livros WHERE ano = ?", 
        livroRowMapper, ano);
  }

  @Override
  public boolean deleteById(Long id) {
    int rowsAffected = jdbcTemplate.update("DELETE FROM livros WHERE id = ?", id);
    return rowsAffected == 1;
  }
}
