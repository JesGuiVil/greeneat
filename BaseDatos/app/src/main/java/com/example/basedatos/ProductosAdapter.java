package com.example.basedatos;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductosAdapter extends ArrayAdapter<Producto> {
    private Context context;
    private int layoutResourceId;
    private List<Producto> data;

    public ProductosAdapter(Context context, int layoutResourceId, List<Producto> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.imageView = row.findViewById(R.id.imageViewProducto);
            holder.textViewNombre = row.findViewById(R.id.textViewNombre);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Producto producto = data.get(position);

        // Configurar el nombre y la descripción del producto
        holder.textViewNombre.setText(producto.getNombre());

        // Cargar la imagen usando Picasso (o Glide u otra biblioteca) en el ImageView
        Picasso.get().load(producto.getImagen()).into(holder.imageView);

        return row;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textViewNombre;
    }
}
