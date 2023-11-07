package com.example.basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="ejemplo.db";



    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROVEEDOR_TABLE = "CREATE TABLE Proveedores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre TEXT, " +
                "Apellidos TEXT, " +
                "Direccion TEXT, " +
                "Telefono INTEGER, " +
                "Correo_e TEXT," +
                "Fecha_alta DATE, " +
                "NIF TEXT " +
                ")";

        db.execSQL(CREATE_PROVEEDOR_TABLE);

        // Tabla Usuario
        String CREATE_USUARIO_TABLE = "CREATE TABLE Usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre TEXT, " +
                "Apellidos TEXT, " +
                "Admin INTEGER, " +
                "Direccion TEXT, " +
                "Telefono INTEGER, " +
                "Correo_e TEXT, " +
                "Contrasenia TEXT, " +
                "Fecha_alta DATE, " +
                "NIF TEXT " +
                ")";

        db.execSQL(CREATE_USUARIO_TABLE);

        ContentValues values = new ContentValues();
        values.put("Nombre", "admin");
        values.put("Apellidos", "admin");
        values.put("Admin", 1);
        values.put("Direccion", "greeneat 1");
        values.put("Telefono", 954999999);
        values.put("Correo_e", "admin@greeneat.com");
        values.put("Contrasenia", "admin");
        values.put("NIF", "12345678A");
        values.put("Fecha_alta", obtenerFechaActual());

        long newRowId = db.insert("Usuarios", null, values);

        // Tabla Producto
        String CREATE_PRODUCTO_TABLE = "CREATE TABLE Productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre TEXT, " +
                "Seccion TEXT, " +
                "Precio REAL, " +
                "IVA REAL, " +
                "Peso REAL, " +
                "Stock INTEGER, " +
                "ID_Proveedor INTEGER, " +
                "FOREIGN KEY (ID_Proveedor) REFERENCES Proveedores(id) " +
                ")";
        db.execSQL(CREATE_PRODUCTO_TABLE);

        // Tabla Pedidos
        String CREATE_PEDIDOS_TABLE = "CREATE TABLE Pedidos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Total REAL, " +
                "Direccion_factu TEXT, " +
                "Fecha DATE, " +
                "Fecha_entrega DATE, " +
                "ID_Usuario INTEGER, " +
                "FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(id) " +
                ")";
        db.execSQL(CREATE_PEDIDOS_TABLE);

        // Tabla Incluye
        String CREATE_INCLUYE_TABLE = "CREATE TABLE Incluye (" +
                "ID_Pedido INTEGER, " +
                "ID_Producto INTEGER, " +
                "PRIMARY KEY (ID_Pedido, ID_Producto), " +
                "FOREIGN KEY (ID_Pedido) REFERENCES Pedidos(id), " +
                "FOREIGN KEY (ID_Producto) REFERENCES Productos(id) " +
                ")";
        db.execSQL(CREATE_INCLUYE_TABLE);

        // Tabla Reclamaciones
        String CREATE_RECLAMACIONES_TABLE = "CREATE TABLE Reclamaciones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_Pedido INTEGER, " +
                "Descripcion TEXT, " +
                "Estado_activo INTEGER, " +
                "FOREIGN KEY (ID_Pedido) REFERENCES Pedidos(id) " +
                ")";
        db.execSQL(CREATE_RECLAMACIONES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE Proveedores");
        db.execSQL("DROP TABLE Usuarios");
        db.execSQL("DROP TABLE Productos");
        db.execSQL("DROP TABLE Pedidos");
        db.execSQL("DROP TABLE Incluye");
        db.execSQL("DROP TABLE Reclamaciones");

        onCreate(db);
    }
    private String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = new Date();
        return dateFormat.format(fechaActual);
    }
}
