package com.example.basedatos;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EliminarActivity extends AppCompatActivity {

    EditText idEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        dbHelper = new DbHelper(this);
        idEditText = findViewById(R.id.ID);

        Button eliminarButton = findViewById(R.id.eliminar);
        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el valor del EditText (ID)
                String id = idEditText.getText().toString();

                // Validar que el campo no esté vacío
                if (id.isEmpty()) {
                    Toast.makeText(EliminarActivity.this, "Por favor, ingresa un ID válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtener una instancia de SQLiteDatabase
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Definir la cláusula WHERE para la eliminación
                    String selection = "id = ?";
                    String[] selectionArgs = { id };

                    // Realizar la eliminación de los datos
                    int rowsDeleted = db.delete("Productos", selection, selectionArgs);

                    // Verificar si la eliminación fue exitosa
                    if (rowsDeleted > 0) {
                        // Muestra un mensaje de éxito
                        Toast.makeText(EliminarActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();

                        // Limpia el campo después de eliminar el producto
                        idEditText.setText("");
                    } else {
                        Toast.makeText(EliminarActivity.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
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
