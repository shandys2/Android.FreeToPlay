package com.example.freetoplay;
import com.example.freetoplay.models.ItemListadoJuegos;
import com.example.freetoplay.models.Juego;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GameDetailsTask extends AsyncTask<String, Void, Juego> {

    View view;
    Juego juego;
    int id;
    public GameDetailsTask(){

    }

    @Override
    protected Juego doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        StringBuffer stringBuffer;
        //Recogemos los parametros
         this.id= Integer.parseInt(params[0]);
        // this.nombre=params[1];

        try {
            //Hacemos la peticion con los parametros que nbos pasan
            URL url = new URL("https://www.freetogame.com/api/game?id="+id);
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
            try {
                //Transformamos la repuesta a JSON Object
                JSONObject jsonObject = new JSONObject(forecastJsonStr);

                juego= new Juego();
                juego.root.id=jsonObject.getInt("id");
                juego.root.title=jsonObject.getString("title");
                juego.root.thumbnail=jsonObject.getString("thumbnail");
                juego.root.short_description=jsonObject.getString("short_description");
                juego.root.description=jsonObject.getString("description");
                juego.root.game_url=jsonObject.getString("game_url");
                juego.root.genre=jsonObject.getString("genre");
                juego.root.platform=jsonObject.getString("platform");
                juego.root.publisher=jsonObject.getString("publisher");
                juego.root.developer=jsonObject.getString("developer");
                juego.root.release_date=jsonObject.getString("release_date");
                juego.root.freetogame_profile_url=jsonObject.getString("freetogame_profile_url");
                JSONObject minimumSystemRequirements=jsonObject.getJSONObject("minimum_system_requirements");

                juego.root.minimum_system_requirements.graphics= minimumSystemRequirements.getString("graphics");
                juego.root.minimum_system_requirements.processor= minimumSystemRequirements.getString("processor");
                juego.root.minimum_system_requirements.os= minimumSystemRequirements.getString("os");
                juego.root.minimum_system_requirements.memory= minimumSystemRequirements.getString("memory");
                juego.root.minimum_system_requirements.storage= minimumSystemRequirements.getString("storage");
                JSONArray screenshots=jsonObject.getJSONArray("screenshots");


                for (int i = 0; i < screenshots.length(); i++) {
                    Juego.Screenshot screenshot =new Juego.Screenshot();
                    screenshot.id=screenshots.getJSONObject(i).getInt("id");
                    screenshot.image=screenshots.getJSONObject(i).getString("image");
                    juego.root.screenshots.add(screenshot);
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
        }
        return juego;
    }


}


