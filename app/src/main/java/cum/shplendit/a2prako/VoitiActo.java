package cum.shplendit.a2prako;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VoitiActo extends AppCompatActivity {

    private EditText editLogin;
    private EditText editPassword;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voiti); // укажите ваш layout

        // Инициализация элементов
        editLogin = findViewById(R.id.etLogin); // замените на ваши ID
        editPassword = findViewById(R.id.etPassword);
        Button buttonLogin = findViewById(R.id.btnLogin);

        // Инициализация базы данных
        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        // Обработчик кнопки входа
        buttonLogin.setOnClickListener(v -> {
            if (editLogin.getText().toString().equals("") || editPassword.getText().toString().equals("")) {
                showDialog(3);
            } else {
                // ИСПОЛЬЗУЕМ параметризованный запрос для защиты от SQL-инъекций
                String sql = "SELECT login, parol FROM user WHERE login = ? AND parol = ?";

                String[] selectionArgs = new String[] {
                        editLogin.getText().toString(),
                        editPassword.getText().toString()
                };

                Cursor cursor = database.rawQuery(sql, selectionArgs);

                try {
                    if (cursor.getCount() != 0) {
                        showDialog(1);
                        Intent intent1 = new Intent(this, glavnoe_menu_activiti.class);

                        // Передача данных (если нужно)
                        intent1.putExtra("user_login", editLogin.getText().toString());

                        // Получаем данные пользователя
                        if (cursor.moveToFirst()) {
                            String userLogin = cursor.getString(cursor.getColumnIndexOrThrow("login"));
                            intent1.putExtra("logged_user", userLogin);
                        }

                        startActivity(intent1);
                        finish(); // закрыть текущую активность
                    } else {
                        showDialog(2);
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            }
        });
    }

    // Метод для показа диалогов
    private void showMessage(int type) {
        String message;
        switch (type) {
            case 1:
                message = "Успешный вход!";
                break;
            case 2:
                message = "Неверный логин или пароль!";
                break;
            case 3:
                message = "Заполните все поля!";
                break;
            default:
                message = "Ошибка!";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}