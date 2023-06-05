package com.manhhdc.qltk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.manhhdc.qltk.Moduls.XuLy;
import com.manhhdc.qltk.R;

import java.util.List;

public class XuLyAdapter extends ArrayAdapter<XuLy> {
    Context context;
    int resource;
    List<XuLy> docgias;

    public XuLyAdapter(@NonNull Context context, int resource, @NonNull List<XuLy> objects) {
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
            XuLy doc = docgias.get(position);
            TextView hoten = convertView.findViewById(R.id.txthoten);
            TextView nguyennhan = convertView.findViewById(R.id.txtnguyennhan);
            TextView tienphat = convertView.findViewById(R.id.txttienphat);
            TextView hinhthuc = convertView.findViewById(R.id.txthinhthuc);

            hoten.setText(doc.HOTEN);
            hinhthuc.setText(doc.HINHTHUCXL);
            tienphat.setText(doc.PHAT + "");
            nguyennhan.setText(doc.NGUYENNHAN);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}