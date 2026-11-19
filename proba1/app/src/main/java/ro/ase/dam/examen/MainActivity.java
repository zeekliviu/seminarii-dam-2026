package ro.ase.dam.examen;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ro.ase.dam.examen.adapters.CustomAdapter;
import ro.ase.dam.examen.databinding.ActivityMainBinding;
import ro.ase.dam.examen.domeniu.Rezervare;
import ro.ase.dam.examen.domeniu.RezervariService;
import ro.ase.dam.examen.xml.ParserXml;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_REZERVARE = "rezervare";

    private ActivityMainBinding binding;
    private final List<Rezervare> rezervari = new ArrayList<>();
    private CustomAdapter adapter;
    private ActivityResultLauncher<Intent> addLauncher;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_adauga) {
            addLauncher.launch(new Intent(this, AddActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_suma_preturi) {
            Toast.makeText(this, "Suma prețuri: " + RezervariService.sumaPreturi(rezervari), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Rezervare primita = IntentCompat.getSerializableExtra(
                                result.getData(), EXTRA_REZERVARE, Rezervare.class);
                        if (primita != null) {
                            rezervari.add(primita);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        adapter = new CustomAdapter(this, 0, rezervari);
        binding.lvRezervari.setAdapter(adapter);
        incarcaDinAssets();

        binding.lvRezervari.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Ștergere")
                    .setMessage("Ștergeți rezervarea?")
                    .setPositiveButton("Da", (d, w) -> {
                        rezervari.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Nu", null)
                    .show();
            return true;
        });
    }

    private void incarcaDinAssets() {
        try {
            String xml = citesteAsset("rezervari.xml");
            List<Rezervare> citite = ParserXml.dinText(xml);
            rezervari.clear();
            rezervari.addAll(citite);
            adapter.notifyDataSetChanged();
        } catch (Exception ignored) {
        }
    }

    private String citesteAsset(String nume) throws Exception {
        try (InputStream in = getAssets().open(nume);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
