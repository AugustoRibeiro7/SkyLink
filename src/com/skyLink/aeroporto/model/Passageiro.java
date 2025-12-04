package com.skyLink.aeroporto.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Passageiro {
    private int id;
    private String nome;
    private LocalDate nascimento;
    private String documento;
    private String login;
    private String senha;
    private Perfil perfil; // Campo Perfil
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;


    public Passageiro(int id, String nome, LocalDate nascimento, String documento,
                      String login, String senha, Perfil perfil,
                      LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.documento = documento;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil; // Agora isso funciona porque o parâmetro existe acima
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    // --- CONSTRUTOR VAZIO ---
    // Define o perfil padrão como CLIENTE para evitar erros em cadastros novos
    public Passageiro() {
        this.perfil = Perfil.CLIENTE;
    }

    // --- GETTERS E SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public String toString() {
        return "Passageiro {" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nascimento=" + nascimento +
                ", documento='" + documento + '\'' +
                ", login='" + login + '\'' +
                ", perfil=" + perfil +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}