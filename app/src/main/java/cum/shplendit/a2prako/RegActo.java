package cum.shplendit.a2prako;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class RegActo extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    // Поля ввода (замените ID на свои из reg.xml)
    private EditText editTextName;
    private EditText editTextLogin;
    private EditText editTextPassword;
    //private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);

        // Инициализация полей (замените ID на свои)
        editTextName = findViewById(R.id.etRegName);
        editTextLogin = findViewById(R.id.etRegLogin);
        editTextPassword = findViewById(R.id.etRegPassword);
        //editTextEmail = findViewById(R.id.etRegEmail);

        Button btnRegister = findViewById(R.id.btnRegister);

        // Инициализация БД
        dbHelper = new DBHelper(this);

        try {
            dbHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        btnRegister.setOnClickListener(v -> {
            // Получаем данные из полей
            String imya = editTextName.getText().toString().trim();
            //String email = editTextEmail.getText().toString().trim();
            String login = editTextLogin.getText().toString().trim();
            String parol = editTextPassword.getText().toString().trim();

            // Проверка на пустые поля
            if (imya.isEmpty() || login.isEmpty() || parol.isEmpty()) {
                Toast.makeText(RegActo.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Подготовка данных для вставки
            ContentValues cv = new ContentValues();
            cv.putNull("familia");      // Пустая фамилия
            cv.put("imya", imya);        // Имя
            cv.putNull("otchestvo");     // Пустое отчество
            cv.put("login", login);      // Логин
            cv.put("parol", parol);      // Пароль

            // Вставка в БД
            long newRowId = database.insert("user", null, cv);

            if (newRowId == -1) {
                Toast.makeText(RegActo.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegActo.this, "Регистрация успешна! ID: " + newRowId, Toast.LENGTH_SHORT).show();

                // Переход на главный экран с передачей данных
                Intent intent = new Intent(this, glavnoe_menu_activiti.class);
                intent.putExtra("name", imya);
                intent.putExtra("login", login);
                intent.putExtra("password", parol);
                intent.putExtra("userId", newRowId);
                startActivity(intent);
                finish(); // Закрыть текущую активность
            }
        });
    }
}