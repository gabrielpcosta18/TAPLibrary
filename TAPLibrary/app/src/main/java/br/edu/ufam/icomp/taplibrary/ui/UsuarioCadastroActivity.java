package br.edu.ufam.icomp.taplibrary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.bd.EmprestimoDAO;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

public class UsuarioCadastroActivity extends AppCompatActivity {
    private final String extra = "usuario";

    private EditText edtNome;
    private EditText edtLogin;
    private EditText edtSenha;
    private Switch swcAdministrador;
    private Button btnConfirmar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cadastro);

        if(getIntent().hasExtra(extra)) {
            this.usuario = (Usuario) getIntent().getSerializableExtra(extra);
        } else this.usuario = new Usuario();

        iniciarComponentes();

        if(this.usuario.getId() != -1)
            preencherCampos();
    }

    private void iniciarComponentes() {
        this.setTitle("Usuário");
        this.edtNome = (EditText) findViewById(R.id.cadastroUsuarioNome);
        this.edtLogin = (EditText) findViewById(R.id.cadastroUsuarioLogin);
        this.edtSenha = (EditText) findViewById(R.id.cadastroUsuarioSenha);
        this.swcAdministrador = (Switch) findViewById(R.id.cadastroUsuarioAdministrador);

        this.btnConfirmar = (Button) findViewById(R.id.btnConfirmarCadastroUsuario);
        this.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConfirmarOnClick();
            }
        });
    }

    private void preencherCampos() {
        this.edtLogin.setText(this.usuario.getLogin());
        this.edtSenha.setText(this.usuario.getSenha());
        this.edtNome.setText(this.usuario.getNome());

        this.swcAdministrador.setChecked((this.usuario.getAdmin() == 1));
    }

    private void btnConfirmarOnClick() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        this.usuario.setNome(edtNome.getText().toString());
        this.usuario.setLogin(edtLogin.getText().toString());
        this.usuario.setSenha(edtSenha.getText().toString());
        this.usuario.setAdmin(swcAdministrador.isChecked()? 1 : 0);

        if(this.usuario.getId() != -1) {
            usuarioDAO.atualizarUsuario(this.usuario);
        }
        else {
            usuarioDAO.adicionarUsuario(this.usuario);
        }

        this.setResult(RESULT_OK);
        this.finish();
    }
}
