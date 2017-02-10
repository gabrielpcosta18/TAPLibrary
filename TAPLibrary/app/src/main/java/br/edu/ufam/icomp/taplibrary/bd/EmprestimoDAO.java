package br.edu.ufam.icomp.taplibrary.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taplibrary.model.Emprestimo;
import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

/**
 * Created by gabri on 09/02/2017.
 */

public class EmprestimoDAO {
    private SQLiteDatabase bancoDeDados;
    private static final String NOME_TABELA = "Emprestimo";
    private Context contexto;

    public EmprestimoDAO(Context contexto) {
        this.contexto = contexto;
        this.bancoDeDados = (new BancoDeDados(contexto)).getWritableDatabase();
    }

    public Emprestimo emprestimoPeloId(int id) {
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE _id = " + id;

        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Emprestimo emprestimo = new Emprestimo();

            cursor.close();
            return emprestimo;
        }

        return null;
    }

    private Emprestimo emprestimoDoCursor(Cursor cursor) {
        return new Emprestimo(cursor.getInt(0),
                new TituloDAO(this.contexto).tituloPorId(cursor.getInt(1)),
                new UsuarioDAO(this.contexto).usuarioPorId(cursor.getInt(2)),
                cursor.getInt(3));
    }

    public List<Emprestimo> todosEmprestimosLista(boolean filtrar) {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        String query = "SELECT * " +
                " FROM " + NOME_TABELA;

        if(filtrar) query += " WHERE foiDevolvido = 0";

        Cursor cursor = this.bancoDeDados.rawQuery(query, null);

        while(cursor.moveToNext()) {
            emprestimos.add(emprestimoDoCursor(cursor));
        }

        return emprestimos;
    }

    public List<Titulo> titulosPorUsuario(Usuario usuario) {
        ArrayList<Titulo> titulos = new ArrayList<>();

        String query = "SELECT * " +
                " FROM " + NOME_TABELA;

        query += " WHERE usuarioId = " + Integer.toString(usuario.getId());

        Cursor cursor = this.bancoDeDados.rawQuery(query, null);

        while(cursor.moveToNext()) {
            titulos.add(emprestimoDoCursor(cursor).getTitulo());
        }

        return titulos;
    }

    public boolean adicionarEmprestimo(Emprestimo emprestimo) {
        try {
            String sqlCmd = "INSERT INTO " + NOME_TABELA +
                    " (tituloId, usuarioId)" +
                    " VALUES (" + Integer.toString(emprestimo.getTitulo().getId()) + ", " +
                    Integer.toString(emprestimo.getUsuario().getId()) + ")";
            Log.d("SQL", sqlCmd);

            this.bancoDeDados.execSQL(sqlCmd);
            return true;
        }
        catch (SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }

    public boolean atualizarEmprestimo(Emprestimo emprestimo) {
        try {
            String sqlCmd = "UPDATE " + NOME_TABELA +
                    " SET foiDevolvido = " + Integer.toString(emprestimo.getFoiDevolvido()) +
                    " WHERE _id = " + emprestimo.getId();

            Log.d("SQL", sqlCmd);
            this.bancoDeDados.execSQL(sqlCmd);
            return true;
        }
        catch(SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }

    public Pair<Titulo, Integer> tituloMaisLido() {
        String query = "SELECT tituloId, count(tituloId) as Counter FROM " +
                NOME_TABELA + " GROUP BY tituloId ORDER BY Counter DESC LIMIT 1";

        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Titulo titulo = new TituloDAO(contexto).tituloPorId(cursor.getInt(0));

            Pair<Titulo, Integer> value = new Pair<>(titulo, new Integer(cursor.getInt(1)));
            cursor.close();

            return value;
        }
        return null;
    }

    public Pair<Usuario, Integer> usuarioMaisEmprestimo() {
        String query = "SELECT usuarioId, count(usuarioId) as Counter FROM " +
                NOME_TABELA + " GROUP BY usuarioId ORDER BY Counter DESC LIMIT 1";

        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Usuario usuario = new UsuarioDAO(contexto).usuarioPorId(cursor.getInt(0));

            Pair<Usuario, Integer> value = new Pair<>(usuario, new Integer(cursor.getInt(1)));
            cursor.close();

            return value;
        }
        return null;
    }
}
