package com.example.edgar.asesoriasinformales;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import java.util.List;
import java.util.UUID;

public class TeacherStats extends AppCompatActivity implements View.OnClickListener {

    private List<Asesoria> asesoriaList;
    private BluetoothDevice mBTDevice;
    private BluetoothConnectionService mBluetoothConnection;
    private static final String appName = "MYAPP";

    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_stats);
        final Button miBoton = (Button) findViewById(R.id.botonVerAsesorias);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);
        mBluetoothConnection = new BluetoothConnectionService(getApplicationContext());

        getTiempoAsesorias();
        miBoton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), HistorialActivity.class);
                startActivity(intent);


            }
        });
    }

    public void getTiempoAsesorias(){

        RequestQueue queue = Volley.newRequestQueue(TeacherStats.this);
        String databaseURLAsesorias = "https://tutorias-220600.firebaseio.com/";
        String query = databaseURLAsesorias + ".json";
        asesoriaList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Entre al response!!!", "*******Otro MENSAJEE!!!");
                        int acumTiempo = 0;


                        try {
                            JSONArray contacts = response.getJSONArray("asesorias2");
                            for (int i = 0; i < contacts.length(); i++) {
                                if (contacts.get(i).toString().equals("null") == false) {
                                    JSONObject c = contacts.getJSONObject(i);

                                    String alumno = c.getString("alumno");
                                    String asesor = c.getString("asesor");
                                    String horas = c.getString("duracion");
                                    String inicio = c.getString("inicio");
                                    String fin = c.getString("fin");

                                    asesoriaList.add(new Asesoria(alumno, asesor, inicio, fin, horas));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int x=0; x<asesoriaList.size(); x++)
                        {
                            char num = asesoriaList.get(x).getHoras().charAt(0);
                            int numInt = num - '0';
                            acumTiempo = acumTiempo + numInt;
                        }

                        final TextView tiempoTotalAsesorias = (TextView)findViewById(R.id.tiempoAsesorias);
                        tiempoTotalAsesorias.setText(""+acumTiempo+" hrs");

                        final TextView promedioAsesoria = (TextView)findViewById(R.id.promedioAsesorias);
                        promedioAsesoria.setText(""+acumTiempo/asesoriaList.size()+" hrs");

                        final TextView cantidadAsesorias = (TextView)findViewById(R.id.asesoriasImpartidas);
                        cantidadAsesorias.setText(""+asesoriaList.size());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // : Handle error


                    }
                });
        queue.add(jsonObjectRequest);

    }




    public void getPromedioPorAsesoria(){

    }

    public void getAsesoriasImpartidas(){

    }

    @Override
    public void onClick(View v) {

    }

    public void activarDisponibilidad(View view) {
        Switch s = (Switch)findViewById(R.id.switch1);
        if(s.isChecked()){
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
//            mBluetoothConnection.startClient(mBTDevice,MY_UUID_INSECURE);
        }
        else {
//            Intent disableDiscoverIntent = new Intent(BluetoothAdapter.ACTION_)
        }

    }


    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
//                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
//                //case2: creating a bone
//                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
//                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
//                }
//                //case3: breaking a bond
//                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
//                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
//                }
            }
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver4);
    }
}
