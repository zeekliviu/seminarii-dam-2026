package ro.ase.semdam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ro.ase.semdam.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.fabAdauga.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
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
