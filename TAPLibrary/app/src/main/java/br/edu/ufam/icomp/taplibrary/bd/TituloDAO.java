package br.edu.ufam.icomp.taplibrary.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taplibrary.model.Titulo;

/**
 * Created by gabri on 09/02/2017.
 */

public class TituloDAO {
    private SQLiteDatabase bancoDeDados;
    private static final String NOME_TABELA = "Titulo";

    public TituloDAO(Context contexto) {
        this.bancoDeDados = (new BancoDeDados(contexto)).getWritableDatabase();
    }

    private Titulo tituloDoCursor(Cursor cursor) {
        return new Titulo(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7),
                cursor.getInt(8),
                cursor.getInt(9));
    }

    public Titulo tituloPorId(int id) {
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE _id = " + id;

        Cursor cursor = this.bancoDeDados.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Titulo titulo = tituloDoCursor(cursor);

            cursor.close();
            return titulo;
        }

        return null;
    }

    public Cursor todosTitulosCursor() {
        return this.bancoDeDados.rawQuery("SELECT *" +
                " FROM " + NOME_TABELA + " ORDER BY titulo", null);
    }

    public List todosTitulosLista() {
        ArrayList<Titulo> titulos = new ArrayList<>();
        Cursor cursor = todosTitulosCursor();

        while(cursor.moveToNext()) {
            titulos.add(tituloDoCursor(cursor));
        }

        return titulos;
    }

    public boolean adicionarTitulo(Titulo titulo) {
        try {
            String sqlCmd = "INSERT INTO " + NOME_TABELA+
                    "(titulo, identUnique, editora, autor, descricao, tipo, numeroExemplares, diasEmprestimo, anoPublicacao)" +
                    " VALUES ('" + titulo.getTitulo() +
                        "', '" + titulo.getIdentUnique() +
                        "', '" + titulo.getEditora() +
                        "', '" + titulo.getAutor() +
                        "', '" + titulo.getDescricao() +
                        "', '" + titulo.getTipo() +
                        "', '" + Integer.toString(titulo.getNumeroExemplares()) +
                        "', '" + Integer.toString(titulo.getDiasEmprestimo()) +
                        "', '" + Integer.toString(titulo.getAnoPublicacao())

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

    public boolean atualizarTitulo(Titulo titulo) {
        try {
            /*String sqlCmd = "UPDATE " + NOME_TABELA +
                    " SET name = '" + customer.getName() + "' "  +
                    " WHERE _id = " + customer.getId();

            Log.d("SQL", sqlCmd);

            this.bancoDeDados.execSQL(sqlCmd);*/
            return true;
        }
        catch(SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }
}
