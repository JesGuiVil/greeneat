package com.example.basedatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatosPersonalesActivity extends AppCompatActivity {

    EditText nombreEditText, apellidosEditText, direccionEditText, telefonoEditText, correoEditText, contraseniaEditText, nifEditText;
    DbHelper dbHelper;
    long idUsuario; // ID del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datospersonales);

        dbHelper = new DbHelper(this);
        idUsuario = getIntent().getLongExtra("idUsuario", -1); // Obtiene el ID del usuario del intent

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
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String direccion = direccionEditText.getText().toString();
                String telefono = telefonoEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String contrasenia = contraseniaEditText.getText().toString();
                String nif = nifEditText.getText().toString();

                if (idUsuario == -1) {
                    Toast.makeText(DatosPersonalesActivity.this, "Error: ID de usuario no válido", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

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
                String[] selectionArgs = { String.valueOf(idUsuario) };

                int rowsUpdated = db.update("Usuarios", values, selection, selectionArgs);

                if (rowsUpdated > 0) {
                    Toast.makeText(DatosPersonalesActivity.this, "Datos personales modificados correctamente", Toast.LENGTH_SHORT).show();
                    // Puedes redirigir al usuario a otra actividad o realizar otras acciones aquí
                } else {
                    Toast.makeText(DatosPersonalesActivity.this, "Error al modificar los datos personales", Toast.LENGTH_SHORT).show();
                }

                db.close();
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
