package br.edu.ufam.icomp.taplibrary.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by gabri on 09/02/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter {
    private List<?> _items;

    public CustomSpinnerAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomSpinnerAdapter(Context context, int resource, List<?> items) {
        super(context, resource, items);
        this._items = items;
    }

    public List<?> getItemsList() {
        return  this._items;
    }
}
