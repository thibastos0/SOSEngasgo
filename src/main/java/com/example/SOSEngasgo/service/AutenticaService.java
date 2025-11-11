package com.example.SOSEngasgo.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOSEngasgo.model.Usuario;
import com.example.SOSEngasgo.repository.UsuarioRepository;

@Service
public class AutenticaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> autenticar(String email, String senha) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getContatoUsuario() != null)
                .filter(u -> Objects.equals(email, u.getContatoUsuario().getEmail()))
                .filter(u -> Objects.equals(senha, u.getSenha()))
                .findFirst();
    }

}
