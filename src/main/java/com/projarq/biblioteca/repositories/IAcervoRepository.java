package com.projarq.biblioteca.repositories;

import java.util.List;

import com.projarq.biblioteca.entities.Livro;

public interface IAcervoRepository{
  List<Livro> getAll();
  Livro getById(Long id);
  List<Livro> getByAutor(String autor);
  List<Livro> getByAno(int ano);
  boolean save(Livro livro);
  boolean deleteById(Long id);
}
