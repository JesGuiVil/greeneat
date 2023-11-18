package com.example.basedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MostrarActivity extends AppCompatActivity {

    TextView resultadosTextView;
    LinearLayout linearLayoutContenido;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        dbHelper = new DbHelper(this);
        resultadosTextView = findViewById(R.id.elimi);
        linearLayoutContenido = findViewById(R.id.linearLayoutContenido);

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
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Productos", null, null, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    String categoria = cursor.getString(cursor.getColumnIndexOrThrow("Categoria"));  // Cambiado de "Seccion" a "Categoria"
                    double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("Precio"));
                    double iva = cursor.getDouble(cursor.getColumnIndexOrThrow("IVA"));
                    double peso = cursor.getDouble(cursor.getColumnIndexOrThrow("Peso"));
                    int stock = cursor.getInt(cursor.getColumnIndexOrThrow("Stock"));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"));
                    int idProveedor = cursor.getInt(cursor.getColumnIndexOrThrow("ID_Proveedor"));
                    // Obtener la imagen como array de bytes desde el cursor
                    byte[] imagenEnBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("Imagen"));

                    // Convertir el array de bytes a un Bitmap
                    Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagenEnBytes, 0, imagenEnBytes.length);

                    // Crea un nuevo ImageView y configura la carga de la imagen con el Bitmap
                    ImageView imageViewProducto = new ImageView(this);
                    imageViewProducto.setLayoutParams(new LinearLayout.LayoutParams(
                            100, // ancho en píxeles
                            100  // altura en píxeles
                    ));

                    // Establecer el Bitmap en el ImageView
                    imageViewProducto.setImageBitmap(imagenBitmap);

                    // Crea un nuevo TextView y configura el nombre del producto
                    TextView textViewNombre = new TextView(this);
                    textViewNombre.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    ));
                    textViewNombre.setText(nombre);
                    textViewNombre.setTextSize(20);

                    // Crea un contenedor para ImageView y TextView
                    LinearLayout itemLayout = new LinearLayout(this);
                    itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                    itemLayout.addView(imageViewProducto);
                    itemLayout.addView(textViewNombre);

                    // Agrega el contenedor al LinearLayout principal
                    linearLayoutContenido.addView(itemLayout);

                    // Resto del código...
                    // Puedes continuar aquí con la construcción de la cadena result y mostrar los demás datos

                } while (cursor.moveToNext());
            } else {
                resultadosTextView.setText("No se encontraron registros.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultadosTextView.setText("Error al obtener registros.");
        }
    }
}