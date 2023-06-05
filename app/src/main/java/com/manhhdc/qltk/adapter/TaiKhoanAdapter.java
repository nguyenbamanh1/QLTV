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
import com.manhhdc.qltk.Moduls.TaiKhoan;
import com.manhhdc.qltk.R;

import java.util.List;

public class TaiKhoanAdapter extends ArrayAdapter<TaiKhoan> {
    Context context;
    int resource;
    List<TaiKhoan> docgias;

    public TaiKhoanAdapter(@NonNull Context context, int resource, @NonNull List<TaiKhoan> objects) {
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
            TaiKhoan lop = docgias.get(position);

            TextView malop = convertView.findViewById(R.id.txttaikhoan);
            TextView tenlop = convertView.findViewById(R.id.txtmatkhau);
            malop.setText(lop.MATT);
            tenlop.setText(lop.TAIKHOAN);
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }
}
