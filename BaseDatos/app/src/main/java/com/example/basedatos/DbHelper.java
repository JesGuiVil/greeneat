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
        ContentValues valuesProveedores = new ContentValues();

        valuesProveedores.put("Nombre", "CooSur");
        valuesProveedores.put("Apellidos", "CoopAgricola");
        valuesProveedores.put("Direccion", "greeneat 1");
        valuesProveedores.put("Telefono", 954999999);
        valuesProveedores.put("Correo_e", "admin@coosur.com");
        valuesProveedores.put("Fecha_alta", obtenerFechaActual());
        valuesProveedores.put("NIF", "12345678A");

        long newRowIdProveedores = db.insert("Proveedores", null, valuesProveedores);


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

        ContentValues valuesUsuarios = new ContentValues();


        valuesUsuarios.put("Nombre", "admin");
        valuesUsuarios.put("Apellidos", "admin");
        valuesUsuarios.put("Admin", 1);
        valuesUsuarios.put("Direccion", "greeneat 1");
        valuesUsuarios.put("Telefono", 954999999);
        valuesUsuarios.put("Correo_e", "admin@greeneat.com");
        valuesUsuarios.put("Contrasenia", "admin");
        valuesUsuarios.put("NIF", "12345678A");
        valuesUsuarios.put("Fecha_alta", obtenerFechaActual());

        long newRowIdUsuarios = db.insert("Usuarios", null, valuesUsuarios);

        // Tabla Producto
        String CREATE_PRODUCTO_TABLE = "CREATE TABLE Productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre TEXT, " +
                "Categoria TEXT CHECK (Categoria IN ('FRUTAS Y HORTALIZAS', 'CEREALES', 'LACTEOS', 'CARNES Y PESCADOS', 'BEBIDAS')), " +
                "Precio REAL, " +
                "IVA REAL, " +
                "Peso REAL, " +
                "Stock INTEGER, " +
                "Descripcion TEXT, " +
                "ID_Proveedor INTEGER, " +
                "EnOferta INTEGER DEFAULT 0, " +
                "Imagen BLOB, " +  // Nuevo campo para la ruta de la imagen
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
