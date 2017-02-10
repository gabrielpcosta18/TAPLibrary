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
import br.edu.ufam.icomp.taplibrary.model.Emprestimo;
import br.edu.ufam.icomp.taplibrary.model.Usuario;

/**
 * Created by gabri on 09/02/2017.
 */

public class EmprestimoListaAdapter extends ArrayAdapter {
    public EmprestimoListaAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public EmprestimoListaAdapter(Context context, int resource, List<Emprestimo> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.emprestimo_lista_item, null);
        }

        Emprestimo emprestimo = (Emprestimo) getItem(posicao);
        Log.d("Emprestimo", emprestimo.toString());

        if (emprestimo != null) {
            TextView txtTitulo = (TextView) v.findViewById(R.id.emprestimo_lista_titulo);
            TextView txtUsuario = (TextView) v.findViewById(R.id.emprestimo_lista_usuario);

            if (txtUsuario != null) {
                txtUsuario.setText(emprestimo.getUsuario().getNome());
            }

            if (txtTitulo != null) {
                txtTitulo.setText(emprestimo.getTitulo().getTitulo());
            }
        }

        return v;
    }
}