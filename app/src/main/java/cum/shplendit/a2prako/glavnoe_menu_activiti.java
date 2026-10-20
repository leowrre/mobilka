package cum.shplendit.a2prako;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class glavnoe_menu_activiti extends AppCompatActivity {

    // Объявляем переменные на уровне класса
    private RecyclerView recyclerView;
    private ZakazAdapter adapter;
    private List<ZakazItem> zakazList;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glavnoe_menu);

        // 1. Инициализация БД
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        // 2. Инициализация RecyclerView
        recyclerView = findViewById(R.id.recyclerViewZakazi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        zakazList = new ArrayList<>();
        adapter = new ZakazAdapter(zakazList);
        recyclerView.setAdapter(adapter);

        // 3. Кнопки
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

        // 4. Загружаем данные
        loadZakazi();
    }

    // Метод loadZakazi() вынесен ЗА ПРЕДЕЛЫ onCreate()
    private void loadZakazi() {
        zakazList.clear();

        String query = "SELECT z.nomer_zakaza, g.model " +
                "FROM zakazi z " +
                "LEFT JOIN gitara g ON z.gitara = g.id " +
                "ORDER BY z.id DESC";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String nomer = cursor.getString(cursor.getColumnIndexOrThrow("nomer_zakaza"));
                String model = cursor.getString(cursor.getColumnIndexOrThrow("model"));

                zakazList.add(new ZakazItem(0, nomer, "", model, ""));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadZakazi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}