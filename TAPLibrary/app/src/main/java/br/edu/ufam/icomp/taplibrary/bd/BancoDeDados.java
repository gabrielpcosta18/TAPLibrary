package br.edu.ufam.icomp.taplibrary.bd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by gabri on 09/02/2017.
 */

public class BancoDeDados extends SQLiteOpenHelper {
    public static final int VERSAO_BANCO = 1;
    public static final String NOME_BANCO = "librarybd.db";

    private SQLiteDatabase bancoDeDados;
    private final Context contexto;
    private String PATH_BANCO;


    public BancoDeDados(Context contexto) {
        super(contexto, NOME_BANCO, null, VERSAO_BANCO);
        this.contexto = contexto;

        try {
            this.PATH_BANCO = "/data/data/" + contexto.getPackageName()  + "/databases/";

            criarBancoDeDados();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criarBancoDeDados() throws IOException{
        boolean bancoExiste = verificarBancoDeDados();

        if(bancoExiste) { }
        else{
            this.getReadableDatabase();

            try {
                copiarBancoDeDados();
            } catch (IOException e) {
                throw new Error("Erro na cópia do banco de dados " + e.getMessage() + " " + this.PATH_BANCO);
            }
        }

    }

    private boolean verificarBancoDeDados(){

        SQLiteDatabase verificarBD = null;

        try{
            String myPath = PATH_BANCO + NOME_BANCO;
            verificarBD = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
        }

        if(verificarBD != null){
            verificarBD.close();
        }

        return verificarBD != null;
    }

    private void copiarBancoDeDados() throws IOException{
        InputStream input = contexto.getAssets().open(NOME_BANCO);
        String arquivoSaida = PATH_BANCO + NOME_BANCO;

        OutputStream output = new FileOutputStream(arquivoSaida);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = input.read(buffer))>0){
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();
    }

    public void abrirBancoDeDados() throws SQLException{
        String path = PATH_BANCO + NOME_BANCO;
        bancoDeDados = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if(bancoDeDados!= null)
            bancoDeDados.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
