package com.manhhdc.qltk;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.manhhdc.qltk.Moduls.DocGia;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.DocGiaService;

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
            Call<DocGia> docgia = service.get(402);
            Response<DocGia> response = docgia.execute();
            DocGia user = response.body();
            if(user != null){
                Log.i("ok",user.TINHTRANG);
            }else{
                Log.i("err","Null");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}