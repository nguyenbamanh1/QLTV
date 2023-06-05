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
import com.manhhdc.qltk.Moduls.TheDangKy;
import com.manhhdc.qltk.R;

import java.util.List;

public class TheDangKyAdapter extends ArrayAdapter<TheDangKy> {
    Context context;
    int resource;
    List<TheDangKy> docgias;

    public TheDangKyAdapter(@NonNull Context context, int resource, @NonNull List<TheDangKy> objects) {
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
            TheDangKy doc = docgias.get(position);

            TextView madg = convertView.findViewById(R.id.txtmadk);
            TextView tendg = convertView.findViewById(R.id.txthoten);
            TextView gt = convertView.findViewById(R.id.txtgioitinh);
            TextView status = convertView.findViewById(R.id.txtloaidk);

            madg.setText(doc.MADK);
            tendg.setText(doc.HOTEN);
            gt.setText(doc.GT == 0 ? "Nam" : "Nữ");
            status.setText(doc.LOAIDK);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}

