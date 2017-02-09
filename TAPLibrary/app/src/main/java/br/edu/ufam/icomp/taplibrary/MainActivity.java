package br.edu.ufam.icomp.taplibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText edtUsuario;
    private EditText edtSenha;
    private Button btnLogin;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        edtUsuario = (EditText) findViewById(R.id.loginUsuario);
        edtSenha = (EditText) findViewById(R.id.loginSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCadastrarOnClick();
            }
        });

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

    }
}
