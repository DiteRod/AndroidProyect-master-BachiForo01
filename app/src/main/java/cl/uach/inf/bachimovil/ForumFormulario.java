package cl.uach.inf.bachimovil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForumFormulario extends AppCompatActivity {
    TextView result,Taqs;
    EditText titulo, descripcion;
    Button insert, two;
    ImageButton tagsButton;

    JSONArray arr = new JSONArray();
    String insertUrl = "http://172.20.53.52/cursoPHP/insertStudent.php";
    RequestQueue requestQueue;

    String[] TAG_LIST = {"Video","Guía","Tarea","Consulta","Álgebra","Geometría", "Cálculo","Lineal","Química", "Dyre",
            "Cálculo 2","EDO","Física 1","Física 2","Metodos Numéricos","Estadística","Física 3"};
    boolean[] checkedTags;
    ArrayList<Integer> userTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_formulario);
        titulo = (EditText) findViewById(R.id.title_a);
        descripcion =(EditText) findViewById(R.id.descripcion_a);
        Taqs =(TextView) findViewById(R.id.taqs_a);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        insert = (Button)findViewById(R.id.insertar_a);
        tagsButton = (ImageButton) findViewById(R.id.btn);

        checkedTags = new boolean[TAG_LIST.length];
        userTags = new ArrayList<>();
        tagsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ForumFormulario.this);
                mBuilder.setTitle("Tags");
                mBuilder.setMultiChoiceItems(TAG_LIST, checkedTags, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {

                        if(isChecked){

                            //if(!userTags.contains(position))
                            userTags.add(position);
                            //else userTags.remove(position);

                        }
                        else{
                            userTags.remove(userTags.indexOf(position));
                        }


                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        String selectedTagsText = "";

                        for (int i = 0; i < userTags.size(); i++){

                            selectedTagsText += TAG_LIST[userTags.get(i)];
                            if (i != userTags.size() - 1) selectedTagsText += ", ";

                        }
                        Taqs.setText(selectedTagsText);

                    }
                });

                mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Limpiar" +
                        "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < checkedTags.length; i++) checkedTags[i] = false;
                        userTags.clear();
                        Taqs.setText("");

                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }

        });







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
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("titulo", titulo.getText().toString());
                        parameters.put("descripcion", descripcion.getText().toString());
                        parameters.put("taqs", Taqs.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
                Intent intent = new Intent(view.getContext(),Main.class);
                startActivity(intent);
            }



        });
    }





}




