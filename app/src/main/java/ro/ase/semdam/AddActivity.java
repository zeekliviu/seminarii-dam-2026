package ro.ase.semdam;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ro.ase.semdam.databinding.ActivityAddBinding;
import ro.ase.semdam.domeniu.Anunt;
import ro.ase.semdam.domeniu.Categorie;

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

        String[] categorii = {"Electronice", "Carti", "Imbracaminte", "Altele"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorii);

        binding.spCategorie.setAdapter(adapter);

        binding.btnSalveaza.setOnClickListener(v -> {
            String titlu = binding.etTitlu.getText().toString().trim();
            double pret = Double.parseDouble(binding.etPret.getText().toString().trim());
            String descriere = binding.etDescriere.getText().toString().trim();
            int cantitate = Integer.parseInt(binding.etCantitate.getText().toString().trim());
            Categorie categorie = Categorie.dinEticheta(binding.spCategorie.getSelectedItem().toString());
            int idBifat = binding.rgStare.getCheckedRadioButtonId();
            boolean nou = idBifat == binding.rbNou.getId();
            LocalDate dataPublicare = LocalDate.parse(binding.etDataPublicare.getText().toString().trim(), DateTimeFormatter.ISO_LOCAL_DATE);

            Anunt anunt = new Anunt(titlu, descriere, pret, cantitate, categorie, nou, dataPublicare);
            Log.d("AddActivity", anunt.toString());
            Toast.makeText(this, "Salvat: " + anunt.toString(), Toast.LENGTH_SHORT).show();
        });

    }
}