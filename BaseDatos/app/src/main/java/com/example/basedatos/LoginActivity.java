package com.example.basedatos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText correoEditText, contraseniaEditText;
    Button enviarButton, volverButton;
    VideoView video_view;

    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        video_view = findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.comidaecologica;
        Uri uri = Uri.parse(path);
        video_view.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        video_view.setMediaController(mediaController);
        video_view.start();

        dbHelper = new DbHelper(this);
        correoEditText = findViewById(R.id.correo_elogin);
        contraseniaEditText = findViewById(R.id.Contrasenialogin);
        enviarButton = findViewById(R.id.Enviar);
        volverButton = findViewById(R.id.Volver);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbHelper.getReadableDatabase();
                String correo = correoEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();

                int loginStatus = validarUsuario(correo, contrasenia);
                long idUsuario = obtenerIdUsuario(correo); // Obten el ID del usuario

                mostrarMensajeDeInicioDeSesion(loginStatus, idUsuario);
            }
        });

        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private int validarUsuario(String correo, String contrasenia) {
        Cursor cursor = db.rawQuery("SELECT * FROM Usuarios WHERE Correo_e = ? AND Contrasenia = ?",
                new String[]{correo, contrasenia});

        if (cursor != null && cursor.moveToFirst()) {
            int adminColumnIndex = cursor.getColumnIndex("Admin");
            int idColumnIndex = cursor.getColumnIndex("id");

            if (adminColumnIndex != -1 && idColumnIndex != -1) {
                int admin = cursor.getInt(adminColumnIndex);
                long idUsuario = cursor.getLong(idColumnIndex);

                cursor.close();
                //mostrarMensajeDeInicioDeSesion(admin, idUsuario); // Pasa ambos argumentos
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


    private void mostrarMensajeDeInicioDeSesion(int admin, long idUsuario) {
        if (admin == 1) {
            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso (administrador)", Toast.LENGTH_SHORT).show();
        } else if (admin == 0) {
            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }

        if (admin == 1) {
            irAAdminActivity(idUsuario);
        } else if (admin == 0) {
            irAClienteActivity(idUsuario);
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

    private void irAAdminActivity(long idUsuario) {
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
        finish();
    }

    private void irAClienteActivity(long idUsuario) {
        Intent intent = new Intent(LoginActivity.this, ClienteActivity.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
        finish();
    }
}



