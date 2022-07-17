package com.example.partyfavors2.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.partyfavors2.database.DataBase;
import com.example.partyfavors2.R;
import com.example.partyfavors2.utilidades.Utilidades;

public class LogIn extends AppCompatActivity {

    //creación de variables

    EditText editUsuario, editPassword;
    Button btnRegistrar, btnIngresar;
    String dbUsuario, dbContraseña, dbNombre, usuario, contraseña;
    int dbId;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Se inicializan las variables

        setContentView(R.layout.activity_log_in);
        editUsuario = findViewById(R.id.editUsuario);
        editPassword = findViewById(R.id.editPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // al clickear en el boton registar, cambia a la vista "sign in"

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnRegistrar = new Intent(LogIn.this, SingIn.class);
                startActivity(btnRegistrar);
            }
        });

        // Al clickear en el "boton ingresar", chequea que esten completados los campos de usuario
        // y password

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editUsuario.equals("") && editPassword.equals("")) {
                    Toast.makeText(LogIn.this, "Todos los campos deben estar completos.", Toast.LENGTH_SHORT).show();
                } else {
                    validarCredenciales();
                }
            }
        });
    }
         // Es un método para chequear si el usuario y la contraseña están en la base de datos

    public void validarCredenciales() {
        DataBase conexionSQLiteHelper = new DataBase(this, "db_partyfavors", null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();

        usuario = editUsuario.getText().toString();
        contraseña = editPassword.getText().toString();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIOS + " WHERE " + Utilidades.CAMPO_USUARIO + " = '" + usuario + "' AND " + Utilidades.CAMPO_CONTRASEÑA + " = '" + contraseña + "'", null);
        while (cursor.moveToNext()) {
            dbId = cursor.getInt(0);
            dbUsuario = cursor.getString(1);
            dbNombre = cursor.getString(2);
            dbContraseña = cursor.getString(4);

            if (usuario.equals(dbUsuario) && contraseña.equals(dbContraseña)) {
                Intent intent = new Intent(LogIn.this, Feed.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idUsuario", dbId);
                bundle.putString("nombreUsuario", dbUsuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrecto.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}