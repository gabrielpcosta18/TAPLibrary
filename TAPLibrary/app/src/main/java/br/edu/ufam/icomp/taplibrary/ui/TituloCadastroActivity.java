package br.edu.ufam.icomp.taplibrary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.adapter.CustomSpinnerAdapter;
import br.edu.ufam.icomp.taplibrary.bd.TituloDAO;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

public class TituloCadastroActivity extends AppCompatActivity {
    private final String extra = "titulo";

    private EditText edtTitulo;
    private EditText edtEditora;
    private EditText edtAutor;
    private EditText edtDescricao;
    private EditText edtNumeroExemplares;
    private EditText edtDiasEmprestimo;
    private EditText edtAnoPublicacao;
    private EditText edtIdentUnique;
    private Spinner spnTipo;

    private Button btnConfirmar;

    private Titulo titulo;
    private ArrayList<String> tipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulo_cadastro);

        if(getIntent().hasExtra(extra)) {
            this.titulo = (Titulo) getIntent().getSerializableExtra(extra);
        } else this.titulo = new Titulo();

        iniciarComponentes();

        if(this.titulo.getId() != -1)
            preencherCampos();
    }

    private void iniciarComponentes() {
        this.tipos = new ArrayList<String>();
        this.tipos.add("Livro");
        this.tipos.add("Periódico");
        this.tipos.add("Revista");
        this.tipos.add("Tese");
        this.tipos.add("Dissertação");
        this.tipos.add("Monografia");

        this.setTitle("Título");
        edtTitulo = (EditText) findViewById(R.id.cadastroTituloTitulo);
        edtEditora = (EditText) findViewById(R.id.cadastroTituloEditora);
        edtAutor = (EditText) findViewById(R.id.cadastroTituloAutor);
        edtDescricao = (EditText) findViewById(R.id.cadastroTituloDescricao);
        edtNumeroExemplares = (EditText) findViewById(R.id.cadastroTituloNumExemplares);
        edtDiasEmprestimo = (EditText) findViewById(R.id.cadastroTituloDiasEmprestimo);
        edtAnoPublicacao = (EditText) findViewById(R.id.cadastroTituloAnoPublicacao);
        edtIdentUnique = (EditText) findViewById(R.id.cadastroTituloIdentUnique);

        CustomSpinnerAdapter tipoAdaptador = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, tipos);
        spnTipo = (Spinner) findViewById(R.id.cadastroTituloTipo);
        this.spnTipo.setAdapter(tipoAdaptador);

        this.btnConfirmar = (Button) findViewById(R.id.btnConfirmarCadastroTitulo);
        this.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConfirmarOnClick();
            }
        });
    }

    private void preencherCampos() {
        edtTitulo.setText(this.titulo.getTitulo());
        edtEditora.setText(this.titulo.getEditora());
        edtAutor.setText(this.titulo.getAutor());
        edtDescricao.setText(this.titulo.getDescricao());
        edtNumeroExemplares.setText(Integer.toString(this.titulo.getNumeroExemplares()));
        edtDiasEmprestimo.setText(Integer.toString(this.titulo.getDiasEmprestimo()));
        edtAnoPublicacao.setText(Integer.toString(this.titulo.getAnoPublicacao()));
        edtIdentUnique.setText(this.titulo.getIdentUnique());

        CustomSpinnerAdapter tipoAdaptador = (CustomSpinnerAdapter) spnTipo.getAdapter();
        this.spnTipo.setSelection(tipoAdaptador.getItemsList().indexOf(this.titulo.getTipo()));
    }

    private void btnConfirmarOnClick() {
        TituloDAO tituloDAO = new TituloDAO(this);

        if(edtTitulo.getText().toString().matches("")
                || edtAnoPublicacao.getText().toString().matches("")
                || edtDescricao.getText().toString().matches("")
                || edtAutor.getText().toString().matches("")
                || edtEditora.getText().toString().matches("")
                || edtIdentUnique.getText().toString().matches("")
                || edtDiasEmprestimo.getText().toString().matches("")
                || edtNumeroExemplares.getText().toString().matches("")) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
        else {
            this.titulo.setTitulo(edtTitulo.getText().toString());
            this.titulo.setEditora(edtEditora.getText().toString());
            this.titulo.setAutor(edtAutor.getText().toString());
            this.titulo.setDescricao(edtDescricao.getText().toString());
            this.titulo.setNumeroExemplares(Integer.parseInt(edtNumeroExemplares.getText().toString()));
            this.titulo.setDiasEmprestimo(Integer.parseInt(edtDiasEmprestimo.getText().toString()));
            this.titulo.setAnoPublicacao(Integer.parseInt(edtAnoPublicacao.getText().toString()));
            this.titulo.setIdentUnique(edtIdentUnique.getText().toString());
            this.titulo.setTipo(spnTipo.getSelectedItem().toString());

            boolean resultado = true;

            if (this.titulo.getId() != -1) {
                tituloDAO.atualizarTitulo(this.titulo);
            } else {
                resultado = tituloDAO.adicionarTitulo(this.titulo);
                if(!resultado) {
                    Toast.makeText(this,
                            "Número de Identificação já inserido", Toast.LENGTH_SHORT).show();
                }
            }

            if(resultado) {
                this.setResult(RESULT_OK);
                this.finish();
            }
        }
    }
}
