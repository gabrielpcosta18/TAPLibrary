package br.edu.ufam.icomp.taplibrary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.util.List;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.adapter.CustomSpinnerAdapter;
import br.edu.ufam.icomp.taplibrary.adapter.TituloListaAdapter;
import br.edu.ufam.icomp.taplibrary.adapter.UsuarioListaAdapter;
import br.edu.ufam.icomp.taplibrary.bd.EmprestimoDAO;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

public class RelatorioLivrosPorUsuario extends AppCompatActivity {
    private Spinner spnUsuario;
    private ListView listaLivros;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_livros_por_usuario);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        this.setTitle("Livros por Usuário");
        usuarios = new UsuarioDAO(this).todosUsuariosLista();

        CustomSpinnerAdapter adaptador = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, usuarios);
        this.spnUsuario = (Spinner) findViewById(R.id.spinnerUsuario);
        this.spnUsuario.setAdapter(adaptador);

        this.spnUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preencherCampos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.listaLivros = (ListView) findViewById(R.id.relatoriaListaLivro);
    }

    private void preencherCampos(int posicao) {
        TituloListaAdapter adaptador = new TituloListaAdapter(this,
                R.layout.titulo_lista_item, new EmprestimoDAO(this)
                .titulosPorUsuario(usuarios.get(posicao)));
        this.listaLivros.setAdapter(adaptador);
    }
}
