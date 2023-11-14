package com.example.basedatos;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class ClienteActivity extends AppCompatActivity {
    InicioFragment inicioFragment = new InicioFragment();
    CarritoFragment carritoFragment = new CarritoFragment();
    CuentaFragment cuentaFragment = new CuentaFragment();
    private NavigationView lateralNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        NavigationBarView navigation = findViewById(R.id.bottom_navigation);

        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(inicioFragment);
        lateralNavigationView = findViewById(R.id.lateral_navigation);
        showHideLateralNavigationView(false);

    }

    private final  NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getTitle().toString()) {
            case "Inicio":
                loadFragment(inicioFragment);
                showHideLateralNavigationView(false);
                return true;
            case "Carrito":
                loadFragment(carritoFragment);
                showHideLateralNavigationView(false);
                return true;
            case "Cuenta":
                loadFragment(cuentaFragment);
                showHideLateralNavigationView(true);
                return true;
        }
        return false;
    };




    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
    private void showHideLateralNavigationView(boolean show) {
        if (show) {
            lateralNavigationView.setVisibility(View.VISIBLE);
            lateralNavigationView.setEnabled(true);
        } else {
            lateralNavigationView.setVisibility(View.GONE);
            lateralNavigationView.setEnabled(false);
        }
    }
}

