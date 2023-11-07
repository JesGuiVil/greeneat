package com.example.basedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class MostrarActivity extends AppCompatActivity {

    TextView resultadosTextView;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        dbHelper = new DbHelper(this);
        resultadosTextView = findViewById(R.id.resultadosTextView);
        mostrarRegistros();

        Button volverButton = findViewById(R.id.Volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mostrarRegistros() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"id", "Nombre", "Descripcion"};

        Cursor cursor = db.query("Productos", projection, null, null, null, null, null);

        StringBuilder result = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"));

                result.append("ID: ").append(id).append("\n");
                result.append("Nombre: ").append(nombre).append("\n");
                result.append("Descripción: ").append(descripcion).append("\n\n");
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            result.append("No se encontraron registros.");
        }

        resultadosTextView.setText(result.toString());

        db.close();
    }
}

