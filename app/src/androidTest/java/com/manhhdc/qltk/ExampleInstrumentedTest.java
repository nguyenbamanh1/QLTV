package com.manhhdc.qltk;

import android.content.Context;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.manhhdc.qltk.Moduls.DocGia;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.DocGiaService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        DocGiaService service =  ApiService.createService(DocGiaService.class);
        try{
            Call<ArrayList<DocGia>> docgia = service.getAll();
            Response<ArrayList<DocGia>> response = docgia.execute();
            ArrayList<DocGia> docGias = response.body();

            Log.i("logooo", docGias.size() + "");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}