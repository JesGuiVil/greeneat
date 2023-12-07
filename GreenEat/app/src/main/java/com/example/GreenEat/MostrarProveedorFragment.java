package com.example.GreenEat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Locale;

public class MostrarProveedorFragment extends Fragment {

    private DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrar_proveedor, container, false);
        dbHelper = new DbHelper(requireContext());

        mostrarRegistros(view.findViewById(R.id.datosproveedores));

        return view;
    }

    private void mostrarRegistros(TextView datosProveedoresTextView) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Proveedores", null, null, null, null, null, null)) {

            StringBuilder proveedoresText = new StringBuilder();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    String direccion = cursor.getString(cursor.getColumnIndexOrThrow("Direccion"));
                    int telefono = cursor.getInt(cursor.getColumnIndexOrThrow("Telefono"));
                    String correo = cursor.getString(cursor.getColumnIndexOrThrow("Correo_e"));
                    String cif = cursor.getString(cursor.getColumnIndexOrThrow("CIF"));

                    String proveedorInfo = String.format(Locale.getDefault(),
                            "ID: %d\nNombre: %s\nDirección: %s\nTeléfono: %d\nCorreo: %s\nCIF: %s\n\n",
                            id, nombre, direccion, telefono, correo, cif);

                    proveedoresText.append(proveedorInfo);

                } while (cursor.moveToNext());

                datosProveedoresTextView.setText(proveedoresText.toString());
            } else {
                datosProveedoresTextView.setText("No hay proveedores registrados.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error al leer datos", Toast.LENGTH_SHORT).show();
        }
    }
}
