package com.example.basedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        recyclerView = view.findViewById(R.id.recyclerproductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        productoAdapter = new ProductoAdapter();
        recyclerView.setAdapter(productoAdapter);

        cargarProductos(); // Método para cargar los productos desde la base de datos

        return view;
    }

    private void cargarProductos() {
        // Aquí debes obtener los productos desde tu base de datos y agregarlos al adaptador
        // Reemplaza este código con la lógica para obtener datos de la base de datos

        DbHelper dbHelper = new DbHelper(requireContext());
        List<Producto> productos = obtenerProductosDesdeBaseDeDatos(dbHelper);

        // Agregar los productos al adaptador
        productoAdapter.setProductos(productos);
        productoAdapter.notifyDataSetChanged();
    }

    // Método de ejemplo para obtener productos desde la base de datos
    private List<Producto> obtenerProductosDesdeBaseDeDatos(DbHelper dbHelper) {
        List<Producto> productos = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Productos", null, null, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"));
                    double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("Precio"));
                    String imagenPath = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"));



                    // Crear un objeto Producto y agregarlo a la lista
                    Producto producto = new Producto(id, nombre, "", precio, 0, 0, 0, descripcion, 0, imagenPath);
                    productos.add(producto);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar error al obtener los registros
        }

        return productos;
    }
}

