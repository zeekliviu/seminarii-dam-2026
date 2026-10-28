package ro.ase.semdam.adapters;

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

import ro.ase.semdam.R;
import ro.ase.semdam.domeniu.Abonament;
import ro.ase.semdam.domeniu.Frecventa;

public class CustomAdapter extends ArrayAdapter<Abonament> {

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Abonament> objects) {
        super(context, resource, objects);
    }

    private static class ViewHolder {
        TextView tvNume;
        TextView tvCost;
        TextView tvFrecventa;
        TextView tvNrPersoane;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elem_listview, parent, false);
            holder = new ViewHolder();
            holder.tvNume = convertView.findViewById(R.id.tvNume);
            holder.tvCost = convertView.findViewById(R.id.tvCost);
            holder.tvFrecventa = convertView.findViewById(R.id.tvFrecventa);
            holder.tvNrPersoane = convertView.findViewById(R.id.tvNrPersoane);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Abonament abonament = getItem(position);
        if(abonament != null)
        {
            holder.tvNume.setText(abonament.getNume());
            holder.tvCost.setText(String.valueOf(abonament.getCost()));
            holder.tvFrecventa.setText(Frecventa.etichetaDin(abonament.getFrecventa()));
            holder.tvNrPersoane.setText(String.valueOf(abonament.getNrPersoane()));
            // Anulat: titlu rosu; Activ: culoare normala (reciclare ViewHolder)
            if(!abonament.isActiv())
                holder.tvNume.setTextColor(Color.RED);
            else
                holder.tvNume.setTextColor(Color.BLACK);
        }
        return convertView;
    }
}
