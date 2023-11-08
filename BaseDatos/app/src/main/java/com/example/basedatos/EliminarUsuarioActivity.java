package com.example.basedatos;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EliminarUsuarioActivity extends AppCompatActivity {

    EditText idEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminarusuario);

        dbHelper = new DbHelper(this);
        idEditText = findViewById(R.id.IDusuario);

        Button eliminarButton = findViewById(R.id.eliminar);
        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el valor del EditText (ID)
                String id = idEditText.getText().toString();

                // Validar que el campo no esté vacío
                if (id.isEmpty()) {
                    Toast.makeText(EliminarUsuarioActivity.this, "Por favor, ingresa un ID válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Obtener una instancia de SQLiteDatabase
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Definir la cláusula WHERE para la eliminación
                    String selection = "id = ?";
                    String[] selectionArgs = { id };

                    // Realizar la eliminación de los datos
                    int rowsDeleted = db.delete("Usuarios", selection, selectionArgs);

                    // Verificar si la eliminación fue exitosa
                    if (rowsDeleted > 0) {
                        // Muestra un mensaje de éxito
                        Toast.makeText(EliminarUsuarioActivity.this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();

                        // Limpia el campo después de eliminar el usuario
                        idEditText.setText("");
                    } else {
                        Toast.makeText(EliminarUsuarioActivity.this, "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
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
