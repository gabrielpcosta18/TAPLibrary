package br.edu.ufam.icomp.taplibrary.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.adapter.TituloListaAdapter;
import br.edu.ufam.icomp.taplibrary.adapter.UsuarioListaAdapter;
import br.edu.ufam.icomp.taplibrary.bd.TituloDAO;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;
import br.edu.ufam.icomp.taplibrary.utils.Constantes;

public class TituloListaActivity extends AppCompatActivity {
    private ListView listaTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulo_lista);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.listaTitulo = (ListView) findViewById(R.id.listaTitulo);
        listaTitulo.setEmptyView((TextView)findViewById(R.id.titulosVazio));
        this.listaTitulo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaTituloItemClicked(position);
            }
        });

        this.listaTitulo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return listaTituloLongItemClicked(position);
            }
        });
        preencherLista();
    }

    private boolean listaTituloLongItemClicked(int p) {
        final int posicao = p;

        new AlertDialog.Builder(this)
                .setTitle("Deletar Título")
                .setMessage("Você tem certeza que deseja deletar o título?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Titulo titulo = (Titulo) listaTitulo.getItemAtPosition(posicao);
                        new TituloDAO(TituloListaActivity.this).deletarTitulo(titulo);

                        preencherLista();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }

    private void listaTituloItemClicked(int posicao) {
        Titulo titulo = (Titulo) listaTitulo.getItemAtPosition(posicao);
        Intent intent = new Intent(this, TituloCadastroActivity.class);
        intent.putExtra("titulo", titulo);
        startActivityForResult(intent, Constantes.ATUALIZAR_LISTAGEM);
    }

    private void preencherLista() {
        TituloListaAdapter adaptador = new TituloListaAdapter(this,
                R.layout.titulo_lista_item, new TituloDAO(this).todosTitulosLista());
        this.listaTitulo.setAdapter(adaptador);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constantes.ATUALIZAR_LISTAGEM) {
            if (resultCode == RESULT_OK) {
                preencherLista();
            }
        }
    }

}
