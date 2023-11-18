package com.example.basedatos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AnadirActivity extends AppCompatActivity {

    EditText nombreEditText, categoriaEditText, precioEditText, ivaEditText, pesoEditText, stockEditText, descripcionEditText, idProveedor;
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
        categoriaEditText = findViewById(R.id.Seccion);
        precioEditText = findViewById(R.id.Precio);
        ivaEditText = findViewById(R.id.IVA);
        pesoEditText = findViewById(R.id.Peso);
        stockEditText = findViewById(R.id.Stock);
        descripcionEditText=findViewById(R.id.Descripcion);
        imagenProducto = findViewById(R.id.ImagenProducto);
        idProveedor = findViewById(R.id.idProveedor);

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
//ju
        // Botón para enviar los datos del producto
        Button enviarButton = findViewById(R.id.Enviar);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de los EditText
                String nombre = nombreEditText.getText().toString();
                String categoria = categoriaEditText.getText().toString();
                double precio = Double.parseDouble(precioEditText.getText().toString());
                double iva = Double.parseDouble(ivaEditText.getText().toString());
                double peso = Double.parseDouble(pesoEditText.getText().toString());
                int stock = Integer.parseInt(stockEditText.getText().toString());
                String descripcion = descripcionEditText.getText().toString();

                // Validar que los campos no estén vacíos
                if (nombre.isEmpty() || categoria.isEmpty()) {
                    Toast.makeText(AnadirActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Obtener una instancia de SQLiteDatabase
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        // Crear un objeto ContentValues para insertar datos
                        ContentValues values = new ContentValues();
                        values.put("Nombre", nombre);
                        values.put("Categoria", categoria);
                        values.put("Precio", precio);
                        values.put("IVA", iva);
                        values.put("Peso", peso);
                        values.put("Stock", stock);
                        values.put("Descripcion", descripcion);
                        values.put("ID_Proveedor", String.valueOf(idProveedor));

                        // Si se seleccionó una imagen, redimensionarla antes de guardarla en la base de datos
                        if (imagenUri != null) {
                            Bitmap imagenRedimensionada = redimensionarImagen(imagenUri, 400, 400);

                            // Verificar si la redimensión fue exitosa
                            if (imagenRedimensionada != null) {
                                // Guardar la imagen redimensionada como BLOB en la base de datos
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                imagenRedimensionada.compress(Bitmap.CompressFormat.PNG, 0, baos);
                                byte[] imagenEnBytes = baos.toByteArray();

                                // Guardar la imagen como BLOB en la base de datos
                                values.put("Imagen", imagenEnBytes);
                            } else {
                                // Mostrar un mensaje de error si la redimensión falla
                                Toast.makeText(AnadirActivity.this, "Error al redimensionar la imagen", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        // Insertar los datos en la base de datos
                        long newRowId = db.insert("Productos", null, values);

                        // Verificar si la inserción fue exitosa
                        if (newRowId != -1) {
                            // Muestra un mensaje de éxito
                            Toast.makeText(AnadirActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();

                            // Limpia los campos y la imagen
                            nombreEditText.setText("");
                            categoriaEditText.setText("");
                            precioEditText.setText("");
                            ivaEditText.setText("");
                            pesoEditText.setText("");
                            stockEditText.setText("");
                            descripcionEditText.setText("");
                            idProveedor.setText("");
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
    private Bitmap redimensionarImagen(Uri uri, int nuevoAncho, int nuevoAlto) {
        try {
            // Obtener la imagen original
            Bitmap bitmapOriginal = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            // Calcular las nuevas dimensiones proporcionales
            float proporcionAncho = ((float) nuevoAncho) / bitmapOriginal.getWidth();
            float proporcionAlto = ((float) nuevoAlto) / bitmapOriginal.getHeight();
            float proporcionMasPequena = Math.min(proporcionAncho, proporcionAlto);

            // Aplicar la redimensión
            int anchoRedimensionado = Math.round(proporcionMasPequena * bitmapOriginal.getWidth());
            int altoRedimensionado = Math.round(proporcionMasPequena * bitmapOriginal.getHeight());

            return Bitmap.createScaledBitmap(bitmapOriginal, anchoRedimensionado, altoRedimensionado, true);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al redimensionar la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
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
