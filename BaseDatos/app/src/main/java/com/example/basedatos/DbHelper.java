package com.example.basedatos;

import static com.example.basedatos.ResourceHelper.getResources;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "greeneat.db";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ResourceHelper.initialize(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROVEEDOR_TABLE = "CREATE TABLE Proveedores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nombre TEXT, " +
                "Direccion TEXT, " +
                "Telefono INTEGER, " +
                "Correo_e TEXT," +
                "Fecha_alta DATE, " +
                "CIF TEXT " +
                ")";

        db.execSQL(CREATE_PROVEEDOR_TABLE);
        ContentValues valuesProveedores = new ContentValues();

        valuesProveedores.put("Nombre", "CooSur");
        valuesProveedores.put("Direccion", "greeneat 1");
        valuesProveedores.put("Telefono", 954999999);
        valuesProveedores.put("Correo_e", "admin@coosur.com");
        valuesProveedores.put("Fecha_alta", obtenerFechaActual());
        valuesProveedores.put("CIF", "12345678A");

        db.insert("Proveedores", null, valuesProveedores);


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
        valuesUsuarios.put("Contrasenia", "k6hjb/ilMgFGewOkgP/t7g==");
        valuesUsuarios.put("NIF", "12345678A");
        valuesUsuarios.put("Fecha_alta", obtenerFechaActual());

        db.insert("Usuarios", null, valuesUsuarios);

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
                "Imagen TEXT, " +  // Nuevo campo para la ruta de la imagen
                "FOREIGN KEY (ID_Proveedor) REFERENCES Proveedores(id) " +
                ")";

        db.execSQL(CREATE_PRODUCTO_TABLE);


        ContentValues valuesProducto1 = new ContentValues();
        valuesProducto1.put("Nombre", "Tomates");
        valuesProducto1.put("Categoria", "FRUTAS Y HORTALIZAS");
        valuesProducto1.put("Precio", 5);
        valuesProducto1.put("IVA", 21);
        valuesProducto1.put("Peso", 2);
        valuesProducto1.put("Stock", 150);
        valuesProducto1.put("Descripcion", "Tomates de Los Palacios");
        valuesProducto1.put("ID_Proveedor", 1);
        valuesProducto1.put("EnOferta", 0);
        valuesProducto1.put("Imagen", "file:///android_asset/tomates.png");
        long idProducto1 = db.insert("Productos", null, valuesProducto1);


        ContentValues valuesProducto2 = new ContentValues();
        valuesProducto2.put("Nombre", "Zanahorias");
        valuesProducto2.put("Categoria", "FRUTAS Y HORTALIZAS");
        valuesProducto2.put("Precio", 3);
        valuesProducto2.put("IVA", 21);
        valuesProducto2.put("Peso", 3);
        valuesProducto2.put("Stock", 200);
        valuesProducto2.put("Descripcion", "Zanahorias frescas");
        valuesProducto2.put("ID_Proveedor", 1);
        valuesProducto2.put("EnOferta", 0);
        valuesProducto2.put("Imagen","file:///android_asset/zanahorias.png");
        long idProducto2 = db.insert("Productos", null, valuesProducto2);

        ContentValues valuesProducto3 = new ContentValues();
        valuesProducto3.put("Nombre", "Trigo");
        valuesProducto3.put("Categoria", "CEREALES");
        valuesProducto3.put("Precio", 6);
        valuesProducto3.put("IVA", 21);
        valuesProducto3.put("Peso", 5);
        valuesProducto3.put("Stock", 800);
        valuesProducto3.put("Descripcion", "Semillas a granel");
        valuesProducto3.put("ID_Proveedor", 1);
        valuesProducto3.put("EnOferta", 0);
        valuesProducto3.put("Imagen", "file:///android_asset/trigos.png");
        long idProducto3 = db.insert("Productos", null, valuesProducto3);

        ContentValues valuesProducto4 = new ContentValues();
        valuesProducto4.put("Nombre", "Cebada");
        valuesProducto4.put("Categoria", "CEREALES");
        valuesProducto4.put("Precio", 2);
        valuesProducto4.put("IVA", 21);
        valuesProducto4.put("Peso", 2);
        valuesProducto4.put("Stock", 500);
        valuesProducto4.put("Descripcion", "Semillas a granel");
        valuesProducto4.put("ID_Proveedor", 1);
        valuesProducto4.put("EnOferta", 0);
        valuesProducto4.put("Imagen", "file:///android_asset/cebadas.png");
        long idProducto4 = db.insert("Productos", null, valuesProducto4);

        ContentValues valuesProducto5 = new ContentValues();
        valuesProducto5.put("Nombre", "Leche de vaca");
        valuesProducto5.put("Categoria", "LACTEOS");
        valuesProducto5.put("Precio", 2);
        valuesProducto5.put("IVA", 10);
        valuesProducto5.put("Peso", 1);
        valuesProducto5.put("Stock", 200);
        valuesProducto5.put("Descripcion", "Leche fresca recien ordeñada");
        valuesProducto5.put("ID_Proveedor", 1);
        valuesProducto5.put("EnOferta", 0);
        valuesProducto5.put("Imagen", "file:///android_asset/lechedevacas.png");
        long idProducto5 = db.insert("Productos", null, valuesProducto5);

        ContentValues valuesProducto6 = new ContentValues();
        valuesProducto6.put("Nombre", "Queso de cabra");
        valuesProducto6.put("Categoria", "LACTEOS");
        valuesProducto6.put("Precio", 4);
        valuesProducto6.put("IVA", 21);
        valuesProducto6.put("Peso", 1);
        valuesProducto6.put("Stock", 100);
        valuesProducto6.put("Descripcion", "Queso suave y cremoso");
        valuesProducto6.put("ID_Proveedor", 1);
        valuesProducto6.put("EnOferta", 1);
        valuesProducto6.put("Imagen", "file:///android_asset/quesoparmesanos.png");
        long idProducto6 = db.insert("Productos", null, valuesProducto6);

        ContentValues valuesProducto7 = new ContentValues();
        valuesProducto7.put("Nombre", "Carne de Ternera");
        valuesProducto7.put("Categoria", "CARNES Y PESCADOS");
        valuesProducto7.put("Precio", 12);
        valuesProducto7.put("IVA", 21);
        valuesProducto7.put("Peso", 1);
        valuesProducto7.put("Stock", 50);
        valuesProducto7.put("Descripcion", "Filete tierno y jugoso");
        valuesProducto7.put("ID_Proveedor", 1);
        valuesProducto7.put("EnOferta", 0);
        valuesProducto7.put("Imagen", "file:///android_asset/carnedevacunos.png");
        long idProducto7 = db.insert("Productos", null, valuesProducto7);

        ContentValues valuesProducto8 = new ContentValues();
        valuesProducto8.put("Nombre", "Salmón");
        valuesProducto8.put("Categoria", "CARNES Y PESCADOS");
        valuesProducto8.put("Precio", 8);
        valuesProducto8.put("IVA", 21);
        valuesProducto8.put("Peso", 1);
        valuesProducto8.put("Stock", 80);
        valuesProducto8.put("Descripcion", "Salmón del Atlántico");
        valuesProducto8.put("ID_Proveedor", 1);
        valuesProducto8.put("EnOferta", 0);
        valuesProducto8.put("Imagen", "file:///android_asset/salmons.png");
        long idProducto8 = db.insert("Productos", null, valuesProducto8);

        ContentValues valuesProducto9 = new ContentValues();
        valuesProducto9.put("Nombre", "Zumo de naranja");
        valuesProducto9.put("Categoria", "BEBIDAS");
        valuesProducto9.put("Precio", 1);
        valuesProducto9.put("IVA", 10);
        valuesProducto9.put("Peso", 1);
        valuesProducto9.put("Stock", 300);
        valuesProducto9.put("Descripcion", "Zumo de naranja recién exprimido natural");
        valuesProducto9.put("ID_Proveedor", 1);
        valuesProducto9.put("EnOferta", 1);
        valuesProducto9.put("Imagen", "file:///android_asset/zumodenaranjas.png");
        long idProducto9 = db.insert("Productos", null, valuesProducto9);

        ContentValues valuesProducto10 = new ContentValues();
        valuesProducto10.put("Nombre", "Agua de coco");
        valuesProducto10.put("Categoria", "BEBIDAS");
        valuesProducto10.put("Precio", 1.5);
        valuesProducto10.put("IVA", 21);
        valuesProducto10.put("Peso", 1);
        valuesProducto10.put("Stock", 150);
        valuesProducto10.put("Descripcion", "natural, sin conservantes");
        valuesProducto10.put("ID_Proveedor", 1);
        valuesProducto10.put("EnOferta", 0);
        valuesProducto10.put("Imagen", "file:///android_asset/aguadecocos.png");
        long idProducto10 = db.insert("Productos", null, valuesProducto10);


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
