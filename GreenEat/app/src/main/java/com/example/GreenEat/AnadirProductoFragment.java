package com.example.GreenEat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AnadirProductoFragment extends Fragment {

    private EditText nombreEditText, categoriaEditText, precioEditText, ivaEditText, pesoEditText, stockEditText, descripcionEditText, idProveedor,enOfertaEditText;
    private ImageView imagenProducto;
    private Uri imagenUri;
    private DbHelper dbHelper;

    public static AnadirProductoFragment newInstance() {
        return new AnadirProductoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anadir_producto, container, false);

        dbHelper = new DbHelper(requireContext());

        // Initialize views
        nombreEditText = view.findViewById(R.id.Nombre);
        categoriaEditText = view.findViewById(R.id.Seccion);
        precioEditText = view.findViewById(R.id.Precio);
        ivaEditText = view.findViewById(R.id.IVA);
        pesoEditText = view.findViewById(R.id.Peso);
        stockEditText = view.findViewById(R.id.Stock);
        descripcionEditText = view.findViewById(R.id.Descripcion);
        imagenProducto = view.findViewById(R.id.ImagenProducto);
        idProveedor = view.findViewById(R.id.idProveedor);
        enOfertaEditText = view.findViewById(R.id.enOferta);
        // Button to add a new product
        Button enviarButton = view.findViewById(R.id.Enviar);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic to add a new product to the database
                addProductToDatabase();
            }
        });

        // Button to select an image
        Button seleccionarImagenButton = view.findViewById(R.id.SeleccionarImagen);
        seleccionarImagenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }
    private void addProductToDatabase() {
        String nombre = nombreEditText.getText().toString();
        String categoria = categoriaEditText.getText().toString();
        double precio = Double.parseDouble(precioEditText.getText().toString());
        double iva = Double.parseDouble(ivaEditText.getText().toString());
        double peso = Double.parseDouble(pesoEditText.getText().toString());
        int stock = Integer.parseInt(stockEditText.getText().toString());
        String descripcion = descripcionEditText.getText().toString();
        int idproveedor = Integer.parseInt(idProveedor.getText().toString());
        int enoferta = Integer.parseInt(enOfertaEditText.getText().toString());

        if (nombre.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            try {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Nombre", nombre);
                values.put("Categoria", categoria);
                values.put("Precio", precio);
                values.put("IVA", iva);
                values.put("Peso", peso);
                values.put("Stock", stock);
                values.put("Descripcion", descripcion);
                values.put("ID_Proveedor", idproveedor);
                values.put("EnOferta", enoferta);
                values.put("Imagen", imagenUri.toString()); // Almacena la ruta de la imagen

                long newRowId = db.insert("Productos", null, values);

                if (newRowId != -1) {
                    Toast.makeText(requireContext(), "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                    // Clear the fields and the image
                    nombreEditText.setText("");
                    categoriaEditText.setText("");
                    precioEditText.setText("");
                    ivaEditText.setText("");
                    pesoEditText.setText("");
                    stockEditText.setText("");
                    descripcionEditText.setText("");
                    idProveedor.setText("");
                    enOfertaEditText.setText("");
                    imagenProducto.setImageURI(null);
                } else {
                    Toast.makeText(requireContext(), "Error al agregar el producto", Toast.LENGTH_SHORT).show();
                }

                db.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            imagenUri = data.getData();
            imagenProducto.setImageURI(imagenUri);
        }
    }
}
