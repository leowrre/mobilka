package cum.shplendit.a2prako;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnGoToReg = findViewById(R.id.reg);
        btnGoToReg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegActo.class);
            startActivity(intent);
        });
        Button voiti = findViewById(R.id.voiti);
        voiti.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, VoitiActo.class);
            startActivity(intent1);
        });
    }
}