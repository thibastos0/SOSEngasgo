package com.example.SOSEngasgo.model;

public class ContatoUsuario {

    private String telefone;
    private String email;

    public ContatoUsuario() {}

    public ContatoUsuario(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
