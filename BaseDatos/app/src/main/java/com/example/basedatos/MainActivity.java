package com.example.basedatos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText correoEditText, contraseniaEditText;
    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView Registro = (TextView) findViewById(R.id.Registro);
        Button iniciarsesion = (Button) findViewById(R.id.iniciarsesion);
        dbHelper = new DbHelper(this);
        correoEditText = findViewById(R.id.correo_elogin);
        contraseniaEditText = findViewById(R.id.Contrasenialogin);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        // Verificar si hay un ID de usuario almacenado en las preferencias compartidas
        long idUsuarioAlmacenado = prefs.getLong("idUsuario", -1);

        if (idUsuarioAlmacenado != -1) {
            // Si hay un ID de usuario almacenado, dirigir al usuario directamente a la actividad correspondiente
            int admin = prefs.getInt("admin", -1);

            if (admin == 1) {
                irAAdminActivity(idUsuarioAlmacenado, 1);
            } else if (admin == 0) {
                irAClienteActivity(idUsuarioAlmacenado, 0);
            }
        }

        File archivo=new File("/data/data/com.example.basedatos/databases/ejemplo.db");
        if (archivo.exists()){
            Toast.makeText(MainActivity.this, "CONECTADO A LA BASE DE DATOS", Toast.LENGTH_LONG).show();
        }else{
            DbHelper dbhelper = new DbHelper(MainActivity.this);
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            if (db!=null){
                Toast.makeText(MainActivity.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this, "ERROR EN BASE DE DATOS", Toast.LENGTH_LONG).show();
            }
        }

        Registro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbHelper.getReadableDatabase();
                String correo = correoEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();

                long idUsuario = obtenerIdUsuario(correo); // Obten el ID del usuario
                int admin = validarUsuario(correo, contrasenia);

                if (admin == 1) {
                    irAAdminActivity(idUsuario, 1);
                } else if (admin == 0) {
                    irAClienteActivity(idUsuario, 0);
                }
            }
        });

    }private int validarUsuario(String correo, String contrasenia) {
        Cursor cursor = db.rawQuery("SELECT * FROM Usuarios WHERE Correo_e = ? AND Contrasenia = ?",
                new String[]{correo, contrasenia});

        if (cursor != null && cursor.moveToFirst()) {
            int adminColumnIndex = cursor.getColumnIndex("Admin");

            if (adminColumnIndex != -1) {
                int admin = cursor.getInt(adminColumnIndex);
                cursor.close();
                mostrarMensajeDeInicioDeSesion(admin);
                return admin;
            } else {
                cursor.close();
            }
        } else {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1; // Valor predeterminado si no se encuentra el usuario
    }

    private void mostrarMensajeDeInicioDeSesion(int admin) {
        if (admin == 1) {
            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso (administrador)", Toast.LENGTH_SHORT).show();
        } else if (admin == 0) {
            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }

    }

    private long obtenerIdUsuario(String correo) {
        long idUsuario = -1;

        Cursor cursor = db.rawQuery("SELECT id FROM Usuarios WHERE Correo_e = ?",
                new String[]{correo});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                idUsuario = cursor.getLong(0);
            }
            cursor.close();
        }

        return idUsuario;
    }

    private void irAAdminActivity(long idUsuario, int admin) {
        // Guardar información del usuario en preferencias compartidas con admin = 1 para administradores
        guardarDatosUsuario(idUsuario, admin);

        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
        finish();
    }

    private void irAClienteActivity(long idUsuario, int admin) {
        // Guardar información del usuario en preferencias compartidas con admin = 0 para clientes
        guardarDatosUsuario(idUsuario, admin);

        Intent intent = new Intent(MainActivity.this, ClienteActivity.class);
        startActivity(intent);
        finish();
    }
    private void guardarDatosUsuario(long idUsuario, int admin) {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Guardar el ID de usuario
        editor.putLong("idUsuario", idUsuario);
        editor.putInt("admin", admin);

        // Guardar cualquier otra información que desees recordar

        editor.apply();
    }
}