package com.example.SOSEngasgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SOSEngasgo.model.Usuario;
import com.example.SOSEngasgo.service.AutenticaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/autenticacao")
public class LoginController {

    @Autowired
    private AutenticaService autenticaService;

    @PostMapping("")
    public ResponseEntity<String> autenticarUsuario(@RequestBody Usuario usuario) {
        String email = usuario.getContatoUsuario().getEmail();
        String senha = usuario.getSenha();
        if(autenticaService.autenticar(email, senha)!=null){
            return ResponseEntity.ok("Login ok");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login ou senha inválidos!");
        }
    
    }
    
/* 
    @GetMapping("/gestao")
    public String validarLogin(){
        return "dashboard";
    }*/

}
