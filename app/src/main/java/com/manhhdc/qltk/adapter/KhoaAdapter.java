package com.manhhdc.qltk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.manhhdc.qltk.Moduls.DocGia;
import com.manhhdc.qltk.Moduls.Khoa;
import com.manhhdc.qltk.R;

import java.util.List;

public class KhoaAdapter extends ArrayAdapter<Khoa> {
    Context context;
    int resource;
    List<Khoa> docgias;

    public KhoaAdapter(@NonNull Context context, int resource, @NonNull List<Khoa> objects) {
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
            Khoa DocGia = docgias.get(position);

            TextView mak = convertView.findViewById(R.id.txtmakhoa);
            TextView tenk = convertView.findViewById(R.id.txttenkhoa);
            mak.setText(DocGia.ID);
            tenk.setText(DocGia.NAME);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
