package com.innovidio.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.innovidio.recyclerview.ads.NativeAd;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewVerticalAdapter extends RecyclerView.Adapter<RecyclerViewVerticalAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;


    private static int counter = 1;
    List<Object> itemList = null;
    MainActivity.CallbackHandeler callbackHandeler;
    Context context;


    public RecyclerViewVerticalAdapter(Context context, List<Object> itemList, MainActivity.CallbackHandeler callbackHandeler) {
        this.itemList = itemList;
        this.context = context;
        this.callbackHandeler = callbackHandeler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                View unifiedNativeLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.ad_unified,
                        viewGroup, false);
                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case MENU_ITEM_VIEW_TYPE:
                // Fall through.
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.menu_item_container, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
        }




        Log.d(TAG, "onCreateViewHolder: called:" + (counter));
        Context parentContext = parent.getContext();
        View view = LayoutInflater.from(parentContext).inflate(R.layout.layout_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called for position:" + position);
        final ListItem listItem = (ListItem) itemList.get(position);
        Glide.with(this.context).asBitmap().load(listItem.getImageURl()).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
        holder.textView.setText(listItem.getTitle());

        Log.d(TAG, "onBindViewHolder: viewHolder: "+ holder.index +" for position :"+position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called and returrned :"+itemList.size());
        return itemList.size();
    }
    public int getItemViewType(int position) {

        Object recyclerViewItem = itemList.get(position);
        if (recyclerViewItem instanceof NativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return MENU_ITEM_VIEW_TYPE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RecyclerViewVerticalAdapter recyclerViewVerticalAdapter;
        CircleImageView imageView;
        TextView textView;
        ConstraintLayout layout;
        int index = 0;


        public ViewHolder(@NonNull View itemView, RecyclerViewVerticalAdapter recyclerViewVerticalAdapter) {
            super(itemView);
            this.recyclerViewVerticalAdapter = recyclerViewVerticalAdapter;
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            layout = itemView.findViewById(R.id.layout);
            index = counter++;
            Log.d(TAG, "Vertical ViewHolder constructor called : "+index);
        }
        @Override
        public void onClick(View v) {
// Get the position of the item that was clicked.
            int mPosition =  getLayoutPosition();

// Use that to access the affected item in mWordList.
            ListItem listItem = (ListItem) itemList.get(mPosition);
            Toast.makeText(context,listItem.getTitle() +" clicked",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onClick: for " +listItem.getTitle());

            listItem.setTitle(listItem.getTitle() +" Clicked !");

            itemList.add(new ListItem("Picture id: "+itemList.size(),"https://picsum.photos/id/"+itemList.size()+"/200/200" ));
            callbackHandeler.handleClick(itemList.size());
// Change the word in the mWordList. mWordList.set(mPosition, "Clicked! " + element);
// Notify the adapter, that the data has changed so it can // update the RecyclerView to display the data.
            this.recyclerViewVerticalAdapter.notifyDataSetChanged();
        }

    }
    public class UnifiedNativeAdViewHolder extends RecyclerView.ViewHolder {

        private UnifiedNativeAdView adView;

        public UnifiedNativeAdView getAdView() {
            return adView;
        }

        UnifiedNativeAdViewHolder(View view) {
            super(view);
            adView = (UnifiedNativeAdView) view.findViewById(R.id.ad_view);

            // The MediaView will display a video asset if one is present in the ad, and the
            // first image asset otherwise.
            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

            // Register the view used for each individual asset.
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
            adView.setBodyView(adView.findViewById(R.id.ad_body));
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
            adView.setIconView(adView.findViewById(R.id.ad_icon));
            adView.setPriceView(adView.findViewById(R.id.ad_price));
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
            adView.setStoreView(adView.findViewById(R.id.ad_store));
            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        }
    }

}
