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
import ro.ase.dam.examen.domeniu.Rezervare;
import ro.ase.dam.examen.domeniu.TipSala;
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
        for (TipSala t : TipSala.values())
            etichete.add(t.getEticheta());
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, etichete);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spTipSala.setAdapter(spinnerAdapter);

        binding.btnSalveaza.setOnClickListener(v -> {
            String titlu = binding.etTitlu.getText().toString().trim();
            String observatii = binding.etObservatii.getText().toString().trim();
            double pret = 0;
            int nrOre = 0;
            try {
                pret = Double.parseDouble(binding.etPret.getText().toString().trim());
                nrOre = Integer.parseInt(binding.etNrOre.getText().toString().trim());
            } catch (NumberFormatException ignored) {
            }
            TipSala tipSala = TipSala.dinEticheta(
                    binding.spTipSala.getSelectedItem() != null
                            ? binding.spTipSala.getSelectedItem().toString()
                            : null);
            boolean confirmata = binding.rbConfirmata.isChecked();
            LocalDate dataRezervare;
            try {
                dataRezervare = LocalDate.parse(binding.etDataRezervare.getText().toString().trim());
            } catch (Exception e) {
                dataRezervare = LocalDate.now();
            }

            Rezervare rezervare = new Rezervare(titlu, observatii, pret, nrOre, tipSala, confirmata, dataRezervare);

            if (Validator.esteValid(rezervare)) {
                Intent raspuns = new Intent();
                raspuns.putExtra(MainActivity.EXTRA_REZERVARE, rezervare);
                setResult(RESULT_OK, raspuns);
                finish();
            } else {
                binding.etTitlu.setError("Rezervare invalidă!");
            }
        });
    }
}
