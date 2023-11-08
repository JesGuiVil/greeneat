package com.example.basedatos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModificarUsuarioActivity extends AppCompatActivity {

    EditText modificarIdEditText, nombreEditText, apellidosEditText, direccionEditText, telefonoEditText, correoEditText, contraseniaEditText, fechaAltaEditText, nifEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarusuario);

        dbHelper = new DbHelper(this);

        modificarIdEditText = findViewById(R.id.IDmodificarusuario);
        nombreEditText = findViewById(R.id.Nombremodificarusuario);
        apellidosEditText = findViewById(R.id.Apellidosmodificarusuario);
        direccionEditText = findViewById(R.id.Direccionmodificarusuario);
        telefonoEditText = findViewById(R.id.Telefonomodificarusuario);
        correoEditText = findViewById(R.id.Correomodificarusuario);
        contraseniaEditText = findViewById(R.id.Contraseniamodificarusuario);
        nifEditText = findViewById(R.id.NIFmodificarusuario);

        Button modificarButton = findViewById(R.id.Modificar);
        modificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String modificarId = modificarIdEditText.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String direccion = direccionEditText.getText().toString();
                String telefono = telefonoEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();
                String nif = nifEditText.getText().toString();

                if (modificarId.isEmpty()) {
                    Toast.makeText(ModificarUsuarioActivity.this, "Por favor, ingresa el ID del usuario a modificar", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    if (!nombre.isEmpty()) {
                        values.put("Nombre", nombre);
                    }
                    if (!apellidos.isEmpty()) {
                        values.put("Apellidos", apellidos);
                    }
                    if (!direccion.isEmpty()) {
                        values.put("Direccion", direccion);
                    }
                    if (!telefono.isEmpty()) {
                        values.put("Telefono", telefono);
                    }
                    if (!correo.isEmpty()) {
                        values.put("Correo_e", correo);
                    }
                    if (!contrasenia.isEmpty()) {
                        values.put("Contrasenia", contrasenia);
                    }
                    if (!nif.isEmpty()) {
                        values.put("NIF", nif);
                    }
                    String selection = "id = ?";
                    String[] selectionArgs = { modificarId };

                    int rowsUpdated = db.update("Usuarios", values, selection, selectionArgs);

                    if (rowsUpdated > 0) {
                        Toast.makeText(ModificarUsuarioActivity.this, "Usuario modificado correctamente", Toast.LENGTH_SHORT).show();
                        modificarIdEditText.setText("");
                        nombreEditText.setText("");
                        apellidosEditText.setText("");
                        direccionEditText.setText("");
                        telefonoEditText.setText("");
                        correoEditText.setText("");
                        contraseniaEditText.setText("");
                        nifEditText.setText("");
                    } else {
                        Toast.makeText(ModificarUsuarioActivity.this, "Error al modificar el usuario", Toast.LENGTH_SHORT).show();
                    }

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
