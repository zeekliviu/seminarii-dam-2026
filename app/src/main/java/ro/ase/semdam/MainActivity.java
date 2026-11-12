package ro.ase.semdam;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.ase.semdam.adapters.CustomAdapter;
import ro.ase.semdam.databinding.ActivityMainBinding;
import ro.ase.semdam.domeniu.Abonament;
import ro.ase.semdam.json.IncarcareJson;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private ActivityResultLauncher<Intent> addLauncher;

    private List<Abonament> abonamente = new ArrayList<>();
    private ArrayAdapter<Abonament> adapter;

    private ExecutorService executor;
    private Handler mainHandler;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_despre)
        {
            Toast.makeText(this, "Aplicatie seminar DAM 1088", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.action_bnr)
        {
            startActivity(new Intent(this, BnrActivity.class));
            return true;
        }
        if(id == R.id.action_total_persoane)
        {
            int total = 0;
            for(Abonament a : abonamente)
                total += a.getNrPersoane();
            Toast.makeText(this, String.valueOf(total), Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.action_json)
        {
            executor.execute(() -> {
                try {
                    String text = IncarcareJson.descarca();
                    List<Abonament> citite = IncarcareJson.dinText(text);
                    mainHandler.post(() -> {
                        abonamente.clear();
                        abonamente.addAll(citite);
                        int nrActive = 0;
                        for(Abonament a : citite)
                        {
                            if(a.isActiv())
                                nrActive++;
                        }
                        SharedPreferences prefs = getSharedPreferences("setari_semdam", MODE_PRIVATE);
                        prefs.edit()
                                        .putInt("nr_incarcate", citite.size())
                                        .putInt("nr_active", nrActive)
                                                .apply();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Incarcat: " + citite.size(), Toast.LENGTH_LONG).show();
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> Toast.makeText(this, "Eroare JSON: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            });
            return true;
        }
        if(id == R.id.action_ultima)
        {
            startActivity(new Intent(this, SecondActivity.class));
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

        executor = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        binding.btnDeschide.setOnClickListener(v -> {
//            Toast.makeText(MainActivity.this, "Salut!", Toast.LENGTH_LONG).show();
//        });

        //Button buton = findViewById(R.id.btnDeschide);
//        buton.setOnClickListener(v -> {
//            Toast.makeText(MainActivity.this, "Salut!", Toast.LENGTH_LONG).show();
//
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            startActivity(intent);
//
//        });

        addLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Abonament abonamentPrimit = IntentCompat.getSerializableExtra(result.getData(), "abonament", Abonament.class);
                    int pozSelectie = result.getData().getIntExtra("pozitie", -1);
                    if(abonamentPrimit != null)
                    {
                        if(pozSelectie >= 0)
                        {
                            abonamente.set(pozSelectie, abonamentPrimit);
                        }
                        else {
                            abonamente.add(abonamentPrimit);
                        }
                        Toast.makeText(this,   abonamentPrimit.toString(), Toast.LENGTH_LONG).show();
//                        abonamente.add(abonamentPrimit);
                        adapter.notifyDataSetChanged();
                    }
                }
        }
        );

        binding.fabAdauga.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddActivity.class);
            addLauncher.launch(intent);
        });

        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, abonamente);
        adapter = new CustomAdapter(this, 0, abonamente);
        // ListView lvElemente = findViewById(R.id.lvAbonamente);
        binding.lvAbonamente.setAdapter(adapter);

        binding.lvAbonamente.setOnItemClickListener((parent, view, position, id) -> {
            Abonament selectat = abonamente.get(position);
            Intent intent = new Intent(this, AddActivity.class);
            intent.putExtra("abonament", selectat);
            intent.putExtra("pozitie", position);
            addLauncher.launch(intent);
        });

        binding.lvAbonamente.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Stergere abonament")
                    .setMessage("Sigur stergi abonamentul?")
                    .setPositiveButton("Da", (d, w) -> {
                        abonamente.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Nu", null)
                    .show();
            return true;
        });

        Log.d(TAG, "s-a apelat onCreate");

        SharedPreferences prefsDeschidere = getSharedPreferences("setari_semdam", MODE_PRIVATE);
        int nrActiveSalvat = prefsDeschidere.getInt("nr_active", 0);
        Toast.makeText(this, "Ultima incarcare: " + nrActiveSalvat, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "s-a apelat onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause?");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy!!!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}