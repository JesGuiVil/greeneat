package com.example.GreenEat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProveedoresFragment extends Fragment {

    // Existing code...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proveedores, container, false);

        Button addButton = view.findViewById(R.id.aniadirproveedor);
        Button modifyButton = view.findViewById(R.id.modificarproveedor);
        Button deleteButton = view.findViewById(R.id.eliminarproveedor);
        Button showButton = view.findViewById(R.id.mostrarproveedor);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AnadirProveedorFragment());
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ModificarProveedorFragment());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BorrarProveedorFragment());
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MostrarProveedorFragment());
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
        transaction.replace(R.id.frame_containeradmin, fragment);

        // Add the transaction to the back stack
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
