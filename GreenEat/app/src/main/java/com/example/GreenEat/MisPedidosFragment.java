package com.example.GreenEat;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MisPedidosFragment extends Fragment implements PedidoAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private PedidoAdapter pedidoAdapter;
    private long idUsuario;

    public MisPedidosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);

        recyclerView = view.findViewById(R.id.recyclerpedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (pedidoAdapter == null) {
            pedidoAdapter = new PedidoAdapter(requireContext());
            pedidoAdapter.setOnItemClickListener(this);
        }

        recyclerView.setAdapter(pedidoAdapter);

        SharedPreferences prefs = requireContext().getSharedPreferences("MisPreferencias", requireContext().MODE_PRIVATE);
        idUsuario = prefs.getLong("idUsuario", -1);

        if (idUsuario != -1) {
            cargarPedidos();
        } else {
            // Manejar la situación en la que no se puede obtener el idUsuario desde SharedPreferences
            // (por ejemplo, redirigir a la pantalla de inicio de sesión)
        }


        return view;
    }
    public void onItemClick(Pedido pedido) {
        // Manejar el clic en un pedido, abrir DatosPedidoFragment
        abrirDatosPedidoFragment(pedido);
    }
    private void abrirDatosPedidoFragment(Pedido pedido) {
        // Crear un Bundle y agregar el ID y la lista de productos del pedido seleccionado
        Bundle bundle = new Bundle();
        bundle.putLong("idPedido", pedido.getIdPedido()); // Agregar el ID del pedido
        bundle.putSerializable("productos", new ArrayList<>(pedido.getProductosIncluidos()));

        // Crear el fragment de DatosPedido y establecer el argumento
        DatosPedidoFragment datosPedidoFragment = new DatosPedidoFragment();
        datosPedidoFragment.setArguments(bundle);

        // Reemplazar el fragmento actual con el fragment de DatosPedido
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(requireActivity().findViewById(R.id.frame_container).getId(), datosPedidoFragment);
        fragmentTransaction.addToBackStack(null); // Agregar la transacción a la pila de retroceso
        fragmentTransaction.commit();
    }

    private void cargarPedidos() {
        // Modifica la lógica para obtener todos los productos de la base de datos
        DbHelper dbHelper = new DbHelper(requireContext());
        List<Pedido> pedidos = obtenerPedidos(dbHelper, idUsuario);

        // Agregar los productos al adaptador
        pedidoAdapter.setPedidos(pedidos);
        pedidoAdapter.notifyDataSetChanged();
    }
    private List<Pedido> obtenerPedidos(DbHelper dbHelper, long idUsuario) {
        List<Pedido> pedidos = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM Pedidos WHERE idUsuario = ?", new String[]{String.valueOf(idUsuario)})) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Obtener datos del pedido
                    long idPedido = cursor.getLong(cursor.getColumnIndexOrThrow("idPedido"));
                    String fechaPedido = cursor.getString(cursor.getColumnIndexOrThrow("fechaPedido"));
                    double costeTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("costeTotal"));

                    // Obtener productos incluidos en el pedido
                    List<Producto> productosIncluidos = obtenerProductosIncluidosEnPedido(dbHelper, idPedido);

                    // Crear un objeto Pedido y agregarlo a la lista
                    Pedido pedido = new Pedido(idPedido, idUsuario, fechaPedido, costeTotal, productosIncluidos);
                    pedidos.add(pedido);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar error al obtener los registros
        }

        return pedidos;
    }

    private List<Producto> obtenerProductosIncluidosEnPedido(DbHelper dbHelper, long idPedido) {
        List<Producto> productosIncluidos = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT p.* FROM ProductosPedido pp " +
                     "JOIN Productos p ON pp.idProducto = p.id " +
                     "WHERE pp.idPedido = ?", new String[]{String.valueOf(idPedido)})) {

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
                    productosIncluidos.add(producto);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar error al obtener los registros
        }

        return productosIncluidos;
    }

}
