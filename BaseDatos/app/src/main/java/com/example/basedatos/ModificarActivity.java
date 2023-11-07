package com.example.basedatos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModificarActivity extends AppCompatActivity {

    EditText modificarIdEditText, nombreEditText, descripcionEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        dbHelper = new DbHelper(this);

        modificarIdEditText = findViewById(R.id.ModificarID);
        nombreEditText = findViewById(R.id.Nombre);
        descripcionEditText = findViewById(R.id.Descripcion);

        Button modificarButton = findViewById(R.id.Modificar);
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de los EditText
                String modificarId = modificarIdEditText.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String descripcion = descripcionEditText.getText().toString();
//hola ardi2
                // Validar que el campo "modificarId" no esté vacío
                if (modificarId.isEmpty()) {
                    Toast.makeText(ModificarActivity.this, "Por favor, ingresa el ID del producto a modificar", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtener una instancia de SQLiteDatabase
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Crear un objeto ContentValues para actualizar datos
                    ContentValues values = new ContentValues();

                    // Solo actualiza el campo "Nombre" si no está vacío
                    if (!nombre.isEmpty()) {
                        values.put("Nombre", nombre);
                    }

                    // Solo actualiza el campo "Descripcion" si no está vacío
                    if (!descripcion.isEmpty()) {
                        values.put("Descripcion", descripcion);
                    }

                    // Definir la cláusula WHERE para la actualización
                    String selection = "id = ?";
                    String[] selectionArgs = { modificarId };

                    // Realizar la actualización de los datos
                    int rowsUpdated = db.update("Productos", values, selection, selectionArgs);

                    // Verificar si la actualización fue exitosa
                    if (rowsUpdated > 0) {
                        // Muestra un mensaje de éxito
                        Toast.makeText(ModificarActivity.this, "Producto modificado correctamente", Toast.LENGTH_SHORT).show();

                        // Limpia los campos después de modificar el producto
                        modificarIdEditText.setText("");
                        nombreEditText.setText("");
                        descripcionEditText.setText("");
                    } else {
                        Toast.makeText(ModificarActivity.this, "Error al modificar el producto", Toast.LENGTH_SHORT).show();
                    }

                    // Cierra la base de datos
                    db.close();
                }
            }
        });


        Button volverButton = findViewById(R.id.Volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

