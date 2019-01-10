package cl.uach.inf.bachimovil;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends Fragment implements AsyncResponse {

    View view;

    public MaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_material, container, false);
        

        return view;
    }



    @Override
    public void obtainServiceResult(JSONObject jsonObject) {
        try
        {



        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
