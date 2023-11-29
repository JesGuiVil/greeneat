package com.example.basedatos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ProductoFragment extends Fragment {

    // Existing code...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto, container, false);

        // Find buttons by their IDs
        Button addButton = view.findViewById(R.id.aniadirproducto);
        Button modifyButton = view.findViewById(R.id.modificarproducto);
        Button deleteButton = view.findViewById(R.id.eliminarproducto);
        Button showButton = view.findViewById(R.id.mostrarproducto);

        // Set click listeners for each button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the "Add Product" fragment
                replaceFragment(new AnadirProductoFragment());
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the "Modify Product" fragment
                replaceFragment(new ModificarProductoFragment());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the "Delete Product" fragment
                replaceFragment(new BorrarProductoFragment());
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace with the "Show Product" fragment
                replaceFragment(new MostrarProductoFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        // Get the FragmentManager
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the new one
        transaction.replace(R.id.fragment_container, fragment);

        // Add the transaction to the back stack
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
