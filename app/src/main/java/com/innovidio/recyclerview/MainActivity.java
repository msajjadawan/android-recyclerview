package com.innovidio.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.innovidio.recyclerview.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int size = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        List<Object> listItems1 = prepareData();
        List<Object> listItems2 = prepareData();

        insertAdsInMenuItems(listItems2);
        initRecyclerViewHorizontal(listItems1);
        initRecyclerViewVertical(listItems2);


    }

    private void insertAdsInMenuItems(List<Object> listItems) {
        listItems.add(2, new NativeAd());
        listItems.add(5, new NativeAd());
        listItems.add(7, new NativeAd());
        listItems.add(9, new NativeAd());
        listItems.add(11, new NativeAd());
        listItems.add(13, new NativeAd());
        listItems.add(15, new NativeAd());

    }
    private void initRecyclerViewHorizontal(List<Object> listItems) {
        RecyclerView recyclerViewHorizontal = findViewById(R.id.recyclerview_horizontal);

        RecyclerViewHorizontalAdapter viewAdapter = new RecyclerViewHorizontalAdapter(this,listItems);

        recyclerViewHorizontal.setAdapter(viewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHorizontal.setLayoutManager(linearLayoutManager);

    }

    private void initRecyclerViewVertical(List<Object> listItems) {

        final RecyclerView recyclerViewVertical = findViewById(R.id.recyclerview_vertical);

        RecyclerViewVerticalAdapter viewAdapter = new RecyclerViewVerticalAdapter(this,listItems, new CallbackHandeler(){

            @Override
            public void handleClick(int size) {
                recyclerViewVertical.smoothScrollToPosition(size);
            }
        });

        recyclerViewVertical.setAdapter(viewAdapter);
        recyclerViewVertical.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Object> prepareData(){
        Log.d(TAG, "prepareData: called");
        List<Object> list = new ArrayList<>(size);
        for(int i=0; i<size;i++){
            list.add(
                    new ListItem("Picture id: "+i,"https://picsum.photos/id/"+i+"/200/200" )
            );
        }
        Log.d(TAG, "prepareData: list size:"+list.size());
        return list;
    }

    public interface CallbackHandeler {
        void handleClick(int size);
    }
}
