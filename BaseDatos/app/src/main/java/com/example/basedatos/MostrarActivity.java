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
        resultadosTextView = findViewById(R.id.elimi);
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
        StringBuilder result = new StringBuilder();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Productos", null, null, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    String seccion = cursor.getString(cursor.getColumnIndexOrThrow("Seccion"));
                    double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("Precio"));
                    double iva = cursor.getDouble(cursor.getColumnIndexOrThrow("IVA"));
                    double peso = cursor.getDouble(cursor.getColumnIndexOrThrow("Peso"));
                    int stock = cursor.getInt(cursor.getColumnIndexOrThrow("Stock"));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"));
                    int idProveedor = cursor.getInt(cursor.getColumnIndexOrThrow("ID_Proveedor"));
                    String imagenPath = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"));

                    result.append("ID: ").append(id).append("\n");
                    result.append("Nombre: ").append(nombre).append("\n");
                    result.append("Sección: ").append(seccion).append("\n");
                    result.append("Precio: ").append(precio).append("\n");
                    result.append("IVA: ").append(iva).append("\n");
                    result.append("Peso: ").append(peso).append("\n");
                    result.append("Stock: ").append(stock).append("\n");
                    result.append("Descripción: ").append(descripcion).append("\n");
                    result.append("ID Proveedor: ").append(idProveedor).append("\n");
                    result.append("Imagen: ").append(imagenPath).append("\n\n");
                } while (cursor.moveToNext());
            } else {
                result.append("No se encontraron registros.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.append("Error al obtener registros.");
        }

        resultadosTextView.setText(result.toString());
    }
}
