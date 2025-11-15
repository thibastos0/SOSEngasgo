package com.example.SOSEngasgo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SOSEngasgo.model.Usuario;
import com.example.SOSEngasgo.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(String id){
        return usuarioRepository.findById(id);
        
    }

    public Usuario salvar(Usuario usuario){
        // Validar que senha não é nula/vazia ao criar novo usuário
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória para novos usuários");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(String id, Usuario usuarioAtualizado){
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (!usuarioExistente.isPresent()) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }

        Usuario usuario = usuarioExistente.get();

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setTipo(usuarioAtualizado.getTipo());
        usuario.setFaixaEtariaAtendimento(usuarioAtualizado.getFaixaEtariaAtendimento());
        usuario.setEscolaId(usuarioAtualizado.getEscolaId());
        usuario.setPerfilAcesso(usuarioAtualizado.getPerfilAcesso());
        usuario.setCapacitadoLeiLucas(usuarioAtualizado.isCapacitadoLeiLucas());
        usuario.setContatoUsuario(usuarioAtualizado.getContatoUsuario());

        // Atualizar senha apenas se fornecida (não nula e não vazia)
        if(usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()){
            usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        }
            
        return usuarioRepository.save(usuario);
        
    }

    public void deletar(String id){
        usuarioRepository.deleteById(id);
    }

}
