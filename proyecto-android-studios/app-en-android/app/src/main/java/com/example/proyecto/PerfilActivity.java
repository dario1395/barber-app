package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{

    TextView introduzcaView, cit, serv;
    Button btnCancelar;
    String usuario;

    RequestQueue requestQueue;

    //URL del PHP para registrar datos
    private static final String URL = "http://192.168.0.10/barber/buscarcita.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //TextViews
        introduzcaView = findViewById(R.id.introduzcaView);
        cit = findViewById(R.id.cita);
        serv = findViewById(R.id.servicio);

        //Buttons
        btnCancelar = findViewById(R.id.btnCancelar);

        //Volley
        requestQueue = Volley.newRequestQueue(this);

        //Obtenemos el nombre de usuario del otro activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }

        verCita();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarCita(usuario);

            }
        });
    }

    //Método para visualizar la cita
    private void verCita(){
        String URL = "http://192.168.0.10/barber/buscarcitas.php?username=" +usuario;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String cita, servicio;
                        try {
                            cita = response.getString("cita");
                            servicio = response.getString("servicio");

                            cit.setText(cita);
                            serv.setText(servicio);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        //Solicitud al RequestQueue
        requestQueue.add(jsonObjectRequest);
    }


    //Cuando clicquemos en el botón cancelar, se borra la cita
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnCancelar) {

            //Obtenemos el nombre del usuario
            Bundle datoEnviado = getIntent().getExtras();
            String usuario = null;

            if(datoEnviado!=null){
                usuario = datoEnviado.getString("usuario");

            }

            cancelarCita(usuario);

        }

    }

    //Método para que el usuario pueda cancelar su cita
    private void cancelarCita(final String user) {
        String URL2 = "http://192.168.0.10/barber/cancelar.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PerfilActivity.this, response, Toast.LENGTH_LONG).show();
                        //Limpiamos los TextViews
                        cit.setText("No tienes ninguna cita pendiente");
                        serv.setText("");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PerfilActivity.this, "Hubo un error y no se pudo cancelar", Toast.LENGTH_LONG).show();
                    }
                }

        ){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                return params;
            }
        };


        requestQueue.add(stringRequest);
    }

}