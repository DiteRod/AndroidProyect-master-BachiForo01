package cl.uach.inf.bachimovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Forum extends AppCompatActivity {

    TextView titulo, descripcion, result, name;
    EditText comentario;
    String id_posts;
    Button insert, get;
    String insertUrl = "http://172.20.53.52/cursoPHP/insertComment.php";
    String showUrl = "http://172.20.53.52/cursoPHP/showComment.php";
    RequestQueue requestQueue;
    RecyclerView recycler;
    ArrayList<String> ListaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        titulo = (TextView) findViewById(R.id.TextoPub);
        descripcion = (TextView) findViewById(R.id.textView2);
        comentario = (EditText) findViewById(R.id.editText);
        name = (TextView) findViewById(R.id.textView5);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            titulo.setText(b.getString("title"));
            descripcion.setText(b.getString("description"));
            id_posts = b.getString("id_post");
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GetDatos();
    }

    public void InserDatos(View view) {
        try {
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
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("Descripcion", comentario.getText().toString());
                    parameters.put("id_post", id_posts);

                    return parameters;
                }
            };
            requestQueue.add(request);
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GetDatos();
    }



    public void GetDatos() {
        System.out.println("ww");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray comments = response.getJSONArray("comentarios");
                    CargarComentarios(comments);
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

    public void CargarComentarios(JSONArray comments) {
        ListaComentarios = new ArrayList<>();
        for (int i = 0; i < comments.length(); i++) {
            try {
                JSONObject comment = comments.getJSONObject(i);
                String id_post = comment.getString("id_post");
                if (id_posts.equals(id_post)) {
                    String description = comment.getString("Descripcion");
                    ListaComentarios.add(description);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapt(ListaComentarios);
    }

    public void adapt(ArrayList<String> Lista) {
        AdapterDatos adapter = new AdapterDatos(Lista);
        recycler.setAdapter(adapter);
    }
}
