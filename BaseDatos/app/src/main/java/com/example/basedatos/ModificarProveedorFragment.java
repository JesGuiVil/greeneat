package com.example.basedatos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ModificarProveedorFragment extends Fragment {

    private EditText idEditText, nombreEditText, direccionEditText, telefonoEditText,  correoEditText, CIFEditText;

    private DbHelper dbHelper;

    public ModificarProveedorFragment() {
        // Required empty public constructor
    }

    public static ModificarProveedorFragment newInstance() {
        return new ModificarProveedorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_proveedor, container, false);

        dbHelper = new DbHelper(requireContext());

        // Initialize views
        idEditText = view.findViewById(R.id.Idproveedor);
        nombreEditText = view.findViewById(R.id.Nombreproveedor);
        direccionEditText = view.findViewById(R.id.Direccionproveedor);
        telefonoEditText = view.findViewById(R.id.Telefonoproveedor);
        correoEditText = view.findViewById(R.id.Correoproveedor);
        CIFEditText = view.findViewById(R.id.Cifproveedor);


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
        String idProveedor = idEditText.getText().toString();
        String nombre = nombreEditText.getText().toString();
        String direccion = direccionEditText.getText().toString();
        String telefono = telefonoEditText.getText().toString();
        String correo = correoEditText.getText().toString();
        String cif = CIFEditText.getText().toString();

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            if (!nombre.isEmpty()) values.put("Nombre", nombre);
            if (!direccion.isEmpty()) values.put("Direccion", direccion);
            if (!telefono.isEmpty()) values.put("Telefono", Integer.parseInt(telefono));
            if (!correo.isEmpty()) values.put("Descripcion", correo);
            if (!cif.isEmpty()) values.put("CIF", cif);

            String[] whereArgs = {idProveedor};
            int numRowsUpdated = db.update("Proveedores", values, "id=?", whereArgs);

            if (numRowsUpdated > 0) {
                Toast.makeText(requireContext(), "Proveedor modificado correctamente", Toast.LENGTH_SHORT).show();
                // Clear the fields and the image
                limpiarCampos();
            } else {
                Toast.makeText(requireContext(), "Error al modificar el proveedor", Toast.LENGTH_SHORT).show();
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        idEditText.setText("");
        nombreEditText.setText("");
        direccionEditText.setText("");
        telefonoEditText.setText("");
        correoEditText.setText("");
        CIFEditText.setText("");
    }

}
