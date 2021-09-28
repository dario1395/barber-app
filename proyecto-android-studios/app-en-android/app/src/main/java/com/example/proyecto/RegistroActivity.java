package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nombreETRegistro, apellidosETRegistro, usuarioETRegistro, emailETRegistro, passwordETRegistro, phoneETResgistro;
    Button btnRegistrer;
    TextView TextViewInicio;

    RequestQueue requestQueue;

    //URL del PHP para registrar datos
    private static final String URL1 = "http://192.168.0.10/barber/registrer.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        requestQueue = Volley.newRequestQueue(this);
        //EditTexts
        nombreETRegistro = findViewById(R.id.nombreETRegistro);
        apellidosETRegistro = findViewById(R.id.apellidosETRegistro);
        passwordETRegistro = findViewById(R.id.passwordETRegistrer);
        phoneETResgistro = findViewById(R.id.phoneETRegistro);
        emailETRegistro = findViewById(R.id.emailETRegistro);
        usuarioETRegistro = findViewById(R.id.usuarioETRegistro);
        TextViewInicio = findViewById(R.id.TextViewInicio);

        //Buttons
        btnRegistrer = findViewById(R.id.btnRegistrer);


        btnRegistrer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnRegistrer) {
            String nombre = nombreETRegistro.getText().toString().trim();
            String apellidos = apellidosETRegistro.getText().toString().trim();
            String phone = phoneETResgistro.getText().toString().trim();
            String usuario = usuarioETRegistro.getText().toString().trim();
            String email = emailETRegistro.getText().toString().trim();
            String password = passwordETRegistro.getText().toString().trim();

            createUser(nombre, apellidos, usuario, password, email, phone);

        }

}

    //Método para crear un nuevo Usuario
    private void createUser(final String nombre, final String apellidos, final String usuario, final String password, final String email, final String phone) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Toast.makeText(RegistroActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Tienen que ser el mismo nombre del param que en Visual Studios
                params.put("nombre", nombre);
                params.put("apellidos", apellidos);
                params.put("usuario", usuario);
                params.put("password", password);
                params.put("email", email);
                params.put("phone", phone);

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void irInicio(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
