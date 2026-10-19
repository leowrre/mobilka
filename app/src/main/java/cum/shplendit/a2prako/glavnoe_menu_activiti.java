package cum.shplendit.a2prako;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class glavnoe_menu_activiti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glavnoe_menu);

        Button dobavit = findViewById(R.id.dobavit);
        dobavit.setOnClickListener(v -> {
            Intent intent = new Intent(this, dobavit.class);
            startActivity(intent);
        });
        Button izmenit = findViewById(R.id.izmenit);
        izmenit.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, izmenit.class);
            startActivity(intent2);
        });
        Button udalit = findViewById(R.id.udalit);
        udalit.setOnClickListener(v -> {
            Intent intent3 = new Intent(this, udalit.class);
            startActivity(intent3);
        });
}}
