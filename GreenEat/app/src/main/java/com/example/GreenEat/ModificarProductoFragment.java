package com.example.GreenEat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ModificarProductoFragment extends Fragment {

    private EditText idProductoEditText, nombreEditText, categoriaEditText, precioEditText, ivaEditText, pesoEditText, stockEditText, descripcionEditText, idProveedorEditText, enOfertaEditText;
    private ImageView imagenProducto;
    private Uri imagenUri;
    private DbHelper dbHelper;

    public ModificarProductoFragment() {
        // Required empty public constructor
    }

    public static ModificarProductoFragment newInstance() {
        return new ModificarProductoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_producto, container, false);

        dbHelper = new DbHelper(requireContext());

        // Initialize views
        idProductoEditText = view.findViewById(R.id.idproducto);
        nombreEditText = view.findViewById(R.id.Nombre);
        categoriaEditText = view.findViewById(R.id.Seccion);
        precioEditText = view.findViewById(R.id.Precio);
        ivaEditText = view.findViewById(R.id.IVA);
        pesoEditText = view.findViewById(R.id.Peso);
        stockEditText = view.findViewById(R.id.Stock);
        descripcionEditText = view.findViewById(R.id.Descripcion);
        imagenProducto = view.findViewById(R.id.ImagenProducto);
        idProveedorEditText = view.findViewById(R.id.idProveedor);
        enOfertaEditText = view.findViewById(R.id.enOferta);

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

        // Button to update the product
        Button enviarButton = view.findViewById(R.id.Enviar);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic to update the product in the database
                modificarProductoEnBaseDeDatos();
            }
        });

        return view;
    }

    private void modificarProductoEnBaseDeDatos() {
        String idProducto = idProductoEditText.getText().toString();
        String nombre = nombreEditText.getText().toString();
        String categoria = categoriaEditText.getText().toString();
        String precioStr = precioEditText.getText().toString();
        String ivaStr = ivaEditText.getText().toString();
        String pesoStr = pesoEditText.getText().toString();
        String stockStr = stockEditText.getText().toString();
        String descripcion = descripcionEditText.getText().toString();
        String idProveedor = idProveedorEditText.getText().toString();
        String enOferta = enOfertaEditText.getText().toString();

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            if (!nombre.isEmpty()) values.put("Nombre", nombre);
            if (!categoria.isEmpty()) values.put("Categoria", categoria);
            if (!precioStr.isEmpty()) values.put("Precio", Double.parseDouble(precioStr));
            if (!ivaStr.isEmpty()) values.put("IVA", Double.parseDouble(ivaStr));
            if (!pesoStr.isEmpty()) values.put("Peso", Double.parseDouble(pesoStr));
            if (!stockStr.isEmpty()) values.put("Stock", Integer.parseInt(stockStr));
            if (!descripcion.isEmpty()) values.put("Descripcion", descripcion);
            if (!idProveedor.isEmpty()) values.put("ID_Proveedor", idProveedor);
            if (!enOferta.isEmpty()) values.put("EnOferta", Integer.parseInt(enOferta));
            if (imagenUri !=null) values.put("Imagen", imagenUri.toString());


            String[] whereArgs = {idProducto};
            int numRowsUpdated = db.update("Productos", values, "id=?", whereArgs);

            if (numRowsUpdated > 0) {
                Toast.makeText(requireContext(), "Producto modificado correctamente", Toast.LENGTH_SHORT).show();
                // Clear the fields and the image
                limpiarCampos();
            } else {
                Toast.makeText(requireContext(), "Error al modificar el producto", Toast.LENGTH_SHORT).show();
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void limpiarCampos() {
        idProductoEditText.setText("");
        nombreEditText.setText("");
        categoriaEditText.setText("");
        precioEditText.setText("");
        ivaEditText.setText("");
        pesoEditText.setText("");
        stockEditText.setText("");
        descripcionEditText.setText("");
        idProveedorEditText.setText("");
        imagenProducto.setImageURI(null);
    }
}
