package com.example.freetoplay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.freetoplay.databinding.FragmentJuegoBinding;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.freetoplay.models.Juego;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JuegoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JuegoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID = "idJuego";
    private FragmentJuegoBinding fragmentJuegoBinding;
    static FragmentManager fragmentManager;
    // TODO: Rename and change types of parameters

    private String idJuego;
    private Juego juego;
    ImageView imageGame, imageScreenshot;
    TextView textTitle,textDesription,textOs,textGraphics, textProcessor,textStorage,textMemory;
    Button buttonBack;
    ItemFragment itemFragment;
    Spinner spinner;

    public JuegoFragment(Spinner sp) {
        this.spinner=sp;
        // Required empty public constructor
    }
    public JuegoFragment() {

        // Required empty public constructor
    }
    public JuegoFragment(ItemFragment fragment) {
        this.itemFragment=fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idJuego = getArguments().getString(ID);

        }
        fragmentManager=getParentFragmentManager();
        GameDetailsTask gameDetailsTask = new GameDetailsTask();
        try {
            juego=gameDetailsTask.execute(idJuego).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View view =inflater.inflate(R.layout.fragment_juego, container, false);
        fragmentJuegoBinding= FragmentJuegoBinding.inflate(getLayoutInflater(),container, false);
        View view=fragmentJuegoBinding.getRoot();
        textTitle=view.findViewById(R.id.textTitle);
        imageGame=view.findViewById(R.id.imageGame);
        textDesription=view.findViewById(R.id.textDescription);
        textMemory=view.findViewById(R.id.textMemory);
        textProcessor=view.findViewById(R.id.textProcessor);
        textStorage=view.findViewById(R.id.textStorage);
        textOs=view.findViewById(R.id.textOs);
        textGraphics=view.findViewById(R.id.textGraphics);

        buttonBack=view.findViewById(R.id.buttonBack);
        //imageScreenshot=  view.findViewById(R.id.imageScreenshot);

        textTitle.setText(juego.root.title);
        textDesription.setText(juego.root.description);
        textOs.setText(juego.root.minimum_system_requirements.os);
        textMemory.setText(juego.root.minimum_system_requirements.memory);
        textProcessor.setText(juego.root.minimum_system_requirements.processor);
        textStorage.setText(juego.root.minimum_system_requirements.storage);
        textGraphics.setText(juego.root.minimum_system_requirements.graphics);

   /*     Bundle bundle = new Bundle();
        bundle.putString("url",juego.root.game_url); // le pasamos al webfragment la url que hemos guardado en el tooltipText del elemento previamente
        WebFragment webFragment= new WebFragment();
        webFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutWeb, webFragment);
        fragmentTransaction.commit();
*/
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(juego.root.thumbnail).getContent());
            imageGame.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemFragment fragment = new ItemFragment(spinner);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();;
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
            }
        });
    /*   try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(juego.root.screenshots.).getContent());
            imageScreenshot.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return view;
    }

    public final String TAG ="JUEGO FRAG";
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