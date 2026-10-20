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
        setContentView(R.layout.voiti);

        editLogin = findViewById(R.id.etLogin);
        editPassword = findViewById(R.id.etPassword);
        Button buttonLogin = findViewById(R.id.btnLogin);

        DBHelper dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        buttonLogin.setOnClickListener(v -> {
            if (editLogin.getText().toString().equals("") || editPassword.getText().toString().equals("")) {
                showMessage(3); // <-- Исправлено с showDialog на showMessage
            } else {
// 1. В запросе замените idUser на точное имя из DBeaver (например, id)
                String sql = "SELECT id, login, parol FROM user WHERE login = ? AND parol = ?";

                String[] selectionArgs = new String[] {
                        editLogin.getText().toString().trim(),
                        editPassword.getText().toString().trim()
                };

                Cursor cursor = database.rawQuery(sql, selectionArgs);

                try {
                    if (cursor.getCount() != 0 && cursor.moveToFirst()) {

                        // 2. ЗДЕСЬ ТОЖЕ замените "id" на точное имя столбца из DBeaver!
                        long userId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));

                        // Сохраняем ID в глобальный класс
                        app appInstance = (app) getApplicationContext();
                        appInstance.idUserNow = userId;

                        Toast.makeText(this, "Успешно! Ваш ID: " + userId, Toast.LENGTH_SHORT).show();

                        // Переход в меню
                        Intent intent1 = new Intent(this, glavnoe_menu_activiti.class);
                        intent1.putExtra("logged_user", editLogin.getText().toString().trim());
                        startActivity(intent1);
                        finish();

                    } else {
                        showMessage(2); // Неверный логин или пароль
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            }
        });
    }

    // Метод для показа диалогов (был showMessage, а вызывали showDialog)
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