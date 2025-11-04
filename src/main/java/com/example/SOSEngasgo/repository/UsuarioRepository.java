package com.example.SOSEngasgo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SOSEngasgo.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

    
}