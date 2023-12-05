package com.example.basedatos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    ProductoFragment productoFragment = new ProductoFragment();
    ProveedoresFragment proveedoresFragment = new ProveedoresFragment();
    ClienteFragment clienteFragment = new ClienteFragment();
    PedidosFragment pedidosFragment = new PedidosFragment();
    CuentaFragment cuentaFragment = new CuentaFragment();
    private NavigationView lateralNavigationView;
    private NavigationBarView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        navigation = findViewById(R.id.bottom_navigationadmin);

        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(productoFragment);
        lateralNavigationView = findViewById(R.id.lateral_navigationadmin);
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
            case "Productos":
                loadFragment(productoFragment);
                showHideLateralNavigationView(false);
                return true;
            case "Proveedores":
                loadFragment(proveedoresFragment);
                showHideLateralNavigationView(false);
                return true;
            case "Clientes":
                loadFragment(clienteFragment);
                showHideLateralNavigationView(false);
                return true;
            case "Pedidos":
                loadFragment(pedidosFragment);
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
            super.onBackPressed();
        }
    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_containeradmin, fragment);
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


