package com.example.freetoplay;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.freetoplay.models.ItemListadoJuegos;
import com.example.freetoplay.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.freetoplay.databinding.FragmentItemBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<ItemListadoJuegos> mValues;
    private FragmentManager frManager;
    Spinner spinner;
    public MyItemRecyclerViewAdapter(List<ItemListadoJuegos> items,FragmentManager fragmentManager, Spinner sp) {
        mValues = items;
        this.spinner=sp;
        this.frManager=fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // holder.mItem = mValues.get(position);

        holder.textItem.setText(mValues.get(position).title);
        String s=mValues.get(position).title;
        String b =mValues.get(position).thumbnail;

        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(mValues.get(position).thumbnail).getContent());
            holder.imageItem.setImageBitmap(bitmap);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.imageItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Al hacer clik en un elemento de la lista es cuando se activa esta funcion

                    Bundle bundle = new Bundle();
                    bundle.putString("idJuego", String.valueOf(mValues.get(position).id)); // le pasamos al webfragment la url que hemos guardado en el tooltipText del elemento previamente

                    JuegoFragment juegoFragment= new JuegoFragment(spinner);
                    juegoFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = frManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, juegoFragment);
                    fragmentTransaction.commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textItem;
        public final ImageView imageItem;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            textItem = binding.textItem;
            imageItem = binding.imageItem;
        }

    }
}