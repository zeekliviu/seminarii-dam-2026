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
import ro.ase.dam.examen.domeniu.Cheltuiala;
import ro.ase.dam.examen.domeniu.CheltuieliService;
import ro.ase.dam.examen.json.ParserJson;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_CHELTUIALA = "cheltuiala";

    private ActivityMainBinding binding;
    private final List<Cheltuiala> cheltuieli = new ArrayList<>();
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
        if (item.getItemId() == R.id.action_suma_totala) {
            Toast.makeText(this, "Suma totală: " + CheltuieliService.sumaTotala(cheltuieli), Toast.LENGTH_LONG).show();
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
                        Cheltuiala primita = IntentCompat.getSerializableExtra(
                                result.getData(), EXTRA_CHELTUIALA, Cheltuiala.class);
                        if (primita != null) {
                            cheltuieli.add(primita);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        adapter = new CustomAdapter(this, 0, cheltuieli);
        binding.lvCheltuieli.setAdapter(adapter);
        incarcaDinAssets();

        binding.lvCheltuieli.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Ștergere")
                    .setMessage("Ștergeți cheltuiala?")
                    .setPositiveButton("Da", (d, w) -> {
                        cheltuieli.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Nu", null)
                    .show();
            return true;
        });
    }

    private void incarcaDinAssets() {
        try {
            String json = citesteAsset("cheltuieli.json");
            List<Cheltuiala> citite = ParserJson.dinText(json);
            cheltuieli.clear();
            cheltuieli.addAll(citite);
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
