package com.example.basedatos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class AnadirActivity extends AppCompatActivity {

    EditText nombreEditText, seccionEditText, precioEditText, ivaEditText, pesoEditText, stockEditText, descripcionEditText;
    ImageView imagenProducto;
    Uri imagenUri;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);

        dbHelper = new DbHelper(this);

        // Obtener referencias a los EditText e ImageView
        nombreEditText = findViewById(R.id.Nombre);
        seccionEditText = findViewById(R.id.Seccion);
        precioEditText = findViewById(R.id.Precio);
        ivaEditText = findViewById(R.id.IVA);
        pesoEditText = findViewById(R.id.Peso);
        stockEditText = findViewById(R.id.Stock);
        descripcionEditText=findViewById(R.id.Descripcion);
        imagenProducto = findViewById(R.id.ImagenProducto);

        // Botón para seleccionar imagen
        Button seleccionarImagenButton = findViewById(R.id.SeleccionarImagen);
        seleccionarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una actividad para seleccionar una imagen de la galería
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        // Botón para enviar los datos del producto
        Button enviarButton = findViewById(R.id.Enviar);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de los EditText
                String nombre = nombreEditText.getText().toString();
                String seccion = seccionEditText.getText().toString();
                double precio = Double.parseDouble(precioEditText.getText().toString());
                double iva = Double.parseDouble(ivaEditText.getText().toString());
                double peso = Double.parseDouble(pesoEditText.getText().toString());
                int stock = Integer.parseInt(stockEditText.getText().toString());
                String descripcion = descripcionEditText.getText().toString();

                // Validar que los campos no estén vacíos
                if (nombre.isEmpty() || seccion.isEmpty()) {
                    Toast.makeText(AnadirActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Obtener una instancia de SQLiteDatabase
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        // Crear un objeto ContentValues para insertar datos
                        ContentValues values = new ContentValues();
                        values.put("Nombre", nombre);
                        values.put("Seccion", seccion);
                        values.put("Precio", precio);
                        values.put("IVA", iva);
                        values.put("Peso", peso);
                        values.put("Stock", stock);
                        values.put("Descripcion", descripcion);

                        // Si se seleccionó una imagen, guardar la ruta en la base de datos
                        if (imagenUri != null) {
                            values.put("Imagen", imagenUri.toString());
                        }

                        // Insertar los datos en la base de datos
                        long newRowId = db.insert("Productos", null, values);

                        // Verificar si la inserción fue exitosa
                        if (newRowId != -1) {
                            // Muestra un mensaje de éxito
                            Toast.makeText(AnadirActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                            // Limpia los campos y la imagen
                            nombreEditText.setText("");
                            seccionEditText.setText("");
                            precioEditText.setText("");
                            ivaEditText.setText("");
                            pesoEditText.setText("");
                            stockEditText.setText("");
                            descripcionEditText.setText("");
                            imagenProducto.setImageURI(null);
                        } else {
                            Toast.makeText(AnadirActivity.this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
                        }

                        // Cierra la base de datos
                        db.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AnadirActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Botón para volver
        Button volverButton = findViewById(R.id.Volver);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Obtener la URI de la imagen seleccionada
            imagenUri = data.getData();

            // Mostrar la imagen seleccionada en el ImageView
            imagenProducto.setImageURI(imagenUri);
        }
    }
}
