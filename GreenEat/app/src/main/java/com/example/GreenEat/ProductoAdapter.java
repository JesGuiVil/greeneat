package com.example.GreenEat;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private static List<Producto> productos;
    public long idUsuario;
    private SharedPreferences prefs;
    private CarritoFragment carritoFragment;

    public ProductoAdapter(Context context) {
        prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        idUsuario = prefs.getLong("idUsuario", -1);
    }
    public void setCarritoFragment(CarritoFragment carritoFragment) {
        this.carritoFragment = carritoFragment;
    }


    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();  // Asegurarse de notificar al adaptador sobre los cambios
    }
    public void clearProductos() {
        if (productos != null) {
            productos.clear();
            notifyDataSetChanged();  // Notificar al adaptador sobre la eliminación de los productos
        }
    }
    public void addProductos(List<Producto> nuevosProductos) {
        if (productos == null) {
            productos = new ArrayList<>();
        }
        productos.addAll(nuevosProductos);
        notifyDataSetChanged();  // Notificar al adaptador sobre la adición de nuevos productos
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);

        // Setear la información del producto en el ViewHolder
        holder.nombreTextView.setText(producto.getNombre());
        holder.descripcionTextView.setText(producto.getDescripcion());

        // Formatear el precio con dos decimales y el símbolo de euro
        String precioFormateado = String.format(Locale.getDefault(), "%.2f €", producto.getPrecio());
        holder.precioTextView.setText(precioFormateado);

        int cantidadEnCarrito = obtenerCantidadEnCarrito(producto.getId(), holder.itemView.getContext());
        holder.cantidadTextView.setText(String.valueOf(cantidadEnCarrito));

        // Establecer el recurso de imagen del favicon según la condición
        if (isProductoEnListaFavoritos(producto.getId(),holder.itemView.getContext())) {
            holder.faviconImageView.setImageResource(R.drawable.fullfav);
        } else {
            holder.faviconImageView.setImageResource(R.drawable.emptyfav);
        }
        if (isProductoEnListaCarrito(producto.getId(),holder.itemView.getContext())) {
            holder.cartImageView.setImageResource(R.drawable.removecart);
        } else {
            holder.cartImageView.setImageResource(R.drawable.addcart);
        }
        // Cargar la imagen usando Glide
        Glide.with(holder.itemView.getContext())
                .load(producto.getImagen())
                .into(holder.imagenImageView);
    }

    private boolean isProductoEnListaFavoritos(long idProducto, Context context) {
        // Verificar si el producto está en la lista de favoritos
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consultar la tabla ProductosDeseados para ver si existe una entrada con el idUsuario y el idProducto
        Cursor cursor = db.rawQuery("SELECT * FROM ProductosDeseados WHERE idUsuario = ? AND idProducto = ?",
                new String[]{String.valueOf(idUsuario), String.valueOf(idProducto)});

        boolean productoEnLista = false;

        if (cursor != null) {
            productoEnLista = cursor.getCount() > 0; // Si hay al menos una fila, el producto está en la lista
            cursor.close();
        }

        return productoEnLista;
    }
    private boolean isProductoEnListaCarrito(long idProducto, Context context) {
        // Verificar si el producto está en la lista de favoritos
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consultar la tabla ProductosDeseados para ver si existe una entrada con el idUsuario y el idProducto
        Cursor cursor = db.rawQuery("SELECT * FROM ProductosCarrito WHERE idUsuario = ? AND idProducto = ?",
                new String[]{String.valueOf(idUsuario), String.valueOf(idProducto)});

        boolean productoEnLista = false;

        if (cursor != null) {
            productoEnLista = cursor.getCount() > 0; // Si hay al menos una fila, el producto está en la lista
            cursor.close();
        }

        return productoEnLista;
    }


    @Override
    public int getItemCount() {
        return productos != null ? productos.size() : 0;
    }

    // ViewHolder para representar cada elemento de la lista
    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagenImageView;
        public TextView nombreTextView;
        public TextView descripcionTextView;
        public TextView precioTextView;
        public ImageView faviconImageView;
        public ImageView cartImageView;

        public EditText cantidadEditText;
        public TextView cantidadTextView;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenImageView = itemView.findViewById(R.id.imagenImageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            precioTextView = itemView.findViewById(R.id.precioTextView);
            faviconImageView = itemView.findViewById(R.id.favicon);
            cartImageView = itemView.findViewById(R.id.cart);
            cantidadEditText = itemView.findViewById(R.id.cantidadEditText);
            cantidadTextView = itemView.findViewById(R.id.cantidadTextView);
            faviconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Manejar el clic en el ícono de favoritos aquí
                    agregarAFavoritos(productos.get(getAdapterPosition()).getId());
                }
            });
            cartImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cantidadStr = cantidadEditText.getText().toString().trim();
                    if (!cantidadStr.isEmpty()) {
                        int cantidad = Integer.parseInt(cantidadStr);
                        // Manejar el clic en el ícono del carrito aquí
                        agregarAlCarrito(productos.get(getAdapterPosition()).getId(), cantidad);
                    } else {
                        // Mostrar un mensaje si el EditText está vacío
                        Toast.makeText(itemView.getContext(), "Ingrese una cantidad", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        private void agregarAFavoritos(long idProducto) {
            if (idUsuario != -1) {
                boolean productoEnLista = isProductoEnListaFavoritos(idProducto, itemView.getContext());

                if (!productoEnLista) {
                    // El producto no está en la lista, así que lo agregamos
                    faviconImageView.setImageResource(R.drawable.fullfav);
                    DbHelper dbHelper = new DbHelper(itemView.getContext());
                    dbHelper.agregarProductoDeseado(idUsuario, idProducto);
                    Toast.makeText(itemView.getContext(), "Producto añadido a la lista de deseados", Toast.LENGTH_SHORT).show();
                } else {
                    // El producto ya está en la lista, así que lo eliminamos
                    faviconImageView.setImageResource(R.drawable.emptyfav);
                    DbHelper dbHelper = new DbHelper(itemView.getContext());
                    dbHelper.eliminarProductoDeseado(idUsuario, idProducto);
                    Toast.makeText(itemView.getContext(), "Producto eliminado de la lista de deseados", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(itemView.getContext(), "No se pudo añadir/eliminar el producto. Inténtalo de nuevo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }
        private void agregarAlCarrito(long idProducto, int cantidad) {
            if (idUsuario != -1) {
                DbHelper dbHelper = new DbHelper(itemView.getContext());
                boolean productoEnLista = isProductoEnListaCarrito(idProducto, itemView.getContext());

                if (!productoEnLista) {
                    // El producto no está en la lista, así que lo agregamos
                    cartImageView.setImageResource(R.drawable.removecart);
                    dbHelper.agregarProductoCarrito(idUsuario, idProducto, cantidad);
                    Toast.makeText(itemView.getContext(), cantidad + " producto(s) añadido(s) al carrito", Toast.LENGTH_SHORT).show();
                } else {
                    // El producto ya está en la lista, así que obtenemos la cantidad actual
                    int cantidadActual = dbHelper.obtenerCantidadProductoCarrito(idUsuario, idProducto);

                    // Restamos la nueva cantidad de la cantidad actual
                    int nuevaCantidad = cantidadActual - cantidad;

                    if (nuevaCantidad > 0) {
                        // Actualizamos la cantidad en el carrito
                        dbHelper.actualizarCantidadProductoCarrito(idUsuario, idProducto, nuevaCantidad);
                        Toast.makeText(itemView.getContext(), cantidad + " producto(s) eliminado(s) del carrito", Toast.LENGTH_SHORT).show();
                    } else {
                        // Si la nueva cantidad es cero o menos, eliminamos el producto del carrito
                        cartImageView.setImageResource(R.drawable.addcart);
                        dbHelper.eliminarProductoCarrito(idUsuario, idProducto);
                        Toast.makeText(itemView.getContext(), "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
                    }
                }
                cantidadEditText.setText("");
            } else {
                Toast.makeText(itemView.getContext(), "No se pudo añadir/eliminar el producto. Inténtalo de nuevo más tarde.", Toast.LENGTH_SHORT).show();
            }
            notifyDataSetChanged();
        }
    }

    private int obtenerCantidadEnCarrito(long idProducto, Context context) {
        DbHelper dbHelper = new DbHelper(context);
        return dbHelper.obtenerCantidadProductoCarrito(idUsuario, idProducto);
    }
}
