package com.example.SOSEngasgo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="usuario")
public class Usuario {

    @Id
    private String id;

    private String nome;
    private String tipo;
    private String faixaEtariaAtendimento;
    private String escolaId;
    private String perfilAcesso;
    private boolean capacitadoLeiLucas;

    private ContatoUsuario contatoUsuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFaixaEtariaAtendimento() {
        return faixaEtariaAtendimento;
    }

    public void setFaixaEtariaAtendimento(String faixaEtariaAtendimento) {
        this.faixaEtariaAtendimento = faixaEtariaAtendimento;
    }

    public String getEscolaId() {
        return escolaId;
    }

    public void setEscolaId(String escolaId) {
        this.escolaId = escolaId;
    }

    public String getPerfilAcesso() {
        return perfilAcesso;
    }

    public void setPerfilAcesso(String perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
    }

    public boolean isCapacitadoLeiLucas() {
        return capacitadoLeiLucas;
    }

    public void setCapacitadoLeiLucas(boolean capacitadoLeiLucas) {
        this.capacitadoLeiLucas = capacitadoLeiLucas;
    }

    public ContatoUsuario getContatoUsuario() {
        return contatoUsuario;
    }

    public void setContatoUsuario(ContatoUsuario contatoUsuario) {
        this.contatoUsuario = contatoUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    

}
