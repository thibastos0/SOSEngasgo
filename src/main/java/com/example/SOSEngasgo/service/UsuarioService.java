package com.example.SOSEngasgo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOSEngasgo.model.Usuario;
import com.example.SOSEngasgo.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(String id){
        return usuarioRepository.findById(id);
        
    }

    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);

    }

    public Usuario atualizar(String id, Usuario usuarioAtualizado){
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()){
            
            Usuario usuario = usuarioExistente.get();

            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setTipo(usuarioAtualizado.getTipo());
            usuario.setFaixaEtariaAtendimento(usuarioAtualizado.getFaixaEtariaAtendimento());
            usuario.setEscolaId(usuarioAtualizado.getEscolaId());
            usuario.setPerfilAcesso(usuarioAtualizado.getPerfilAcesso());
            usuario.setCapacitadoLeiLucas(usuarioAtualizado.isCapacitadoLeiLucas());

            usuario.setContatoUsuario(usuarioAtualizado.getContatoUsuario());
            
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuaŕio não encontrado com ID: " + id);
        }
    }

    public void deletar(String id){
        usuarioRepository.deleteById(id);
    }

}
