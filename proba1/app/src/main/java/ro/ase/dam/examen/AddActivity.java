package ro.ase.dam.examen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.ase.dam.examen.databinding.ActivityAddBinding;
import ro.ase.dam.examen.domeniu.Categorie;
import ro.ase.dam.examen.domeniu.Cheltuiala;
import ro.ase.dam.examen.domeniu.Validator;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<String> etichete = new ArrayList<>();
        for (Categorie c : Categorie.values())
            etichete.add(c.getEticheta());
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, etichete);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spCategorie.setAdapter(spinnerAdapter);

        binding.btnSalveaza.setOnClickListener(v -> {
            String denumire = binding.etDenumire.getText().toString().trim();
            String nota = binding.etNota.getText().toString().trim();
            double suma = 0;
            int nrRate = 0;
            try {
                suma = Double.parseDouble(binding.etSuma.getText().toString().trim());
                nrRate = Integer.parseInt(binding.etNrRate.getText().toString().trim());
            } catch (NumberFormatException ignored) {
            }
            Categorie categorie = Categorie.dinEticheta(
                    binding.spCategorie.getSelectedItem() != null
                            ? binding.spCategorie.getSelectedItem().toString()
                            : null);
            boolean platita = binding.rbPlatita.isChecked();
            LocalDate dataCheltuiala;
            try {
                dataCheltuiala = LocalDate.parse(binding.etDataCheltuiala.getText().toString().trim());
            } catch (Exception e) {
                dataCheltuiala = LocalDate.now();
            }

            Cheltuiala cheltuiala = new Cheltuiala(denumire, nota, suma, nrRate, categorie, platita, dataCheltuiala);

            if (Validator.esteValid(cheltuiala)) {
                Intent raspuns = new Intent();
                raspuns.putExtra(MainActivity.EXTRA_CHELTUIALA, cheltuiala);
                setResult(RESULT_OK, raspuns);
                finish();
            } else {
                binding.etDenumire.setError("Cheltuială invalidă!");
            }
        });
    }
}
