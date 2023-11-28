package com.example.basedatos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
    private NavigationBarView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        navigation = findViewById(R.id.bottom_navigation);

        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(inicioFragment);
        lateralNavigationView = findViewById(R.id.lateral_navigation);
        lateralNavigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        showHideLateralNavigationView(false);

        View headerView = lateralNavigationView.getHeaderView(0);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        long idUsuario = prefs.getLong("idUsuario", -1);
        String nombreUsuario = prefs.getString("nombreUsuario", "");
        String correoUsuario = prefs.getString("correoUsuario", "");

        TextView nombreTextView = headerView.findViewById(R.id.nombreusuario);
        TextView correoTextView = headerView.findViewById(R.id.gmailusuario);

// Actualiza los textos con la información del usuario
        nombreTextView.setText(nombreUsuario);
        correoTextView.setText(correoUsuario);

    }
    private boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            // Otros casos aquí

            case "Cerrar sesion":
                // Manejar el clic en "Cerrar sesión"
                SessionManager.cerrarSesion(this);
                return true;
        }
        return false;
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
    public void onBackPressed() {
        if (lateralNavigationView.getVisibility() == View.VISIBLE) {
            // Si el lateral navigation está visible, ocúltalo
            showHideLateralNavigationView(false);
        } else {
            // Si el lateral navigation está oculto
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);

            if (currentFragment == cuentaFragment) {
                // Si la cuenta está seleccionada, vuelve a inicio
                loadFragment(inicioFragment);
                navigation.setSelectedItemId(R.id.iniciomenu);
                showHideLateralNavigationView(false);
            } else if (currentFragment == inicioFragment) {
                // Si ya estás en inicio, cierra la aplicación
                super.onBackPressed();
            }
        }
    }
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