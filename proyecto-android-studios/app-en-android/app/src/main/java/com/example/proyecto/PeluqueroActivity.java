package com.example.proyecto;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PeluqueroActivity extends AppCompatActivity implements View.OnClickListener {

    DatePickerDialog datePickerDialog;
    Button btnSelectPelu;
    EditText btnFecha;
    Spinner spinnerPelu, spinnerHoras, spinnerServicio;
    TextView textView, nomUsuario;

    RequestQueue requestQueue;

    //URL del PHP para registrar datos
    private static final String URL2 = "http://192.168.0.10/barber/cita.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peluquero);

        //Spinners
        spinnerPelu = findViewById(R.id.spinnerPelu);
        spinnerHoras = findViewById(R.id.spinnerHoras);
        spinnerServicio = findViewById(R.id.spinnerServicio);

        //Buttons
        btnSelectPelu = findViewById(R.id.btnSelectPelu);
        btnFecha = findViewById(R.id.btnFecha);

        //TextViews
        textView = findViewById(R.id.textView);
        nomUsuario = findViewById(R.id.nomUsuario);

        //Array para los peluqueros
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.peluqueros, android.R.layout.simple_spinner_dropdown_item);

        spinnerPelu.setAdapter(adapter);

        //Array para los horarios
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,
                R.array.hora, android.R.layout.simple_spinner_dropdown_item);

        spinnerHoras.setAdapter(adapter2);

        //Array para los servicios
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,
                R.array.servicios, android.R.layout.simple_spinner_dropdown_item);

        spinnerServicio.setAdapter(adapter3);

        Bundle datoEnviado = getIntent().getExtras();
        String usuario = null;

        if(datoEnviado!=null){
            usuario = datoEnviado.getString("usuario");
            nomUsuario.setText(usuario);

        }


        requestQueue = Volley.newRequestQueue(this);


        btnSelectPelu.setOnClickListener(this);


        //Mostrar el día de hoy
        btnFecha.setText(getTodaysDate());
        
        initDatePicker();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PeluqueroActivity.this, LoginActivity.class));
            }
        });

    }

    //Para que muestre el día actual
    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    //Método para que nos aparezca el calendario a nuestro gusto
    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnFecha.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        //Para que empiece por la fecha actual y no permita escoger anteriores
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
    }

    //Convierte la fecha en String
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    //Convierte los números de los meses en Escrito
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "ENERO";
        if(month == 2)
            return "FEBRERO";
        if(month == 3)
            return "MARZO";
        if(month == 4)
            return "ABRIL";
        if(month == 5)
            return "MAYO";
        if(month == 6)
            return "JUNIO";
        if(month == 7)
            return "JULIO";
        if(month == 8)
            return "AGOSTO";
        if(month == 9)
            return "SEPTIEMBRE";
        if(month == 10)
            return "OCTUBRE";
        if(month == 11)
            return "NOVIEMBRE";
        if(month == 12)
            return "DICIEMBRE";

        //Por defecto saldrá Enero
        return "ENERO";
    }

    //Abre el calendario para seleccionar fecha
    public void openDatePicker(View view) {

        datePickerDialog.show();


    }

    //Obtenemos los parámetros
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnSelectPelu) {
            String peluquero = spinnerPelu.getSelectedItem().toString().trim();
            String fecha = btnFecha.getText().toString().trim();
            String hora = spinnerHoras.getSelectedItem().toString().trim();
            String servicio = spinnerServicio.getSelectedItem().toString().trim();

            //Obtenemos el nombre del usuario
            Bundle datoEnviado = getIntent().getExtras();
            String usuario = null;

            if(datoEnviado!=null){
                usuario = datoEnviado.getString("usuario");

            }


            createCita(peluquero, fecha, hora, servicio, usuario);

        }
    }

    //Creamos la cita
    private void createCita(final String peluquero, final String fecha, final String hora, final String servicio, final String usuario) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PeluqueroActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(PeluqueroActivity.this, "Hubo un error al registrar la cita", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            //Enviamos los datos a la BD
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Tienen que ser el mismo nombre del param que en Visual Studios
                params.put("cita", peluquero + " " + hora + " " + fecha);
                params.put("servicio", servicio);
                params.put("username", usuario);


                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}