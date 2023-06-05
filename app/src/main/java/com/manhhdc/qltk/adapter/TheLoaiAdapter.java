package com.manhhdc.qltk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.TheDangKy;
import com.manhhdc.qltk.R;

import java.util.List;

public class TheLoaiAdapter extends ArrayAdapter<Entity> {
    Context context;
    int resource;
    List<Entity> docgias;

    public TheLoaiAdapter(@NonNull Context context, int resource, @NonNull List<Entity> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.docgias = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        try{
            Entity doc = docgias.get(position);
            TextView madg = convertView.findViewById(R.id.txtmatheloai);
            TextView tendg = convertView.findViewById(R.id.txttentheloai);
            madg.setText(doc.ID);
            tendg.setText(doc.NAME);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
