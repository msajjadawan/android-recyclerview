package com.innovidio.recyclerview.ads;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AdsManager {

    public static final int NUMBER_OF_ADS = 10;

    // The AdLoader used to load ads.
    private AdLoader adLoader;
    // List of native ads that have been successfully loaded.
    private Queue<UnifiedNativeAd> mNativeAds = new LinkedList<>();
    private Context context;
    private static AdsManager adsManager = new AdsManager();

    public static AdsManager getInstance(){
        return adsManager;
    }
    public void init(Context context){
        this.context = context;
        loadNativeAds();
    }

    private AdsManager(){

    }

    public UnifiedNativeAd getAd(){
        UnifiedNativeAd unifiedNativeAd = mNativeAds.poll();
        if(mNativeAds.size() <3 ){
            loadNativeAds();
        }
        return unifiedNativeAd;
    }

    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(this.context, "ca-app-pub-3940256099942544/2247696110");
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
//                        if (!adLoader.isLoading()) {
//                            insertAdsInMenuItems();
//                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
//                        if (!adLoader.isLoading()) {
//                            insertAdsInMenuItems();
//                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }
}
