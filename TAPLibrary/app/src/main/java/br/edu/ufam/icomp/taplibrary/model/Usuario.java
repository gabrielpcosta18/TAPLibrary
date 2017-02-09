package br.edu.ufam.icomp.taplibrary.model;

import java.io.Serializable;

/**
 * Created by gabri on 09/02/2017.
 */

public class Usuario implements Serializable {
    private int id;
    private String nome;
    private String login;
    private String senha;
    private int admin;

    public Usuario() {
        this.id = -1;
    }

    public Usuario(int id, String nome, String login, String senha, int admin) {
        this();
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.admin = admin;
    }

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

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

}
