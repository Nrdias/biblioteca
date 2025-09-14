package com.projarq.biblioteca.entities;

public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String perfil;
    private boolean ativo;

    public Usuario() {}

    public Usuario(Long id, String username, String password, String email, String perfil, boolean ativo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.perfil = perfil;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", username=" + username + ", email=" + email + 
               ", perfil=" + perfil + ", ativo=" + ativo + "]";
    }
}
