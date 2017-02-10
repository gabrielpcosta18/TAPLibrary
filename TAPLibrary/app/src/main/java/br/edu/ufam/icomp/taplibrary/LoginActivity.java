package br.edu.ufam.icomp.taplibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ufam.icomp.taplibrary.bd.TituloDAO;
import br.edu.ufam.icomp.taplibrary.bd.UsuarioDAO;
import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;
import br.edu.ufam.icomp.taplibrary.ui.MainActivity;
import br.edu.ufam.icomp.taplibrary.ui.TituloListaActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsuario;
    private EditText edtSenha;
    private Button btnLogin;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        edtUsuario = (EditText) findViewById(R.id.loginUsuario);
        edtSenha = (EditText) findViewById(R.id.loginSenha);
        //btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        /*btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCadastrarOnClick();
            }
        });*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginOnClick();
            }
        });
    }

    private void btnCadastrarOnClick() {

    }

    private void btnLoginOnClick() {
        if(!edtUsuario.getText().toString().matches("")
                || !edtSenha.getText().toString().matches("")) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(this);
            Usuario usuario = new Usuario(edtUsuario.getText().toString(),
                    edtSenha.getText().toString());

            if(usuarioDAO.validarLogin(usuario)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Credenciais Inválidas")
                        .setMessage("Login ou a senha está errada. Tente Novamente.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        }
    }
}
