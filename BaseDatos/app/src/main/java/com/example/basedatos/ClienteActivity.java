package com.example.basedatos;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;

public class ClienteActivity extends AppCompatActivity {
    InicioFragment inicioFragment = new InicioFragment();
    CarritoFragment carritoFragment = new CarritoFragment();
    CuentaFragment cuentaFragment = new CuentaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        NavigationBarView navigation = findViewById(R.id.bottom_navigation);

        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(inicioFragment);

    }

    private final  NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getTitle().toString()) {
                case "Inicio":
                    loadFragment(inicioFragment);
                    return true;
                case "Carrito":
                    loadFragment(carritoFragment);
                    return true;
                case "Cuenta":
                    loadFragment(cuentaFragment);
                    return true;
            }
            return false;
        }
    };




    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}

