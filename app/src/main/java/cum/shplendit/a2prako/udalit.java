package cum.shplendit.a2prako;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast; // <-- 1. Добавили этот импорт

import androidx.appcompat.app.AppCompatActivity;

public class udalit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {

            // 2. Добавили всплывающее окно (покажется на 2 секунды)
            Toast.makeText(this, "Запись удалена", Toast.LENGTH_SHORT).show();

            // 3. Твой исходный код остался без изменений
            Intent intent2 = new Intent(this, glavnoe_menu_activiti.class);
            startActivity(intent2);
        });
    }
}
