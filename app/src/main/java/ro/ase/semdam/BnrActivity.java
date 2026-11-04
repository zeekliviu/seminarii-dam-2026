package ro.ase.semdam;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.ase.semdam.databinding.ActivityBnrBinding;
import ro.ase.semdam.retea.BnrClient;
import ro.ase.semdam.retea.CursValutar;

public class BnrActivity extends AppCompatActivity {

    private ActivityBnrBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());

        binding = ActivityBnrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        binding.btnActualizeaza.setOnClickListener(view -> {
            binding.btnActualizeaza.setEnabled(false);
            executor.execute(() -> {try {
                List<CursValutar> cursuri = BnrClient.descarca();
                mainHandler.post(() -> {
                    for(CursValutar curs : cursuri) {
                        if("EUR".equals(curs.getMoneda()))
                            binding.tvEur.setText("EUR: "+curs.getValoare());
                        else if ("USD".equals(curs.getMoneda())) {
                            binding.tvUsd.setText("USD: "+curs.getValoare());
                        }
                    }
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    Toast.makeText(this, "Eroare retea: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            finally {
                mainHandler.post(() -> {binding.btnActualizeaza.setEnabled(true);});
            }});
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}