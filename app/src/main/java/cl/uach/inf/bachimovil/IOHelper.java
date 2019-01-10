package cl.uach.inf.bachimovil;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IOHelper {
    public static String stringFromStream(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stringFromFile(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        String str = stringFromStream(fis);
        fis.close();
        return str;
    }

    public static void writeToFile(File f, String str) throws IOException {
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(str.getBytes());
        fos.close();

    }

    public static void writeToFile(Context context, String fileName, String str) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(str.getBytes(), 0, str.length());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String stringFromAsset(Context context, String assetFileName) {
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(assetFileName);
            String result = IOHelper.stringFromStream(is);
            is.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<String> StringtoArrayList(String taqs){
        ArrayList<String> TAQS= new ArrayList<>();
        String frase = taqs;
        int pos = frase.indexOf(",");
        while(pos!=-1){
            taqs = frase.substring(0,pos);
            frase = frase.substring(pos+1,frase.length());
            TAQS.add(taqs);
            pos=frase.indexOf(",");
        }
        TAQS.add(frase);
        return TAQS;
    }

}
