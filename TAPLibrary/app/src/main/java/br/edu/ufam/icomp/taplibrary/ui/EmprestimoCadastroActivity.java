package br.edu.ufam.icomp.taplibrary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.adapter.CustomSpinnerAdapter;
import br.edu.ufam.icomp.taplibrary.bd.EmprestimoDAO;
import br.edu.ufam.icomp.taplibrary.bd.TituloDAO;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Emprestimo;
import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

public class EmprestimoCadastroActivity extends AppCompatActivity {
    private final String extra = "emprestimo";

    private Spinner spnTitulo;
    private Spinner spnUsuario;
    private Button btnConfirmar;

    private Emprestimo emprestimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emprestimo_cadastro);

        if(getIntent().hasExtra(extra)) {
            this.emprestimo = (Emprestimo) getIntent().getSerializableExtra(extra);
        } else this.emprestimo = new Emprestimo();

        iniciarComponentes();

        if(this.emprestimo.getId() != -1)
            preencherCampos();
    }

    private void iniciarComponentes() {
        CustomSpinnerAdapter tituloSpinnerAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, new TituloDAO(this).todosTitulosLista());
        this.spnTitulo = (Spinner) findViewById(R.id.cadastroEmprestimoTitulo);
        this.spnTitulo.setAdapter(tituloSpinnerAdapter);

        CustomSpinnerAdapter usuarioSpinnerAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, new UsuarioDAO(this).todosUsuariosLista());
        this.spnUsuario = (Spinner) findViewById(R.id.cadastroEmprestimoUsuario);
        this.spnUsuario.setAdapter(usuarioSpinnerAdapter);

        this.btnConfirmar = (Button) findViewById(R.id.btnConfirmarCadastroEmprestimo);
        this.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConfirmarOnClick();
            }
        });
    }

    private void preencherCampos() {
        CustomSpinnerAdapter usuarioAdaptador = (CustomSpinnerAdapter) spnUsuario.getAdapter();
        CustomSpinnerAdapter tituloAdaptador = (CustomSpinnerAdapter) spnTitulo.getAdapter();

        this.spnUsuario.setSelection(usuarioAdaptador.getItemsList().indexOf(this.emprestimo.getUsuario()));
        this.spnTitulo.setSelection(tituloAdaptador.getItemsList().indexOf(this.emprestimo.getTitulo()));
    }

    private void btnConfirmarOnClick() {
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(this);

        this.emprestimo.setTitulo((Titulo) spnTitulo.getSelectedItem());
        this.emprestimo.setUsuario((Usuario) spnUsuario.getSelectedItem());

        if(this.emprestimo.getId() != -1) {
            emprestimoDAO.atualizarEmprestimo(this.emprestimo);
        }
        else {
            emprestimoDAO.adicionarEmprestimo(this.emprestimo);
        }

        this.setResult(RESULT_OK);
        this.finish();
    }
}
