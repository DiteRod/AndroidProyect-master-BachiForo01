package cl.uach.inf.bachimovil;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import android.support.v4.app.Fragment;


public class ServiceManager extends AsyncTask<String,Integer,String> {

    private AsyncResponse delegate;
    private AlertDialog alertDialog;
    private WeakReference<Activity> activityWeakReference;


    public ServiceManager(Activity activity, AsyncResponse delegate) {
        activityWeakReference = new WeakReference<>(activity);
        this.delegate = delegate;

    }

    public void callService(String serviceURL){
        this.execute(serviceURL);
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onPreExecute() {

        super.onPreExecute();

        AlertDialog.Builder builder=new AlertDialog.Builder(activityWeakReference.get());

        builder.setTitle(R.string.dialog_wait);

        builder.setView(R.layout.dialog_wait);

        builder.setCancelable(true);

        alertDialog = builder.create();

        alertDialog.show();

    }



    @Override

    protected String doInBackground(String... strings) {

        try{

            //Script URL
            String script = strings[0];
            URL url = new URL(script);

            //Init HTTPS Connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setDoOutput(true);
            connection.setDoInput(true);



            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );



            String line = "";

            StringBuilder data = new StringBuilder();



            while (line != null) {

                line = bufferedReader.readLine();

                data.append(line).append("\n");

            }

            inputStream.close();

            //return data.toString().substring(data.indexOf("["), data.lastIndexOf("]") + 1);
            return data.toString();




        }catch(Exception e){

            e.printStackTrace();

            return "Error";

        }

    }



    @Override

    protected void onPostExecute(String result) {

        super.onPostExecute(result);


        //Parsing

        try{
            JSONObject jsonObject = new JSONObject(result);

            alertDialog.dismiss();

            delegate.obtainServiceResult(jsonObject);

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }





}