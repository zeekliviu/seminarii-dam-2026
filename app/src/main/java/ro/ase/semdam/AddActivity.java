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
import ro.ase.semdam.domeniu.Abonament;
import ro.ase.semdam.domeniu.Frecventa;

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

        String[] frecvente = {"Lunar", "Anual", "Saptamanal"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, frecvente);

        binding.spFrecventa.setAdapter(adapter);

        binding.btnSalveaza.setOnClickListener(v -> {
            String nume = binding.etNume.getText().toString().trim();
            double cost = Double.parseDouble(binding.etCost.getText().toString().trim());
            // Uitasem sa adaug isCoreLibraryDesugaringEnabled = true in build.gradle.kts din app.
            // Acum nu ar trebui sa va mai dea eroare de compilare
            LocalDate data = LocalDate.parse(binding.etDataReinnoire.getText().toString().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            String observatii = binding.etObservatii.getText().toString();
            int nrPersoane = Integer.parseInt(binding.etNrPersoane.getText().toString());
            Frecventa frecventa = Frecventa.dinEticheta(binding.spFrecventa.getSelectedItem().toString());
            int idBifat = binding.rgActiv.getCheckedRadioButtonId();
            boolean activ = idBifat == binding.rbActiv.getId();

            Abonament abonament = new Abonament(nume, observatii, cost, nrPersoane, frecventa, activ, data);
            Log.d("AddActivity", abonament.toString());
            Toast.makeText(this, "Salvat: " + abonament.toString(), Toast.LENGTH_LONG).show();
        });
    }
}