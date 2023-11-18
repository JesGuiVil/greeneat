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

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button btn = (Button) findViewById(R.id.Crear);
        Button btnborrar = (Button) findViewById(R.id.borrar);
        Button btnanadir = (Button) findViewById(R.id.Anadir);
        Button btnmodificar = (Button) findViewById(R.id.Modificar);
        Button btnlogin = (Button) findViewById(R.id.Login);
        Button btneliminar = (Button) findViewById(R.id.Eliminar);
        Button mostrarProductosButton = findViewById(R.id.mostrarProductos);
        Button btnsalir = findViewById(R.id.salir);
        File archivo=new File("/data/data/com.example.basedatos/databases/ejemplo.db");
        if (archivo.exists()){
            btn.setEnabled(false);
            btn.setText("conectado a la base de datos");
            btnborrar.setEnabled(true);
            btnanadir.setEnabled(true);
            btnmodificar.setEnabled(true);
            btneliminar.setEnabled(true);
            mostrarProductosButton.setEnabled(true);
        }else{
            btn.setEnabled(true);
            btnborrar.setEnabled(false);
            btn.setText("crear base de datos");
            mostrarProductosButton.setEnabled(false);
            btnanadir.setEnabled(false);
            btnmodificar.setEnabled(false);
            btneliminar.setEnabled(false);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DbHelper dbhelper = new DbHelper(AdminActivity.this);
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                if (db!=null){
                    Toast.makeText(AdminActivity.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AdminActivity.this, "ERROR EN BASE DE DATOS", Toast.LENGTH_LONG).show();
                }
                if (archivo.exists()){
                    btn.setEnabled(false);
                    btn.setText("conectado a la base de datos");
                    btnborrar.setEnabled(true);
                    btnanadir.setEnabled(true);
                    btnmodificar.setEnabled(true);
                    btneliminar.setEnabled(true);
                    mostrarProductosButton.setEnabled(true);
                }else{
                    btn.setEnabled(true);
                    btnborrar.setEnabled(false);
                    btn.setText("crear base de datos");
                    mostrarProductosButton.setEnabled(false);
                    btnanadir.setEnabled(false);
                    btnmodificar.setEnabled(false);
                    btneliminar.setEnabled(false);
                }
            }
        });
        btnanadir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AnadirActivity.class);
                startActivity(intent);
            }
        });




        btnmodificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ModificarActivity.class);
                startActivity(intent);
            }
        });


        btneliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EliminarActivity.class);
                startActivity(intent);
            }
        });

        mostrarProductosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, MostrarActivity.class);
                startActivity(intent);

            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnborrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbhelper = new DbHelper(AdminActivity.this);
                SQLiteDatabase db = dbhelper.getWritableDatabase();

                if (db != null) {
                    // Muestra un cuadro de diálogo de confirmación
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage("¿Estás seguro de que deseas borrar la base de datos?");
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Cierra la base de datos antes de eliminarla
                            db.close();
                            if (deleteDatabase("ejemplo.db")) {
                                Toast.makeText(AdminActivity.this, "BASE DE DATOS BORRADA", Toast.LENGTH_LONG).show();
                                // Deshabilita el botón después de borrar la base de datos
                                btn.setEnabled(true);
                                btn.setText("Crear base de datos");
                            } else {
                                Toast.makeText(AdminActivity.this, "ERROR AL BORRAR BASE DE DATOS", Toast.LENGTH_LONG).show();
                            }

                            // Luego de borrar la base de datos, realiza la comprobación de existencia del archivo
                            if (archivo.exists()) {
                                btn.setEnabled(false);
                                btn.setText("Conectado a la base de datos");
                                btnborrar.setEnabled(true);
                                btnanadir.setEnabled(true);
                                btnmodificar.setEnabled(true);
                                btneliminar.setEnabled(true);
                                mostrarProductosButton.setEnabled(true);
                            } else {
                                btn.setEnabled(true);
                                btnborrar.setEnabled(false);
                                btn.setText("Crear base de datos");
                                btnanadir.setEnabled(false);
                                btnmodificar.setEnabled(false);
                                btneliminar.setEnabled(false);
                                mostrarProductosButton.setEnabled(false);
                            }
                        }
                    });
                    builder.setNegativeButton("No", null);
                    builder.show();
                } else {
                    Toast.makeText(AdminActivity.this, "NO HAY BASE DE DATOS PARA BORRAR", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnsalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}