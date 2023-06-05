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
import com.manhhdc.qltk.Moduls.TaiLieu;
import com.manhhdc.qltk.R;

import java.util.List;

public class TaiLieuAdapter extends ArrayAdapter<TaiLieu> {
    Context context;
    int resource;
    List<TaiLieu> docgias;

    public TaiLieuAdapter(@NonNull Context context, int resource, @NonNull List<TaiLieu> objects) {
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
            TaiLieu doc = docgias.get(position);

            TextView madg = convertView.findViewById(R.id.txtmatl);
            TextView tendg = convertView.findViewById(R.id.txttentl);
            TextView gt = convertView.findViewById(R.id.txttheloai);
            TextView status = convertView.findViewById(R.id.txtsoluong);
            madg.setText(doc.MATL);
            tendg.setText(doc.TENTL);
            gt.setText(doc.THELOAI);
            status.setText(doc.SOLUONG + "");
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
