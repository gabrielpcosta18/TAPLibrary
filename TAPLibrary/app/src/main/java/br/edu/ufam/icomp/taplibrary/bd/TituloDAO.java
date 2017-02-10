package br.edu.ufam.icomp.taplibrary.bd;

import android.content.Context;
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

public class TituloDAO {
    private SQLiteDatabase bancoDeDados;
    private static final String NOME_TABELA = "Titulo";

    public TituloDAO(Context contexto) {
        this.bancoDeDados = (new BancoDeDados(contexto)).getWritableDatabase();
    }

    private Titulo tituloDoCursor(Cursor cursor) {
        return new Titulo(cursor.getInt(0),
                cursor.getString(2),
                cursor.getString(1),
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

    public Cursor todosTitulosCursor(boolean filtrar) {
        String query = "SELECT *" +
                " FROM " + NOME_TABELA + " as e ";

        if(filtrar)
            query += " WHERE numeroExemplares > (SELECT COUNT(*) FROM Emprestimo WHERE tituloId = e._id and foiDevolvido = 0)";

        query += " ORDER BY titulo";

        return this.bancoDeDados.rawQuery(query, null);
    }

    public List todosTitulosLista(boolean filtrar) {
        ArrayList<Titulo> titulos = new ArrayList<>();
        Cursor cursor = todosTitulosCursor(filtrar);

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

    public boolean deletarTitulo(Titulo titulo) {
        try {
            String sqlCmd = "DELETE FROM " + NOME_TABELA +
                    " WHERE _id = " + Integer.toString(titulo.getId());

            Log.d("SQL", sqlCmd);

            this.bancoDeDados.execSQL(sqlCmd);
            return true;
        }
        catch(SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }

    public boolean atualizarTitulo(Titulo titulo) {
        try {
            String sqlCmd = "UPDATE " + NOME_TABELA +
                    " SET titulo = '" + titulo.getTitulo() + "' "  +
                    ", editora = '" + titulo.getEditora() + "' "  +
                    ", autor = '" + titulo.getAutor() + "' "  +
                    ", descricao = '" + titulo.getDescricao() + "' "  +
                    ", tipo = '" + titulo.getTipo() + "' "  +
                    ", identUnique = '" + titulo.getIdentUnique() + "' "  +
                    ", numeroExemplares = " + Integer.toString(titulo.getNumeroExemplares()) + " "  +
                    ", diasEmprestimo = " + Integer.toString(titulo.getDiasEmprestimo()) + " "  +
                    ", anoPublicacao = " + Integer.toString(titulo.getAnoPublicacao()) + " "  +
                    " WHERE _id = " + titulo.getId();

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
