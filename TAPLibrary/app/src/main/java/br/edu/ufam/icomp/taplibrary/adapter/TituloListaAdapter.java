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
import br.edu.ufam.icomp.taplibrary.model.Titulo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

/**
 * Created by gabri on 09/02/2017.
 */

public class TituloListaAdapter extends ArrayAdapter{
    public TituloListaAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TituloListaAdapter(Context context, int resource, List<Titulo> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.titulo_lista_item, null);
        }

        Titulo titulo = (Titulo) getItem(posicao);
        Log.d("Titulo", titulo.toString());

        if (titulo != null) {
            TextView txtTitulo = (TextView) v.findViewById(R.id.titulo_lista_titulo);
            TextView txtAutor = (TextView) v.findViewById(R.id.titulo_lista_autor);

            if (txtTitulo != null) {
                txtTitulo.setText(titulo.getTitulo());
            }

            if (txtAutor != null) {
                txtAutor.setText(titulo.getAutor());
            }
        }

        return v;
    }
}
