package com.example.edgar.asesoriasinformales;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HistorialAlumnoActivity extends AppCompatActivity implements View.OnClickListener{
    private AsesoriaAdapter asesoriaAdapter;
    private List<Asesoria> asesoriaList;
    public boolean conectado=false;
    public int numero;

    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<NearbyDevice> nearbyDevices = new ArrayList<NearbyDevice>();
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private BluetoothConnectionService mBluetoothConnection;
    private BluetoothDevice mBTDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_alumno);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        fillTable();

        asesoriaAdapter = new AsesoriaAdapter(this, asesoriaList);
        asesoriaAdapter.notifyDataSetChanged();
        //  final ListView asesoriaView = (ListView) findViewById(R.id.asesoria_view);
        //  asesoriaView.setAdapter(asesoriaAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton:
                for(int i=0;i<5;i++){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if(!conectado){
                                conectado=prepararMetodo(4);
                            }

                        }
                    }, 1000);
                }
                if(!conectado){
                    Toast.makeText(getApplicationContext(), "Error al conectar con asesor. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();

                }



                break;
        }
        }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void fillTable(){
        RequestQueue queue = Volley.newRequestQueue(HistorialAlumnoActivity.this);
        String databaseURLAsesorias = "https://tutorias-220600.firebaseio.com/";
        String query = databaseURLAsesorias + ".json";
        //   Asesoria asesoria;
        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Entre al response!!!", "*******Otro MENSAJE!!!");

                        asesoriaList = new ArrayList<>();
                        try {
                            Log.e("Entre al try!!!", "******Otro MENSAJEEEEEEEEEEEEEEE!!!");
                            // Getting JSON Array node
                            JSONArray contacts = response.getJSONArray("asesorias");

                            //  Log.e("Contacts",contacts.toString());
                            //  Log.e("Contacts lenght",""+contacts.length());
                            // looping through All Contacts


                            for (int i = 0; i < contacts.length(); i++) {
                                //               Log.e("Entre al ciclo!!!","*******"+contacts.get(i).toString());
                                if (contacts.get(i).toString().equals("null") == false) {

                                    JSONObject c = contacts.getJSONObject(i);
                                    //   Log.e("Entre al ciclo!!!","MENSAJE");

                                    String alumno = c.getString("alumno");
                                    String asesor = c.getString("asesor");
                                    String horas = c.getString("duracion");
                                    String inicio = c.getString("inicio");
                                    String fin = c.getString("fin");

                                    asesoriaList.add(new Asesoria(alumno, asesor, inicio, fin, horas));
                                }

                            }
                        } catch (final JSONException e) {
                            Log.e("Estoy tronando por aqui", e.getMessage());
                            Log.e("Estoy tronando por aqui", e.toString());
                        }

                        TableLayout table = (TableLayout) findViewById(R.id.table_layout);


                        for (int x = 0; x < asesoriaList.size(); x++) {

                            TableRow row = new TableRow(HistorialAlumnoActivity.this);

                            TextView taskdate = new TextView(HistorialAlumnoActivity.this);
                            taskdate.setTextSize(10);
                            taskdate.setText(asesoriaList.get(x).getAsesor()+"\t\t\t\t");
                            row.addView(taskdate);

                            TextView title = new TextView(HistorialAlumnoActivity.this);
                            title.setTextSize(10);
                            title.setText(asesoriaList.get(x).getInicio()+"\t\t\t\t");
                            row.addView(title);

                            TextView taskhour = new TextView(HistorialAlumnoActivity.this);
                            taskhour.setTextSize(10);
                            taskhour.setText(asesoriaList.get(x).getFin()+"\t\t\t\t");
                            row.addView(taskhour);

                            TextView description = new TextView(HistorialAlumnoActivity.this);
                            description.setTextSize(10);
                            description.setText(asesoriaList.get(x).getHoras());
                            row.addView(description);

                            table.addView(row);

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


public boolean prepararMetodo(int n){
if(n==4){
    Toast.makeText(getApplicationContext(), "Error al conectar con asesor. Revisar que ambos tengan al comunicacion prendida", Toast.LENGTH_LONG).show();
}
        return false;
}


    public void solicitarAsesoria(View view) {
        checkBTPermissions();

        mBluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBroadcastReceiver3, filter);
    }

    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = ActivityCompat.checkSelfPermission(this,"Manifest.permissions.ACCESS_FINE_LOCATION");
            permissionCheck += ActivityCompat.checkSelfPermission(this,"Manifest.permissions.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 1001);
            }
        }
    }

    // Este broadcast receiver se encarga de listar los dispositivos encontrados
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            // Cuando encuentra un dispositivo
            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                // Dispositivo encontrado
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                // Fuerza de la señal
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                NearbyDevice nd = new NearbyDevice(device,rssi);
                nearbyDevices.add(nd);
            }
            // Cuando termina de descubrir
            if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                // Debe encontrar el que tenga mejor conexión
                Collections.sort(nearbyDevices);
                // Este es el dispositivo más cercano
                BluetoothDevice nearest = nearbyDevices.get(0).getDevice();
                mBTDevice = nearest;

                mBTDevice.createBond();
                mBluetoothConnection = new BluetoothConnectionService(getApplicationContext());
                Log.i("Discovery",nearest.getName()+" "+nearbyDevices.get(0).getSignalStrength());
                mBluetoothConnection.startClient(mBTDevice,MY_UUID_INSECURE);
                mBluetoothConnection.write("Hello World".getBytes());
                Log.i("Bluetooth Write", "Hello World");
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d("BC4", "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d("BC4", "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d("BC4", "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
    }
}
