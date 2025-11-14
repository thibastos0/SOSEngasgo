package com.example.SOSEngasgo.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.SOSEngasgo.model.Usuario;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;
    
    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        // convertendo o perfil de acesso do usuário em uma autoridade do Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfilAcesso()));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getContatoUsuario().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario getUsuarioEntity() {
        return this.usuario;
    }

}
