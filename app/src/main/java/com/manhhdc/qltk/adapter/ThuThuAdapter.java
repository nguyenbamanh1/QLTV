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
import com.manhhdc.qltk.Moduls.ThuThu;
import com.manhhdc.qltk.R;

import java.util.List;

public class ThuThuAdapter extends ArrayAdapter<ThuThu> {
    Context context;
    int resource;
    List<ThuThu> docgias;

    public ThuThuAdapter(@NonNull Context context, int resource, @NonNull List<ThuThu> objects) {
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
            ThuThu doc = docgias.get(position);
            TextView madg = convertView.findViewById(R.id.txtmatt);
            TextView tendg = convertView.findViewById(R.id.txttentt);
            TextView gt = convertView.findViewById(R.id.txtgioitinhtt);

            madg.setText(doc.MATT);
            tendg.setText(doc.TENTT);
            gt.setText(doc.GT == 0 ? "Nam" : "Nữ");

        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}