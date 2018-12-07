package com.example.perso.foro_v3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    RecyclerView recycler;
    EditText Taqs;
    TextView result, Texto;
    RequestQueue requestQueue;
    JSONArray posts;
    Adaptador adapter;
    ArrayList List;
    JSONArray POSTS;
    String showUrl = "http://192.168.0.6/cursoPHP/showStudents.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //result = (TextView) findViewById(R.id.textView2);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        Taqs = (EditText) findViewById(R.id.taqsDeBusqueda_b);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        cargar();



    }
    public void cargar() {
        System.out.println("ww");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray posts = response.getJSONArray("posts");
                    CargarListView(posts);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);



    }
    public void CargarListView(JSONArray posts){
       POSTS = posts;
        ArrayList<Post> Listado= new ArrayList<>();

        for (int i = 0; i < posts.length(); i++) {
            try {
                JSONObject post = posts.getJSONObject(i);
                String id = post.getString("id_post");
                String title = post.getString("titulo");
                String description = post.getString("descripcion");
                String taqs = post.getString("taqs");
                Post pub = new Post(id,title,description,taqs);
                Listado.add(pub);
            }catch(JSONException e) {
                e.printStackTrace();
            }
        }
       Adapt(Listado);

    }
    public void getPost(View v){
    String taqsDeBusqueda = Taqs.getText().toString();
    ArrayList<Post> P = new ArrayList<>();
    ArrayList<String> listTaqsDeBusqueda;
    listTaqsDeBusqueda= IOHelper.StringtoArrayList(taqsDeBusqueda);
    JSONArray listaTaqsBusqueda= new JSONArray(listTaqsDeBusqueda);
    if(!taqsDeBusqueda.equals("")) {
        try {
            for (int i = 0; i < POSTS.length(); i++) {
                boolean B = true;
                int j = 0;
                JSONObject post = POSTS.getJSONObject(i);
                String listaTaqsObject_a = post.getString("taqs");
                ArrayList<String> listaTaqsObject_b = IOHelper.StringtoArrayList(listaTaqsObject_a);
                JSONArray listaTaqsObject = new JSONArray(listaTaqsObject_b);
                while (B && j < listaTaqsObject.length()) {
                    int k = 0;
                    while (B && k < listaTaqsBusqueda.length()) {
                        if (listaTaqsObject.getString(j).equals(listaTaqsBusqueda.getString(k))) {
                            B = false;
                        }
                        k++;
                    }

                    if (!B) {
                        String id = post.getString("id_post");
                        String title = post.getString("titulo");
                        String description = post.getString("descripcion");
                        String taqs = post.getString("taqs");

                        Post po = new Post(id, title, description, taqs);
                        P.add(po);
                    }
                    j++;
                }

            }
            Adapt(P);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    else{
        CargarListView(POSTS);
    }
    }


    public void Adapt(ArrayList<Post> Lista) {
        List = Lista;
        adapter = new Adaptador(List);
        recycler.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post  = (Post) List.get(recycler.getChildAdapterPosition(v));
                Intent visorDetalles = new Intent(v.getContext(),Main3Activity.class);
                visorDetalles.putExtra("title",post.getTitulo());
                visorDetalles.putExtra("description",post.getDescripcion());
                visorDetalles.putExtra("id_post",post.getId());
                startActivity(visorDetalles);





            }
        });


    }




}

