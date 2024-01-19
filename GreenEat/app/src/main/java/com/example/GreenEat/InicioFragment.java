package com.example.GreenEat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class InicioFragment extends Fragment {
    CategoriasFragment categoriasFragment = new CategoriasFragment();
    private RecyclerView recyclerOfertas;
    private ProductoAdapter productoOfertaAdapter;
    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        recyclerOfertas = view.findViewById(R.id.recyclerOfertas);
        recyclerOfertas.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        if (productoOfertaAdapter == null) {
            productoOfertaAdapter = new ProductoAdapter(requireContext());
        }
        recyclerOfertas.setAdapter(productoOfertaAdapter);
        cargarProductosEnOferta();

        ViewFlipper flipper = view.findViewById(R.id.flipper);
        flipper.startFlipping();

        Button ver = view.findViewById(R.id.vercats);

        ver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                replaceFragment(categoriasFragment);
            }
        });

        return view;
    }
    private void cargarProductosEnOferta() {
        DbHelper dbHelper = new DbHelper(requireContext());
        List<Producto> productos = obtenerProductosEnOferta(dbHelper);

        // Agregar los productos al adaptador
        productoOfertaAdapter.setProductos(productos);
        productoOfertaAdapter.notifyDataSetChanged();
    }
    private List<Producto> obtenerProductosEnOferta(DbHelper dbHelper) {
        List<Producto> productosOferta = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Productos", null, "EnOferta=?",
                new String[]{"1"}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    String categoria = cursor.getString(cursor.getColumnIndexOrThrow("Categoria"));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"));
                    double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("Precio"));
                    double iva = cursor.getDouble(cursor.getColumnIndexOrThrow("IVA"));
                    double peso = cursor.getDouble(cursor.getColumnIndexOrThrow("Peso"));
                    int stock = cursor.getInt(cursor.getColumnIndexOrThrow("Stock"));
                    long proveedor = cursor.getLong(cursor.getColumnIndexOrThrow("ID_Proveedor"));
                    int enOferta = cursor.getInt(cursor.getColumnIndexOrThrow("EnOferta"));
                    String imagenPath = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"));

                    // Crear un objeto Producto y agregarlo a la lista
                    Producto producto = new Producto(id, nombre, categoria, precio, iva, peso, stock, descripcion, proveedor, enOferta, imagenPath);
                    productosOferta.add(producto);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar error al obtener los registros
        }

        return productosOferta;
    }
    private void replaceFragment(Fragment fragment) {
        // Get the FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new one
        transaction.replace(R.id.frame_container, fragment);

        // Add the transaction to the back stack
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
