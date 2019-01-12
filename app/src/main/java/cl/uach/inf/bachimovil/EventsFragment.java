package cl.uach.inf.bachimovil;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements AsyncResponse{
    String response;
    String usr;
    TextView mostrarConsulta;
    Button btnConsultar;
    View view;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_eventos, container, false);
        mostrarConsulta = view.findViewById(R.id.resultadoConsulta);
        btnConsultar = view.findViewById(R.id.btnConsulta);


        return view;

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {
        if(v.getId() == R.id.btnConsulta) {

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
            mostrarConsulta.setText(jsonArray.toString());
            /*String text = "";
            for(int i=0;i<jsonArray.length();i++){
                text += jsonArray.getJSONObject(i).getString("name")+"\n";
            }*/

            //mostrarConsulta.setText(text);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
