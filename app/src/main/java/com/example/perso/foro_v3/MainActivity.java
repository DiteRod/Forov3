package com.example.perso.foro_v3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
     EditText titulo, descripcion,taqs;
    Button insert, two;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.0.6/cursoPHP/insertStudent.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titulo = (EditText) findViewById(R.id.title_a);
        descripcion = (EditText) findViewById(R.id.description_a);
        taqs = (EditText) findViewById(R.id.editText3);
        insert = (Button) findViewById(R.id.pub_a);
        two = (Button) findViewById(R.id.foro_a);
        requestQueue = Volley.newRequestQueue(getApplicationContext());




        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters  = new HashMap<String, String>();
                        parameters.put("titulo",titulo.getText().toString());
                        parameters.put("descripcion",descripcion.getText().toString());
                        parameters.put("taqs",taqs.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }

        });
    }
    public void Foro(View view){
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }



}

