package br.edu.ufam.icomp.taplibrary.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ufam.icomp.taplibrary.R;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

/**
 * Created by gabri on 09/02/2017.
 */

public class UsuarioListaAdapter extends ArrayAdapter {
    public UsuarioListaAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public UsuarioListaAdapter(Context context, int resource, List<Usuario> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.usuario_lista_item, null);
        }

        Usuario usuario = (Usuario) getItem(posicao);
        Log.d("Usuario", usuario.toString());

        if (usuario != null) {
            TextView txtNome= (TextView) v.findViewById(R.id.usuario_lista_nome);
            TextView txtTipo= (TextView) v.findViewById(R.id.usuario_lista_tipo);

            if (txtNome != null) {
                txtNome.setText(usuario.getNome());
            }

            if (txtTipo != null) {
                txtTipo.setText(usuario.getAdmin() == 0? "Usuário Comum" : "Usuário Administrador");
            }
        }

        return v;
    }

}
