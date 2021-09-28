package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity {

    TextView tvCantProductos;
    Button btnVerCarro;
    RecyclerView rvListaProductos;
    AdaptadorProductos adaptador;

    List<Producto> listaProductos = new ArrayList<>();
    List <Producto> carroCompras = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        getSupportActionBar().hide();

        tvCantProductos = findViewById(R.id.tvCantProductos);
        btnVerCarro = findViewById(R.id.btnVerCarro);
        rvListaProductos = findViewById(R.id.rvListaProductos);
        rvListaProductos.setLayoutManager(new GridLayoutManager(ProductosActivity.this, 1));

        listaProductos.add(new Producto("1", "Cera", "Fijador para el pelo", 10.0));
        listaProductos.add(new Producto("2", "Aceite para barba", "Aceite para suavizar la barba", 20.0));
        listaProductos.add(new Producto("3", "Corte de pelo", "Podrá seleccionar hora y fecha", 15.0));
        listaProductos.add(new Producto("4", "Corte de pelo y barba", "Podrá seleccionar hora y fecha", 25.0));
        listaProductos.add(new Producto("5", "Corte de barba", "Podrá seleccionar hora y fecha", 15.0));

        adaptador = new AdaptadorProductos(ProductosActivity.this, tvCantProductos, btnVerCarro, listaProductos, carroCompras);
        rvListaProductos.setAdapter(adaptador);
    }
}