package br.edu.ufam.icomp.taplibrary.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.adapter.CustomSpinnerAdapter;
import br.edu.ufam.icomp.taplibrary.adapter.EmprestimoListaAdapter;
import br.edu.ufam.icomp.taplibrary.adapter.TituloListaAdapter;
import br.edu.ufam.icomp.taplibrary.bd.EmprestimoDAO;
import br.edu.ufam.icomp.taplibrary.bd.TituloDAO;
import br.edu.ufam.icomp.taplibrary.model.Emprestimo;
import br.edu.ufam.icomp.taplibrary.utils.Constantes;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listaEmprestimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();
        preencherLista();
    }

    private void iniciarComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEmprestimoLista);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabOnClick();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.listaEmprestimo = (ListView) findViewById(R.id.listaEmprestimo);
        listaEmprestimo.setEmptyView((TextView)findViewById(R.id.emprestimosVazio));
        this.listaEmprestimo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaTituloItemClicked(position);
            }
        });

        this.listaEmprestimo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return listaTituloLongItemClicked(position);
            }
        });
    }

    private void fabOnClick() {
        Intent intent = new Intent(this, EmprestimoCadastroActivity.class);
        startActivityForResult(intent, Constantes.ATUALIZAR_LISTAGEM);
    }

    private boolean listaTituloLongItemClicked(int posicao) {
        return true;
    }

    private void listaTituloItemClicked(int posicao) {
        Emprestimo emprestimo = (Emprestimo) listaEmprestimo.getItemAtPosition(posicao);
        Intent intent = new Intent(this, EmprestimoCadastroActivity.class);
        intent.putExtra("emprestimo", emprestimo);
        startActivityForResult(intent, Constantes.ATUALIZAR_LISTAGEM);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_usuario) {
            Intent intent = new Intent(this, UsuarioListaActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_titulo) {
            Intent intent = new Intent(this, TituloListaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_emprestimo) {
            Intent intent = new Intent(this, EmprestimoCadastroActivity.class);
            startActivityForResult(intent, Constantes.ATUALIZAR_LISTAGEM);
        } else if (id == R.id.nav_visao_geral) {
            Intent intent = new Intent(this, VisaoGeralActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_outros) {
            Intent intent = new Intent(this, RelatorioLivrosPorUsuario.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void preencherLista() {
        EmprestimoListaAdapter adaptador = new EmprestimoListaAdapter(this,
                R.layout.emprestimo_lista_item, new EmprestimoDAO(this).todosEmprestimosLista(true));
        this.listaEmprestimo.setAdapter(adaptador);
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
