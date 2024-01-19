package com.example.GreenEat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MostrarProductoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;

    public MostrarProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_producto, container, false);

        recyclerView = view.findViewById(R.id.recyclerproductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (productoAdapter == null) {
            productoAdapter = new ProductoAdapter(requireContext());
        }

        recyclerView.setAdapter(productoAdapter);

        // Obtén la categoría seleccionada del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String categoriaSeleccionada = bundle.getString("Categoria");
            if ("Ver todo".equals(categoriaSeleccionada)) {
                cargarTodosLosProductos();
            } else {
                // Si es una categoría específica, cargar productos por esa categoría
                cargarProductosPorCategoria(categoriaSeleccionada);
            }
        } else {
            cargarTodosLosProductos();
        }
        return view;
    }

    private void cargarProductosPorCategoria(String categoria) {
        // Modifica la lógica para obtener productos de la base de datos
        DbHelper dbHelper = new DbHelper(requireContext());
        List<Producto> productos = obtenerProductosPorCategoria(dbHelper, categoria);

        // Agregar los productos al adaptador
        productoAdapter.setProductos(productos);
        productoAdapter.notifyDataSetChanged();
    }

    private List<Producto> obtenerProductosPorCategoria(DbHelper dbHelper, String categoria) {
        List<Producto> productos = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Productos", null, "Categoria=?", new String[]{categoria}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"));
                    double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("Precio"));
                    double iva = cursor.getDouble(cursor.getColumnIndexOrThrow("IVA"));
                    double peso = cursor.getDouble(cursor.getColumnIndexOrThrow("Peso"));
                    int stock = cursor.getInt(cursor.getColumnIndexOrThrow("Stock"));
                    long proveedor = cursor.getLong(cursor.getColumnIndexOrThrow("ID_Proveedor"));
                    int enOferta = cursor.getInt(cursor.getColumnIndexOrThrow("EnOferta"));
                    String imagenPath = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"));

                    // Crear un objeto Producto y agregarlo a la lista
                    Producto producto = new Producto(id, nombre, categoria, precio, iva, peso, stock, descripcion,proveedor, enOferta, imagenPath);
                    productos.add(producto);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar error al obtener los registros
        }

        return productos;
    }
    private void cargarTodosLosProductos() {
        // Modifica la lógica para obtener todos los productos de la base de datos
        DbHelper dbHelper = new DbHelper(requireContext());
        List<Producto> productos = obtenerTodosLosProductos(dbHelper);

        // Agregar los productos al adaptador
        productoAdapter.setProductos(productos);
        productoAdapter.notifyDataSetChanged();
    }
    private List<Producto> obtenerTodosLosProductos(DbHelper dbHelper) {
        List<Producto> productos = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Productos", null, null, null, null, null, null)) {

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
                    Producto producto = new Producto(id, nombre, categoria, precio, iva, peso, stock, descripcion,proveedor, enOferta, imagenPath);
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
