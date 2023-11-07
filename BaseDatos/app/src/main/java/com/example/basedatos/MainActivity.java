package com.example.basedatos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnlogin = (Button) findViewById(R.id.Login);
        Button btnRegistro = (Button) findViewById(R.id.Registro);
        Button btnsalir = findViewById(R.id.salir);
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

        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}