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
import ro.ase.dam.examen.domeniu.Rezervare;

public class CustomAdapter extends ArrayAdapter<Rezervare> {

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Rezervare> objects) {
        super(context, resource, objects);
    }

    private static class ViewHolder {
        TextView tvTitlu;
        TextView tvObservatii;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elem_listview, parent, false);
            holder = new ViewHolder();
            holder.tvTitlu = convertView.findViewById(R.id.tvTitlu);
            holder.tvObservatii = convertView.findViewById(R.id.tvObservatii);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Rezervare rezervare = getItem(position);
        if (rezervare != null) {
            holder.tvTitlu.setText(rezervare.getTitlu());
            holder.tvObservatii.setText(rezervare.getObservatii());
            if (rezervare.isConfirmata()) {
                holder.tvTitlu.setTextColor(Color.BLUE);
            } else {
                holder.tvTitlu.setTextColor(Color.MAGENTA);
            }
        }
        return convertView;
    }
}
