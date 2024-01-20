package com.example.GreenEat;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos;
    private Context context;
    private long idUsuario;
    private DbHelper dbHelper;

    public interface OnItemClickListener {
        void onItemClick(Pedido pedido);
    }

    private OnItemClickListener onItemClickListener;


    public PedidoAdapter(Context context) {
        this.context = context;
        this.pedidos = new ArrayList<>();
        dbHelper = new DbHelper(context);
        idUsuario = obtenerIdUsuario();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    private long obtenerIdUsuario() {
        SharedPreferences prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        return prefs.getLong("idUsuario", -1);
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);

        // Setear la información del pedido en el ViewHolder
        holder.idPedidoTextView.setText(""+pedido.getIdPedido());
        holder.fechaPedidoTextView.setText("" + pedido.getFechaPedido());
        holder.costeTotalTextView.setText("" + String.format("%.2f €", pedido.getCosteTotal()));
    }

    @Override
    public int getItemCount() {
        return pedidos != null ? pedidos.size() : 0;
    }

    // ViewHolder para representar cada elemento de la lista
    public class PedidoViewHolder extends RecyclerView.ViewHolder {
        public TextView idPedidoTextView;
        public TextView fechaPedidoTextView;
        public TextView costeTotalTextView;
        public CardView cardView;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            idPedidoTextView = itemView.findViewById(R.id.idPedidoTextView);
            fechaPedidoTextView = itemView.findViewById(R.id.fechaPedidoTextView);
            costeTotalTextView = itemView.findViewById(R.id.costeTotalTextView);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Manejar el clic en el botón para ver detalles del pedido
                    onItemClickListener.onItemClick(pedidos.get(getAdapterPosition()));
                }
            });
        }

    }
}

