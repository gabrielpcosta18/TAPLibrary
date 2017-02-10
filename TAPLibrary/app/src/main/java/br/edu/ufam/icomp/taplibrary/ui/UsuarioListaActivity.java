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
import br.edu.ufam.icomp.taplibrary.adapter.UsuarioListaAdapter;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Usuario;
import br.edu.ufam.icomp.taplibrary.utils.Constantes;

public class UsuarioListaActivity extends AppCompatActivity {
    private ListView listaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista);

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

        this.listaUsuario = (ListView) findViewById(R.id.listaUsuario);
        this.listaUsuario.setEmptyView((TextView)findViewById(R.id.usuariosVazio));
        this.listaUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaUsuarioItemClicked(position);
            }
        });

        this.listaUsuario.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return listaUsuarioLongItemClicked(position);
            }
        });
        preencherLista();
    }

    private boolean listaUsuarioLongItemClicked(int p) {
        final int posicao = p;

        new AlertDialog.Builder(this)
                .setTitle("Deletar Usuário")
                .setMessage("Você tem certeza que deseja deletar o usuário?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Usuario usuario = (Usuario) listaUsuario.getItemAtPosition(posicao);
                        new UsuarioDAO(UsuarioListaActivity.this).deletarUsuario(usuario);

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

    private void listaUsuarioItemClicked(int posicao) {
        Usuario usuario = (Usuario) listaUsuario.getItemAtPosition(posicao);
        Intent intent = new Intent(this, UsuarioCadastroActivity.class);
        intent.putExtra("usuario", usuario);
        startActivityForResult(intent, Constantes.ATUALIZAR_LISTAGEM);
    }

    private void preencherLista() {
        UsuarioListaAdapter adaptador = new UsuarioListaAdapter(this,
                R.layout.usuario_lista_item, new UsuarioDAO(this).todosUsuariosLista());
        this.listaUsuario.setAdapter(adaptador);
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
