package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HubActivity extends AppCompatActivity {

    Button btnUbicacion, btnCita, btnPerfil, btnProductos;
    TextView nomUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        btnUbicacion = findViewById(R.id.btnUbicacion);
        btnCita = findViewById(R.id.btnCita);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnProductos = findViewById(R.id.btnProductos);
        nomUsuario = findViewById(R.id.nomUsuario);

        Bundle datoEnviado = getIntent().getExtras();
        String usuario = null;

        if(datoEnviado!=null){
            usuario = datoEnviado.getString("usuario");
            nomUsuario.setText(usuario);

        }

        //Al pulsar nos lleva a la pantalla Ubicación
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HubActivity.this, UbicacionActivity.class));
            }
        });

        //Al pulsar nos lleva a la pantalla Productos
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HubActivity.this, ProductosActivity.class));

            }
        });


        //Al pulsar nos lleva a la pantalla Citas
        btnCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PeluqueroActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("usuario", nomUsuario.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //Nos lleva a la pantalla Perfil
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("usuario", nomUsuario.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}