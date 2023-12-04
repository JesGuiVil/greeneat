package com.example.basedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MostrarProductoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MostrarProductoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MostrarProductoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MostrarProductoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MostrarProductoFragment newInstance(String param1, String param2) {
        MostrarProductoFragment fragment = new MostrarProductoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_producto, container, false);

        // Find views in the inflated layout
        LinearLayout linearLayoutContenido = view.findViewById(R.id.linearLayoutContenido);

        // Replace this with your actual database helper class
        DbHelper dbHelper = new DbHelper(requireContext());

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("Productos", null, null, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extract product information from the cursor
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
                    byte[] imagenEnBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("Imagen"));

                    // Convert byte array to Bitmap
                    Bitmap imagenBitmap = BitmapFactory.decodeByteArray(imagenEnBytes, 0, imagenEnBytes.length);

                    // Create new ImageView and set the image using Glide
                    ImageView imageViewProducto = new ImageView(requireContext());
                    imageViewProducto.setLayoutParams(new LinearLayout.LayoutParams(
                            100, // width in pixels
                            100  // height in pixels
                    ));
                    Glide.with(this)
                            .load(imagenBitmap)
                            .override(100, 100) // Set your desired size
                            .into(imageViewProducto);

                    // Create new TextView for product name
                    TextView textViewNombre = new TextView(requireContext());
                    textViewNombre.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    ));
                    textViewNombre.setText(nombre);
                    textViewNombre.setTextSize(20);

                    // Create a container for ImageView and TextView
                    LinearLayout itemLayout = new LinearLayout(requireContext());
                    itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                    itemLayout.addView(imageViewProducto);
                    itemLayout.addView(textViewNombre);

                    // Add the container to the main LinearLayout
                    linearLayoutContenido.addView(itemLayout);

                } while (cursor.moveToNext());
            } else {
                // Handle case when no records are found
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Handle error getting records
        }

        return view;
    }
}
