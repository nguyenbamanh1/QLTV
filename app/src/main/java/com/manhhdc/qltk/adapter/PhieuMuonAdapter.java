package com.manhhdc.qltk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.manhhdc.qltk.Moduls.Lop;
import com.manhhdc.qltk.Moduls.PhieuMuon;
import com.manhhdc.qltk.R;

import java.util.List;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    Context context;
    int resource;
    List<PhieuMuon> docgias;

    public PhieuMuonAdapter(@NonNull Context context, int resource, @NonNull List<PhieuMuon> objects) {
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
            PhieuMuon lop = docgias.get(position);

            TextView mamt = convertView.findViewById(R.id.txtmamt);
            TextView hoten = convertView.findViewById(R.id.txthoten);
            TextView hinhthuc = convertView.findViewById(R.id.txthinhthuc);
            TextView ngaymuon = convertView.findViewById(R.id.txtngaymuon);
            TextView ngaytra = convertView.findViewById(R.id.txtngaytra);
            mamt.setText(lop.MAMT);
            hoten.setText(lop.HOTEN);
            hinhthuc.setText(lop.HINHTHUC);
            ngaymuon.setText(lop.NGAYMUON);
            ngaytra.setText(lop.NGAYTRA);
        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }
}
