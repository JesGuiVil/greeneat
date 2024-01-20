package com.example.GreenEat;

import android.content.SharedPreferences;
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

public class DeseadosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;
    private long idUsuario;

    public DeseadosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deseados, container, false);

        recyclerView = view.findViewById(R.id.recyclerproductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (productoAdapter == null) {
            productoAdapter = new ProductoAdapter(requireContext());
        }

        recyclerView.setAdapter(productoAdapter);

        SharedPreferences prefs = requireContext().getSharedPreferences("MisPreferencias", requireContext().MODE_PRIVATE);
        idUsuario = prefs.getLong("idUsuario", -1);

        if (idUsuario != -1) {
            cargarDeseados();
        } else {
            // Manejar la situación en la que no se puede obtener el idUsuario desde SharedPreferences
            // (por ejemplo, redirigir a la pantalla de inicio de sesión)
        }


        return view;
    }

    private void cargarDeseados() {
        // Modifica la lógica para obtener todos los productos de la base de datos
        DbHelper dbHelper = new DbHelper(requireContext());
        List<Producto> productos = obtenerProductosDeseados(dbHelper, idUsuario);

        // Agregar los productos al adaptador
        productoAdapter.setProductos(productos);
        productoAdapter.notifyDataSetChanged();
    }
    private List<Producto> obtenerProductosDeseados(DbHelper dbHelper, long idUsuario) {
        List<Producto> productos = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT p.* FROM ProductosDeseados pd " +
                     "JOIN Productos p ON pd.idProducto = p.id " +
                     "WHERE pd.idUsuario = ?", new String[]{String.valueOf(idUsuario)})) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Obtener datos del producto deseado
                    long idProducto = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    String categoria = cursor.getString(cursor.getColumnIndexOrThrow("Categoria"));
                    double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("Precio"));
                    double iva = cursor.getDouble(cursor.getColumnIndexOrThrow("IVA"));
                    double peso = cursor.getDouble(cursor.getColumnIndexOrThrow("Peso"));
                    int stock = cursor.getInt(cursor.getColumnIndexOrThrow("Stock"));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("Descripcion"));
                    long proveedor = cursor.getLong(cursor.getColumnIndexOrThrow("ID_Proveedor"));
                    int enOferta = cursor.getInt(cursor.getColumnIndexOrThrow("EnOferta"));
                    String imagenPath = cursor.getString(cursor.getColumnIndexOrThrow("Imagen"));

                    // Crear un objeto Producto y agregarlo a la lista
                    Producto producto = new Producto(idProducto, nombre, categoria, precio, iva, peso, stock, descripcion, proveedor, enOferta, imagenPath);
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
