package com.example.freetoplay;
import com.example.freetoplay.models.ItemListadoJuegos;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GameListTask extends AsyncTask<String, Void, List<ItemListadoJuegos>> {

    View view;
    List<ItemListadoJuegos> lista;
    public static List<String> listaGenerosDisponibles;
    public static List<String> listaNombres;
    public static List<String> listaAniosDisponibles;


    public GameListTask(){

    }

    @Override
    protected List<ItemListadoJuegos> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        StringBuffer stringBuffer;
        //Recogemos los parametros
       // this.pais=params[0];
       // this.nombre=params[1];

        try {
            //Hacemos la peticion con los parametros que nbos pasan
            URL url = new URL("https://www.freetogame.com/api/games");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            stringBuffer = new StringBuffer();
            //Si no nos devuelve nada salimos
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n"); // salida por consola con salto de linea  mientras haya más registros
            }

            if (stringBuffer.length() == 0) {
                return null;
            }
            forecastJsonStr = stringBuffer.toString();

            lista= new ArrayList<ItemListadoJuegos>(){};
            listaGenerosDisponibles = new ArrayList<>();
            listaAniosDisponibles =new ArrayList<>();
            listaNombres= new ArrayList<>();

           try {
                //Transformamos la repuesta a JSON array
                JSONArray jsonArray = new JSONArray(forecastJsonStr);
                for (int i = 0; i <jsonArray.length() ; i++) {
                    //Convertimos la respuesta primero a objetos JSON y despues formamos el objeto Universidad para agragarlo a la lista
                    try {
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));

                        ItemListadoJuegos item= new ItemListadoJuegos();
                        item.id=jsonObject.getInt("id");
                        item.title=jsonObject.getString("title");
                        item.thumbnail=jsonObject.getString("thumbnail");
                        item.short_description=jsonObject.getString("short_description");
                        item.game_url=jsonObject.getString("game_url");
                        item.genre=jsonObject.getString("genre").toUpperCase().trim();

                        if(listaGenerosDisponibles.indexOf(item.genre)==-1){
                             listaGenerosDisponibles.add(item.genre);
                        }
                        item.platform=jsonObject.getString("platform");
                        item.publisher=jsonObject.getString("publisher");
                        item.developer=jsonObject.getString("developer");
                        item.release_date=jsonObject.getString("release_date");

                        String anio=item.release_date.substring(0,4);

                        if(listaAniosDisponibles.indexOf(anio)==-1){
                            listaAniosDisponibles.add(anio);
                        }
                        item.freetogame_profile_url=jsonObject.getString("freetogame_profile_url");
                        lista.add(item);
                        listaNombres.add(item.title.toUpperCase());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);

            return null;
        } finally{
            //Cerramos conexiones
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
            //Imprimo las 3 listas
            for (String nombre: listaNombres) {
                Log.i("JUEGO",nombre);
            }
            for (String a:listaGenerosDisponibles) {
                Log.i("GENERO", a);
            }
           // Collections.sort(listaAniosDisponibles);
            for (String a:listaAniosDisponibles) {
                Log.i("ANIO", a);
            }
            listaAniosDisponibles=ordenar(listaAniosDisponibles);
            for (String a:listaAniosDisponibles) {
                Log.i("ORDENADO", a);
            }
        }
    return lista;
    }


   //Para ordenar la lista de años
    public List<String> ordenar(List<String> lista){
       String aux= "";
        for (int x = 0; x <lista.size() ; x++) {
            for (int i = 0; i <lista.size()-1 ; i++) {
                if (Integer.valueOf(lista.get(i)) < Integer.valueOf(lista.get(i+1))){
                    aux=lista.get(i);
                    lista.set(i,lista.get(i+1));
                    lista.set(i+1,aux);
                }
            }
        }


        return lista;
    }

}


