package com.example.GreenEat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AnadirProveedorFragment extends Fragment {

    EditText nombreEditText, apellidosEditText, direccionEditText, telefonoEditText, correoEditText, cifEditText;
    DbHelper dbHelper;

    public AnadirProveedorFragment() {
        // Required empty public constructor
    }
    public static AnadirProveedorFragment newInstance() {
        return new AnadirProveedorFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anadir_proveedor, container, false);

        dbHelper = new DbHelper(requireContext());

        nombreEditText = view.findViewById(R.id.Nombreproveedor);
        direccionEditText = view.findViewById(R.id.Direccionproveedor);
        telefonoEditText = view.findViewById(R.id.Telefonoproveedor);
        correoEditText = view.findViewById(R.id.Correo_eproveedor);
        cifEditText = view.findViewById(R.id.CIFproveedor);

        Button enviarButton = view.findViewById(R.id.Enviar);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreEditText.getText().toString();
                String direccion = direccionEditText.getText().toString();
                String telefonoStr = telefonoEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String fechaAlta = RegistroActivity.obtenerFechaActual();
                String cif = cifEditText.getText().toString();

                if (nombre.isEmpty() || direccion.isEmpty() || telefonoStr.isEmpty() || correo.isEmpty() || fechaAlta.isEmpty() || cif.isEmpty()) {
                    Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("Nombre", nombre);
                    values.put("Direccion", direccion);
                    values.put("Telefono", Integer.parseInt(telefonoStr));
                    values.put("Correo_e", correo);
                    values.put("Fecha_alta", fechaAlta);
                    values.put("CIF", cif);

                    long newRowId = db.insert("Proveedores", null, values);

                    if (newRowId != -1) {
                        Toast.makeText(requireContext(), "Proveedor agregado correctamente", Toast.LENGTH_SHORT).show();
                        // Limpia los campos
                        nombreEditText.setText("");
                        direccionEditText.setText("");
                        telefonoEditText.setText("");
                        correoEditText.setText("");
                        cifEditText.setText("");
                    } else {
                        Toast.makeText(requireContext(), "Error al agregar el proveedor", Toast.LENGTH_SHORT).show();
                    }

                    db.close();
                }
            }
        });
        return view;
    }
}
