package com.example.SOSEngasgo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SOSEngasgo.model.Usuario;
import com.example.SOSEngasgo.repository.UsuarioRepository;
import com.example.SOSEngasgo.security.UsuarioDetails;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findByContatoUsuarioEmail(username);

        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + username);
        }

        return new UsuarioDetails((Usuario)usuarioOpt.get());
    }

}
