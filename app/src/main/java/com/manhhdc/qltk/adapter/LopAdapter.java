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
import com.manhhdc.qltk.Moduls.Lop;
import com.manhhdc.qltk.R;

import org.w3c.dom.Text;

import java.util.List;

public class LopAdapter extends ArrayAdapter<Lop> {
    Context context;
    int resource;
    List<Lop> docgias;

    public LopAdapter(@NonNull Context context, int resource, @NonNull List<Lop> objects) {
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
            Lop lop = docgias.get(position);

            TextView malop = convertView.findViewById(R.id.txtmalop);
            TextView tenlop = convertView.findViewById(R.id.txttenlop);
            malop.setText(lop.ID);
            tenlop.setText(lop.NAME);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
