package cl.uach.inf.bachimovil;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment implements AsyncResponse {
    String response;
    String usr;
    EditText titulo, descripcion,taqs;
    Button insert, two;
    TextView mostrarConsulta;
    Button btnConsultar;
    Button btnInsertar;
    View view;
    JSONArray arr = new JSONArray();
    JSONArray POSTS;
    ArrayList<Post> List;
    Adaptador adapter;
    RecyclerView recycler;
    TextView Taqs;
    Button Buscardatos;
    FloatingActionButton button;
    ImageButton tagsButton;
    TextView selectedTags;
    boolean[] checkedTags;
    ArrayList<Integer> userTags;
    String[] TAG_LIST = {"Video","Guía","Tarea","Consulta","Álgebra","Geometría", "Cálculo","Lineal","Química", "Dyre",
            "Cálculo 2","EDO","Física 1","Física 2","Metodos Numéricos","Estadística","Física 3"};

    //String [] TAG_LIST;


    public ForumFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_foro, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        tagsButton = (ImageButton) view.findViewById(R.id.btn);
        Taqs = (TextView) view.findViewById(R.id.taqsDeBusqueda_b);
        userTags = new ArrayList<>();

        Buscardatos = view.findViewById(R.id.buscarDatos);
        button = view.findViewById(R.id.button);
        checkedTags = new boolean[TAG_LIST.length];





        tagsButton.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View view){

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ForumFragment.this.getContext());
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





        cargar();
        //btnConsultar = view.findViewById(R.id.btnConsulta_a);









        return view;
    }

    private void cargar() {
        ServiceManager serviceManager = new ServiceManager(this.getActivity(),this);
        serviceManager.callService("http://172.20.53.52/cursoPHP/connect.php");

    }
    public void obtainServiceResult(JSONObject jsonObject) {
        try
        {
            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            CargarListView(jsonArray);


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }



    /*public void cargar(){
        try {
            FileInputStream is = getContext().openFileInput("Post.json");
            String result = IOHelper.stringFromStream(is);
            JSONArray posts = new JSONArray(result);
            CargarListView(posts);
        } catch (JSONException e) {

        } catch (FileNotFoundException k) {
        }


    }*/
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
    public void onClick(View v) {
        if (v.getId() == R.id.buscarDatos) {
            String taqsDeBusqueda = Taqs.getText().toString();
            ArrayList<Post> P = new ArrayList<>();
            ArrayList<String> listTaqsDeBusqueda;
            listTaqsDeBusqueda = IOHelper.StringtoArrayList(taqsDeBusqueda);
            JSONArray listaTaqsBusqueda = new JSONArray(listTaqsDeBusqueda);
            if (!taqsDeBusqueda.equals("")) {
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
            } else {
                CargarListView(POSTS);
            }
        }
        else if(v.getId()==R.id.button){
            startActivity(new Intent(v.getContext(),ForumFormulario.class));

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
                Intent visorDetalles = new Intent(v.getContext(),Forum.class);
                visorDetalles.putExtra("title",post.getTitulo());
                visorDetalles.putExtra("description",post.getDescripcion());
                visorDetalles.putExtra("id_post",post.getId());
                startActivity(visorDetalles);





            }
        });



    }


}


/*public void onClick(View v) {
        if (v.getId() == R.id.obtenerDatos) {
            try {
                FileInputStream is = getContext().openFileInput("Post.json");
                String result = IOHelper.stringFromStream(is);

                JSONArray posts = new JSONArray(result);
                mostrarConsulta.setText(posts.toString());
            } catch (JSONException e) {

            } catch (FileNotFoundException k) {
            }
        } else if (v.getId() == R.id.enviarDatos) {
            /*String title = "titulo";
            String description = "description";
            String taqq = "taqs";
            ArrayList<String> taq;
            taq = IOHelper.StringtoArrayList(taqq);


            try {
               /* JSONObject obj = new JSONObject();
                obj.put("titulo","Titulo");
                obj.put("descripcion","Desarrollo");
                obj.put("taqs","Taqs");
                arr.put(obj);

            }catch(JSONException e){}
            IOHelper.writeToFile(this.getActivity(),"Post.json",arr.toString());
        }
    }*/




    //Servidor
    /*
    private void cargar() {
        ServiceManager serviceManager = new ServiceManager(this.getActivity(),this);
        serviceManager.callService("http://146.83.216.206/info104/resp/getCourseList.php?tags=");

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {
            if(v.getId() == R.id.btnConsulta_a) {

            int randomNum = (int) (24*Math.random()+1);

            ServiceManager serviceManager = new ServiceManager(this.getActivity(),this);
            serviceManager.callService("http://146.83.216.206/info104/resp/getCourseList.php?tags=");

        }

    }

    @Override
    public void obtainServiceResult(JSONObject jsonObject) {
        try
        {
            JSONArray jsonArray = jsonObject.getJSONArray("courses");
            mostrarConsulta.setText(jsonArray.toString()+"2");
            /*String text = "";
            for(int i=0;i<jsonArray.length();i++){
                text += jsonArray.getJSONObject(i).getString("name")+"\n";
            }

            //mostrarConsulta.setText(text);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }*/



