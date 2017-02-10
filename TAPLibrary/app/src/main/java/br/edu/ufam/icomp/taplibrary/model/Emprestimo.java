package br.edu.ufam.icomp.taplibrary.model;

import android.os.CpuUsageInfo;

import java.io.Serializable;

/**
 * Created by gabri on 09/02/2017.
 */

public class Emprestimo implements Serializable {
    private int id;
    private Titulo titulo;
    private Usuario usuario;

    private int foiDevolvido;

    public Emprestimo() {
        this.id = -1;
    }

    public Emprestimo(int id, Titulo titulo, Usuario usuario, int foiDevolvido) {
        this();
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.foiDevolvido = foiDevolvido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getFoiDevolvido() {
        return foiDevolvido;
    }

    public void setFoiDevolvido(int foiDevolvido) {
        this.foiDevolvido = foiDevolvido;
    }
}
