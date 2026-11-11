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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.ase.semdam.adapters.CustomAdapter;
import ro.ase.semdam.databinding.ActivityMainBinding;
import ro.ase.semdam.domeniu.Anunt;
import ro.ase.semdam.json.IncarcareJson;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private ActivityMainBinding binding;

    private ActivityResultLauncher<Intent> addLauncher;

    ExecutorService executor;
    Handler mainHandler;

    private List<Anunt> anunturi = new ArrayList<>();
    private ArrayAdapter<Anunt> adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_bnr)
        {
            startActivity(new Intent(this, BnrActivity.class));
            return true;
        }
        if(id == R.id.action_despre)
        {
            Toast.makeText(this, "Aplicatie seminar DAM 1086", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.action_total_stoc)
        {
            int total = 0;
            for(Anunt a : anunturi)
                total += a.getCantitate();
            Toast.makeText(this, String.valueOf(total), Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.action_json)
        {
            executor.execute(() -> {
                try {
                    String text = IncarcareJson.descarca();
                    List<Anunt> citite = IncarcareJson.dinText(text);
                    mainHandler.post(() -> {
                       anunturi.clear();
                       anunturi.addAll(citite);
                       int nrNoi = 0;
                       for(Anunt a : citite)
                       {
                           if(a.isNou())
                               nrNoi++;
                       }
                        SharedPreferences prefs = getSharedPreferences("setari_semdam", MODE_PRIVATE);
                        prefs.edit()
                                        .putInt("nr_incarcate", citite.size())
                                        .putInt("nr_noi", nrNoi)
                                                .apply();
                       adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Incarcat: " + citite.size(), Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {
                    mainHandler.post(() -> {
                       Toast.makeText(this, "Eroare JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
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

        // View Binding: decomentați getRoot (+ binding.main / binding.btnToast mai jos)
        // setContentView(binding.getRoot());
        // Varianta clasică: R.layout + findViewById (pereche)
        //setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            // Cu View Binding: binding.main în loc de findViewById(R.id.main)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "s-a apelat onCreate");

        SharedPreferences prefsDeschidere = getSharedPreferences("setari_semdam", MODE_PRIVATE);
        int nrNoiSalvat = prefsDeschidere.getInt("nr_noi", 0);
        Toast.makeText(this, "Ultima incarcare: " + nrNoiSalvat, Toast.LENGTH_SHORT).show();

//        binding.btnToast.setOnClickListener(v -> {
//            Toast.makeText(this, "Am apasat", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            startActivity(intent);
//        });

        //Button button = findViewById(R.id.btnToast);
//        button.setOnClickListener(v -> {
//            Toast.makeText(this, "Am apasat", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//            startActivity(intent);
//        });

        addLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK && result.getData() != null)
            {
                Anunt anuntPrimit = IntentCompat.getSerializableExtra(result.getData(), "element", Anunt.class);

                if(anuntPrimit != null)
                {
                    int pozSelectie = result.getData().getIntExtra("pozitie", -1);
                    if(pozSelectie >= 0)
                    {
                        anunturi.set(pozSelectie, anuntPrimit);
                    }
                    else {
                        Toast.makeText(this, anuntPrimit.toString(), Toast.LENGTH_LONG).show();
                        anunturi.add(anuntPrimit);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.fabAdauga.setOnClickListener(view -> {
            addLauncher.launch(new Intent(this, AddActivity.class));
        });

//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, anunturi);
        adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, anunturi);
        // ListView lvAnunturi = findViewById(R.id.lvElemente)
        binding.lvAnunturi.setAdapter(adapter);

        binding.lvAnunturi.setOnItemClickListener((parent, view, position, id) -> {
            Anunt selectat = anunturi.get(position);
            Intent intent = new Intent(this, AddActivity.class);
            intent.putExtra("element", selectat);
            intent.putExtra("pozitie", position);
            addLauncher.launch(intent);
        });

        binding.lvAnunturi.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Stergere")
                    .setMessage("Stergeti elementul?")
                    .setPositiveButton("Da", (d, w) -> {
                        anunturi.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Nu", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "dar onResume?");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy????");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
