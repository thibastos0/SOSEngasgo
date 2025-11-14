package com.example.SOSEngasgo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SOSEngasgo.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	Optional<Usuario> findByContatoUsuarioEmail(String email); //implementado automaticamente pelo Spring Data MongoDB
}