package com.example.GreenEat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> productos;

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

        // Cargar la imagen usando Glide
        Glide.with(holder.itemView.getContext())
                .load(producto.getImagen())
                .into(holder.imagenImageView);
    }

    @Override
    public int getItemCount() {
        return productos != null ? productos.size() : 0;
    }

    // ViewHolder para representar cada elemento de la lista
    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagenImageView;
        public TextView nombreTextView;
        public TextView descripcionTextView;
        public TextView precioTextView;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenImageView = itemView.findViewById(R.id.imagenImageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            precioTextView = itemView.findViewById(R.id.precioTextView);
        }
    }
}
