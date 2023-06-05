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
import com.manhhdc.qltk.R;

import java.util.List;

public class DocGiaAdapter extends ArrayAdapter<DocGia> {
    Context context;
    int resource;
    List<DocGia> docgias;

    public DocGiaAdapter(@NonNull Context context, int resource, @NonNull List<DocGia> objects) {
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
            DocGia DocGia = docgias.get(position);

            TextView madg = convertView.findViewById(R.id.txtmadg);
            TextView tendg = convertView.findViewById(R.id.txttendg);
            TextView gt = convertView.findViewById(R.id.txtgioitinh);
            TextView status = convertView.findViewById(R.id.txttinhtrang);
            madg.setText(DocGia.MADG);
            tendg.setText(DocGia.HOTEN);
            gt.setText(DocGia.GT == 0 ? "Nam" : "Nữ");
            status.setText(DocGia.TINHTRANG);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
