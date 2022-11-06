package com.example.freetoplay;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Spinner;

import com.example.freetoplay.models.ItemListadoJuegos;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    GameListTask peticiones= new GameListTask();

    List<ItemListadoJuegos> lista;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        spinner=findViewById(R.id.spinner);
        try {
            lista=peticiones.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ItemFragment fragment = new ItemFragment(spinner);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }


    public final String TAG ="MAIN APP";
    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG,"onPause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState");
    }
}