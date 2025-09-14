package com.projarq.biblioteca.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projarq.biblioteca.entities.Usuario;
import com.projarq.biblioteca.repositories.IUsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        
        if ("BIBLIOTECARIO".equals(usuario.getPerfil())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_BIBLIOTECARIO"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            usuario.isAtivo(),
            true,
            true,
            true,
            authorities
        );
    }
}
