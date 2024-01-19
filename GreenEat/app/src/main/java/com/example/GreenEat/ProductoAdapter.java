package com.example.GreenEat;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private static List<Producto> productos;
    public long idUsuario;
    private SharedPreferences prefs;

    public ProductoAdapter(Context context) {
        prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        idUsuario = prefs.getLong("idUsuario", -1);
    }



    public void setProductos(List<Producto> productos) {
        this.productos = productos;
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

        // Establecer el recurso de imagen del favicon según la condición
        if (isProductoEnListaFavoritos(producto.getId(),holder.itemView.getContext())) {
            holder.faviconImageView.setImageResource(R.drawable.fullfav);
        } else {
            holder.faviconImageView.setImageResource(R.drawable.emptyfav);
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
        public ImageView addcartImageView;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenImageView = itemView.findViewById(R.id.imagenImageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            precioTextView = itemView.findViewById(R.id.precioTextView);

            faviconImageView = itemView.findViewById(R.id.favicon);
            faviconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Manejar el clic en el ícono de favoritos aquí
                    agregarAFavoritos(productos.get(getAdapterPosition()).getId());
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

    }
}
