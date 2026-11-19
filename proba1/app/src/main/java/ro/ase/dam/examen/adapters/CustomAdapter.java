package ro.ase.dam.examen.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ro.ase.dam.examen.R;
import ro.ase.dam.examen.domeniu.Cheltuiala;

public class CustomAdapter extends ArrayAdapter<Cheltuiala> {

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Cheltuiala> objects) {
        super(context, resource, objects);
    }

    private static class ViewHolder {
        TextView tvDenumire;
        TextView tvNota;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elem_listview, parent, false);
            holder = new ViewHolder();
            holder.tvDenumire = convertView.findViewById(R.id.tvDenumire);
            holder.tvNota = convertView.findViewById(R.id.tvNota);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cheltuiala cheltuiala = getItem(position);
        if (cheltuiala != null) {
            holder.tvDenumire.setText(cheltuiala.getDenumire());
            holder.tvNota.setText(cheltuiala.getNota());
            if (cheltuiala.isPlatita()) {
                holder.tvDenumire.setTextColor(Color.CYAN);
            } else {
                holder.tvDenumire.setTextColor(Color.RED);
            }
        }
        return convertView;
    }
}
