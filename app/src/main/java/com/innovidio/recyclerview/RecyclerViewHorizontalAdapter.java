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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewHorizontalAdapter extends RecyclerView.Adapter<RecyclerViewHorizontalAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private static int counter = 1;
    List<ListItem> itemList = null;
    Context context;

    public RecyclerViewHorizontalAdapter(Context context, List<Object> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called:" + (counter));
        Context parentContext = parent.getContext();
        View view = LayoutInflater.from(parentContext).inflate(R.layout.layout_list_item_horizontal,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called for position:" + position);
        final ListItem listItem = itemList.get(position);
        Glide.with(this.context).asBitmap().load(listItem.getImageURl()).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
        holder.textView.setText(listItem.getTitle());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: for " +listItem.getTitle());
                Toast.makeText(context,listItem.getTitle() +" clicked",Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "onBindViewHolder: viewHolder: "+ holder.index +" for position :"+position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called and returrned :"+itemList.size());
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView textView;
        ConstraintLayout layout;
        int index = 0;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            textView = itemView.findViewById(R.id.textView2);
            layout = itemView.findViewById(R.id.layout2);
            index = counter++;
            Log.d(TAG, "Horizontal ViewHolder constructor called : "+index);
        }
    }

}
