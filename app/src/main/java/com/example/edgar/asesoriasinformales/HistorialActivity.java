package com.example.edgar.asesoriasinformales;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.edgar.asesoriasinformales.connector.DatabaseConnector;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity implements View.OnClickListener{
    private AsesoriaAdapter asesoriaAdapter;
    private List<Asesoria> asesoriaList;
    public boolean conectado=false;
    public int numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        final Button miBoton= (Button) findViewById(R.id.boton);
        miBoton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Plip le pique al boton","que maduro");
                for(int i=0;i<5;i++){
                    numero=i;
                    Log.e("Ciclo","ENTRE AL CICLO DE LOS NUMEROS!!!!");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Log.e("Estoy en el hilo?","Estas en el hilo");
                            if(!conectado){

                                conectado=prepararMetodo(numero);
                            }

                        }
                    }, 5000);
                }
                if(!conectado){


                }



            }
        });
        fillTable();
        asesoriaAdapter= new AsesoriaAdapter(this, asesoriaList);
        asesoriaAdapter.notifyDataSetChanged();
        final ListView asesoriaView = (ListView) findViewById(R.id.asesoria_view);
        asesoriaView.setAdapter(asesoriaAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton:
                Log.e("Plip le pique al boton","que maduro");
                for(int i=0;i<5;i++){
                    Log.e("Ciclo","ENTRE AL CICLO DE LOS NUMEROS!!!!");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Log.e("Estoy en el hilo?","Estas en el hilo");
                            if(!conectado){
                                conectado=prepararMetodo(4);
                            }

                        }
                    }, 1000);
                }
                if(!conectado){
                    Toast.makeText(getApplicationContext(), "Error al conectar con alumno. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();

                }



                break;
        }
        }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void fillTable(){
        RequestQueue queue = Volley.newRequestQueue(HistorialActivity.this);
        String databaseURLAsesorias = "https://tutorias-220600.firebaseio.com/";

            Asesoria asesoria = new Asesoria();
        // Request a string response from the provided URL.

        for(int i=1; i<=3;i++) {
        String query = databaseURLAsesorias + "asesorias/"+i+".json";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    asesoriaList = new ArrayList<>();


                    try {
                        asesoria.alumno= response.getString("alumno");
                        asesoria.asesor= response.getString("asesor");
                        asesoria.horas= response.getString("duracion");
                        asesoria.horas= response.getString("inicio");
                        asesoria.horas= response.getString("fin");
                        asesoriaList.add(asesoria);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                        }



            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error requesting user info. Please check Internet connection and try again", Toast.LENGTH_LONG).show();
                    Log.e("Check User Error", error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }





    }


public boolean prepararMetodo(int n){
if(n==4){
    Toast.makeText(getApplicationContext(), "Error al conectar con alumno. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();
}
        return false;
}
}
