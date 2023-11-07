package com.example.basedatos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AnadirProveedorActivity extends AppCompatActivity {

    EditText nombreEditText, apellidosEditText, direccionEditText, telefonoEditText, correoEditText, fechaAltaEditText, nifEditText;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadirproveedor);

        dbHelper = new DbHelper(this);

        nombreEditText = findViewById(R.id.Nombreproveedor);
        apellidosEditText = findViewById(R.id.Apellidosproveedor);
        direccionEditText = findViewById(R.id.Direccionproveedor);
        telefonoEditText = findViewById(R.id.Telefonoproveedor);
        correoEditText = findViewById(R.id.Correo_eproveedor);
        nifEditText = findViewById(R.id.NIFproveedor);

        Button enviarButton = findViewById(R.id.Enviar);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String direccion = direccionEditText.getText().toString();
                String telefonoStr = telefonoEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String fechaAlta = RegistroActivity.obtenerFechaActual();
                String nif = nifEditText.getText().toString();

                if (nombre.isEmpty() || apellidos.isEmpty() || direccion.isEmpty() || telefonoStr.isEmpty() || correo.isEmpty() || fechaAlta.isEmpty() || nif.isEmpty()) {
                    Toast.makeText(AnadirProveedorActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("Nombre", nombre);
                    values.put("Apellidos", apellidos);
                    values.put("Direccion", direccion);
                    values.put("Telefono", Integer.parseInt(telefonoStr));
                    values.put("Correo_e", correo);
                    values.put("Fecha_alta", fechaAlta);
                    values.put("NIF", nif);

                    long newRowId = db.insert("Proveedores", null, values);

                    if (newRowId != -1) {
                        Toast.makeText(AnadirProveedorActivity.this, "Proveedor agregado correctamente", Toast.LENGTH_SHORT).show();
                        // Limpia los campos
                        nombreEditText.setText("");
                        apellidosEditText.setText("");
                        direccionEditText.setText("");
                        telefonoEditText.setText("");
                        correoEditText.setText("");
                        nifEditText.setText("");
                    } else {
                        Toast.makeText(AnadirProveedorActivity.this, "Error al agregar el proveedor", Toast.LENGTH_SHORT).show();
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
