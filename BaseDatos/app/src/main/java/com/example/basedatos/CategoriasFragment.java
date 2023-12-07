package com.example.basedatos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CategoriasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);

        // Obtén referencias a las tarjetas
        CardView frutasCard = view.findViewById(R.id.frutasCard);
        CardView cerealesCard = view.findViewById(R.id.cerealesCard);
        CardView lacteosCard = view.findViewById(R.id.lacteosCard);
        CardView carneCard = view.findViewById(R.id.carneCard);
        CardView bebidasCard = view.findViewById(R.id.bebidasCard);
        CardView verTodoCard = view.findViewById(R.id.verTodoCard);

        // Agrega OnClickListener a cada tarjeta
        frutasCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("FRUTAS Y HORTALIZAS");
            }
        });

        cerealesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment("CEREALES");
            }
        });

        lacteosCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("LACTEOS");
            }
        });

        carneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("CARNES Y PESCADOS");
            }
        });

        bebidasCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("BEBIDAS");
            }
        });

        verTodoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("Ver todo");
            }
        });

        return view;
    }
    private void replaceFragment(String Categoria) {
        // Crea una instancia del fragmento MostrarProductoFragment
        MostrarProductoFragment mostrarProductoFragment = new MostrarProductoFragment();

        // Pasa la categoría seleccionada como argumento al fragmento
        Bundle bundle = new Bundle();
        bundle.putString("Categoria", Categoria);
        mostrarProductoFragment.setArguments(bundle);

        // Obtiene el FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Inicia una nueva transacción de fragmentos
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplaza el fragmento actual con el nuevo MostrarProductoFragment
        transaction.replace(R.id.frame_container, mostrarProductoFragment);

        // Agrega la transacción al back stack para permitir la navegación hacia atrás
        transaction.addToBackStack(null);

        // Realiza la transacción
        transaction.commit();
    }

}
