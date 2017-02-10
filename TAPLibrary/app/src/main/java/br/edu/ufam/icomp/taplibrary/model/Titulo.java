package br.edu.ufam.icomp.taplibrary.model;

import java.io.Serializable;

/**
 * Created by gabri on 09/02/2017.
 */

public class Titulo implements Serializable {
    private int id;
    private String titulo;
    private String identUnique;
    private String editora;
    private String autor;
    private String descricao;
    private String tipo;
    private int numeroExemplares;
    private int diasEmprestimo;
    private int anoPublicacao;

    public Titulo() {
        this.id = -1;
    }

    public Titulo(int id, String titulo, String identUnique, String editora,
                  String autor, String descricao, String tipo, int numeroExemplares,
                  int diasEmprestimo, int anoPublicacao) {
        this();
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.identUnique = identUnique;
        this.editora = editora;
        this.autor = autor;
        this.descricao = descricao;
        this.numeroExemplares = numeroExemplares;
        this.diasEmprestimo = diasEmprestimo;
        this.anoPublicacao = anoPublicacao;
    }

    public String toString() {
        return this.titulo + "/" + this.autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdentUnique() {
        return identUnique;
    }

    public void setIdentUnique(String identUnique) {
        this.identUnique = identUnique;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNumeroExemplares() {
        return numeroExemplares;
    }

    public void setNumeroExemplares(int numeroExemplares) {
        this.numeroExemplares = numeroExemplares;
    }

    public int getDiasEmprestimo() {
        return diasEmprestimo;
    }

    public void setDiasEmprestimo(int diasEmprestimo) {
        this.diasEmprestimo = diasEmprestimo;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Titulo.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Titulo outro = (Titulo) obj;

        return this.id == outro.id;
    }
}
