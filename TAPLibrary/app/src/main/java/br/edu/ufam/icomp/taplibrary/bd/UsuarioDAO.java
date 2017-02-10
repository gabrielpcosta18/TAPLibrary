package br.edu.ufam.icomp.taplibrary.bd;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

/**
 * Created by gabri on 09/02/2017.
 */

public class UsuarioDAO {
    private SQLiteDatabase bancoDeDados;
    private static final String NOME_TABELA = "Usuario";

    public UsuarioDAO(Context contexto) {
        this.bancoDeDados = (new BancoDeDados(contexto)).getWritableDatabase();
    }

    private Usuario usuarioDoCursor(Cursor cursor) {
        return new Usuario(cursor.getInt(0),
                cursor.getString(4),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(1));
    }

    public boolean validarLogin(Usuario usuario) {
        String query = "SELECT COUNT(*) FROM " + NOME_TABELA + " WHERE login = '" +
                usuario.getLogin() + "' and senha = '" + usuario.getSenha() + "'";
        Log.d("SQL", query);
        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        cursor.moveToNext();
        return cursor.getInt(0) == 1;
    }

    public Usuario usuarioPorId(int id) {
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE _id = " + id;

        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Usuario usuario = usuarioDoCursor(cursor);

            cursor.close();
            return usuario;
        }

        return null;
    }

    public Cursor todosUsuariosCursor() {
        return this.bancoDeDados.rawQuery("SELECT *" +
                " FROM " + NOME_TABELA + " ORDER BY nome", null);
    }

    public List todosUsuariosLista() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = todosUsuariosCursor();

        while(cursor.moveToNext()) {
            usuarios.add(usuarioDoCursor(cursor));
        }

        return usuarios;
    }

    public boolean adicionarUsuario(Usuario usuario) {
        try {
            String sqlCmd = "INSERT INTO " + NOME_TABELA+
                    "(login, senha, nome, admin)" +
                    " VALUES ('" + usuario.getLogin() +
                    "', '" + usuario.getSenha() +
                    "', '" + usuario.getNome() +
                    "', '" + Integer.toString(usuario.getAdmin())
                    + "')";

            Log.d("SQL", sqlCmd);

            this.bancoDeDados.execSQL(sqlCmd);
            return true;
        }
        catch (SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }

    public boolean deletarUsuario(Usuario usuario) {
        try {
            String sqlCmd = "DELETE FROM " + NOME_TABELA +
                    " WHERE _id = " + Integer.toString(usuario.getId());

            Log.d("SQL", sqlCmd);

            this.bancoDeDados.execSQL(sqlCmd);
            return true;
        }
        catch(SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }

    public boolean atualizarUsuario(Usuario usuario) {
        try {
            String sqlCmd = "UPDATE " + NOME_TABELA +
                    " SET nome = '" + usuario.getNome() +
                    "', login = '"  + usuario.getLogin() +
                    "', senha = '" + usuario.getSenha() +
                    "', admin = " + Integer.toString(usuario.getAdmin()) +
                    " WHERE _id = " + usuario.getId();

            Log.d("SQL", sqlCmd);

            this.bancoDeDados.execSQL(sqlCmd);
            return true;
        }
        catch(SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }
}
