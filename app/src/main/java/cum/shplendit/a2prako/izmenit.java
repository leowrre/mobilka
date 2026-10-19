package cum.shplendit.a2prako;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast; // <-- 1. Добавили этот импорт

import androidx.appcompat.app.AppCompatActivity;

public class izmenit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        Button update = findViewById(R.id.update);
        update.setOnClickListener(v -> {

            // 2. Добавили всплывающее окно с нужным текстом
            Toast.makeText(this, "Запись изменена", Toast.LENGTH_SHORT).show();

            // 3. Твой исходный код остался без изменений
            Intent intent1 = new Intent(this, glavnoe_menu_activiti.class);
            startActivity(intent1);
        });
    }
}