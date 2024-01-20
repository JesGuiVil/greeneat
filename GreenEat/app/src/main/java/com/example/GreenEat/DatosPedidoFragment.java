package com.example.GreenEat;

import android.annotation.SuppressLint;
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

public class DatosPedidoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductoPedidoAdapter productoPedidoAdapter;

    private DbHelper dbHelper;

    public DatosPedidoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_producto, container, false);

        dbHelper = new DbHelper(requireContext());

        recyclerView = view.findViewById(R.id.recyclerproductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (productoPedidoAdapter == null) {
            productoPedidoAdapter = new ProductoPedidoAdapter(requireContext());
        }

        recyclerView.setAdapter(productoPedidoAdapter);

        // Obtén la categoría seleccionada del Bundle
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("productos") && bundle.containsKey("idPedido")) {
            long idPedido = bundle.getLong("idPedido");
            List<Producto> productos = (List<Producto>) bundle.getSerializable("productos");

            // Verifica que la lista de productos no sea nula antes de asignarla al adaptador
            if (productos != null) {
                // Borra los productos actuales del adaptador antes de agregar los nuevos
                productoPedidoAdapter.clearProductos();

                // Establece la cantidad de unidades para cada producto
                for (Producto producto : productos) {
                    producto.setCantidad(obtenerCantidadParaProducto(idPedido, producto.getId()));
                }

                productoPedidoAdapter.setProductos(productos);
            }
        }
        return view;
    }
    @SuppressLint("Range")
    public int obtenerCantidadParaProducto(long idPedido, long idProducto) {
        int cantidad = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Suponiendo que tienes una columna 'cantidad' en la tabla 'ProductosPedido'
        String query = "SELECT cantidad FROM ProductosPedido WHERE idPedido = ? AND idProducto = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idPedido), String.valueOf(idProducto)});

        if (cursor != null && cursor.moveToFirst()) {
            cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
            cursor.close();
        }

        db.close();
        return cantidad;
    }

}
