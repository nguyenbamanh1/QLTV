package com.manhhdc.qltk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.manhhdc.qltk.Moduls.TacGia;
import com.manhhdc.qltk.Moduls.TacGia;
import com.manhhdc.qltk.R;

import java.util.List;

public class TacGiaAdapter extends ArrayAdapter<TacGia> {
    Context context;
    int resource;
    List<TacGia> docgias;

    public TacGiaAdapter(@NonNull Context context, int resource, @NonNull List<TacGia> objects) {
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
            TacGia lop = docgias.get(position);

            TextView mamt = convertView.findViewById(R.id.txtmatg);
            TextView hoten = convertView.findViewById(R.id.txttentg);
            mamt.setText(lop.ID);
            hoten.setText(lop.NAME);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
