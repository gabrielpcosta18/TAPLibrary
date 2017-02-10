package br.edu.ufam.icomp.taplibrary.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.bd.EmprestimoDAO;
import br.edu.ufam.icomp.taplibrary.bd.TituloDAO;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

public class VisaoGeralActivity extends AppCompatActivity {
    private TextView txtTotalUsuarios;
    private TextView txtTotalEmprestimos;
    private TextView txtTotalEmprestimosPendentes;
    private TextView txtTotalLivros;
    private TextView txtLivroMaisLido;
    private TextView txtQuantidadeLivroMaisLido;
    private TextView txtUsuarioMaisEmprestimo;
    private TextView txtQuantidadeUsuarioMaisEmprestimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visao_geral);

        iniciarComponentes();
        preencherCampos();
    }

    private void iniciarComponentes() {
        this.setTitle("Visão Geral");
        this.txtTotalUsuarios = (TextView) findViewById(R.id.visaoGeralTotalUsuarios);
        this.txtTotalEmprestimos = (TextView) findViewById(R.id.visaoGeralTotalEmprestimos);
        this.txtTotalEmprestimosPendentes = (TextView) findViewById(R.id.visaoGeralTotalEmprestimosAtivos);
        this.txtTotalLivros = (TextView) findViewById(R.id.visaoGeralTotalLivros);
        this.txtLivroMaisLido = (TextView) findViewById(R.id.visaoGeralLivroMaisLido);
        this.txtQuantidadeLivroMaisLido = (TextView) findViewById(R.id.visaoGeralQuantidadeLivroMaisLido);
        this.txtUsuarioMaisEmprestimo = (TextView) findViewById(R.id.visaoGeralUsuarioMaisEmprestimos);
        this.txtQuantidadeUsuarioMaisEmprestimo = (TextView)
                findViewById(R.id.visaoGeralQuantidadeUsuarioMaisEmprestimos);
    }

    private void preencherCampos() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        TituloDAO tituloDAO = new TituloDAO(this);
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(this);

        this.txtTotalUsuarios.setText(Integer.toString(usuarioDAO.todosUsuariosLista().size()));
        this.txtTotalLivros.setText(Integer.toString(tituloDAO.todosTitulosLista(false).size()));
        this.txtTotalEmprestimos.setText(Integer.toString(emprestimoDAO.todosEmprestimosLista(false).size()));
        this.txtTotalEmprestimosPendentes.setText(Integer.toString(emprestimoDAO.todosEmprestimosLista(true).size()));

        Pair<Titulo, Integer> tituloMaisLido = emprestimoDAO.tituloMaisLido();
        if(tituloMaisLido != null) {
            this.txtLivroMaisLido.setText(tituloMaisLido.first.getTitulo());
            this.txtQuantidadeLivroMaisLido.setText("Lido " + tituloMaisLido.second.toString() + " vezes");
        }

        Pair<Usuario, Integer> usuarioMaisEmprestimo = emprestimoDAO.usuarioMaisEmprestimo();
        if(usuarioMaisEmprestimo != null) {
            this.txtUsuarioMaisEmprestimo.setText(usuarioMaisEmprestimo.first.getNome());
            this.txtQuantidadeUsuarioMaisEmprestimo.setText("Emprestou " + usuarioMaisEmprestimo.second.toString() + " vezes");
        }
    }
}
