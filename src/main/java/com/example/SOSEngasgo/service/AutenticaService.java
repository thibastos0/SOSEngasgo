package com.example.SOSEngasgo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOSEngasgo.model.Usuario;
import com.example.SOSEngasgo.repository.UsuarioRepository;

@Service
public class AutenticaService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario autenticar(String email, String senha){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .filter(u -> u.getContatoUsuario() != null)
                .filter(u -> email.equals(u.getContatoUsuario().getEmail()))
                .filter(u -> senha != null && senha.equals(u.getSenha()))
                .findFirst()
                .orElse(null);
    }

}
