package br.edu.ufam.icomp.taplibrary.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taplibrary.model.Emprestimo;

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
                new TituloDAO(this.contexto).tituloPorId(cursor.getInt(2)),
                new UsuarioDAO(this.contexto).usuarioPorId(cursor.getInt(1)));
    }

    public List<Emprestimo> todosEmprestimosLista() {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        Cursor cursor = this.bancoDeDados.rawQuery("SELECT * " +
                " FROM " + NOME_TABELA + "", null);

        while(cursor.moveToNext()) {
            emprestimos.add(emprestimoDoCursor(cursor));
        }

        return emprestimos;
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
            /*String sqlCmd = "UPDATE " + NOME_TABELA +
                    " SET employeerestitutionid = " + rental.getEmployeeRestitution().getId() + ", wasdeveloped = "
                    + (rental.isWasDeveloped()? "1":"0") +
                    " WHERE _id = " + rental.getId();

            Log.d("SQL", sqlCmd);
            this.database.execSQL(sqlCmd);*/
            return true;
        }
        catch(SQLException e) {
            Log.e("TAPLibrary", e.getMessage());
            return false;
        }
    }
}
