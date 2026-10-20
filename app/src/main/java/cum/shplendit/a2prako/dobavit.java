package cum.shplendit.a2prako;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class dobavit extends AppCompatActivity {
    private Spinner spinnerGitara;
    private SQLiteDatabase database;
    private List<Long> guitarIds; // Храним ID гитар для связи с заказами

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        spinnerGitara = findViewById(R.id.guitar);
        Button add = findViewById(R.id.add);

        // ИСПРАВЛЕНИЕ 2: Загрузка гитар с названиями компаний
        guitarIds = new ArrayList<>();
        List<String> guitarDisplayList = new ArrayList<>();

        // JOIN с таблицей compania для получения названия компании
        Cursor cursor = database.rawQuery(
                "SELECT g.id, g.model, c.name as compania_name " +
                        "FROM gitara g " +
                        "LEFT JOIN compania c ON g.compania = c.id",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                long gitaraId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                String model = cursor.getString(cursor.getColumnIndexOrThrow("model"));
                String companiaName = cursor.getString(cursor.getColumnIndexOrThrow("compania_name"));

                guitarIds.add(gitaraId);

                // Формируем строку для отображения: "Model (Company)"
                if (companiaName != null && !companiaName.isEmpty()) {
                    guitarDisplayList.add(model + " (" + companiaName + ")");
                } else {
                    guitarDisplayList.add(model);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (guitarDisplayList.isEmpty()) {
            Toast.makeText(this, "ВНИМАНИЕ: В базе нет гитар!", Toast.LENGTH_LONG).show();
            guitarDisplayList.add("Нет доступных гитар");
            guitarIds.add(-1L);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, guitarDisplayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGitara.setAdapter(adapter);

        add.setOnClickListener(v -> {
            // ИСПРАВЛЕНИЕ 1: Проверка и получение ID пользователя
            app appInstance = (app) getApplicationContext();
            long userId = appInstance.idUserNow;

            // Проверка: если ID = 0, значит пользователь не авторизован
            if (userId == 0 || userId == -1) {
                Toast.makeText(this, "Ошибка: Пользователь не авторизован! ID=" + userId, Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this, "Текущий пользователь ID: " + userId, Toast.LENGTH_SHORT).show();

            // Генерация номера заказа
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(18);
            for (int i = 0; i < 18; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            String nomerZakaza = sb.toString();

            // Дата
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dataZakaza = sdf.format(new Date());

            // Получаем ID выбранной гитары
            int selectedPosition = spinnerGitara.getSelectedItemPosition();
            long gitaraId = guitarIds.get(selectedPosition);

            if (gitaraId == -1) {
                Toast.makeText(this, "Выберите гитару из списка!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Вставка в базу (теперь используем ID гитары, а не название)
            String query = "INSERT INTO zakazi (nomer_zakaza, data_zakaza, gitara, user) VALUES (?, ?, ?, ?)";
            database.execSQL(query, new Object[]{nomerZakaza, dataZakaza, gitaraId, userId});

            Toast.makeText(this, "Заказ добавлен!\nНомер: " + nomerZakaza, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, glavnoe_menu_activiti.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}