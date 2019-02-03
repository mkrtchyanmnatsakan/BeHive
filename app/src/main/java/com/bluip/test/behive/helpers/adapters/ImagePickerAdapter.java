package com.bluip.test.behive.helpers.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bluip.test.behive.R;
import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Image;
import java.util.ArrayList;
import java.util.List;

public class ImagePickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity homeActivity;
    private LayoutInflater inflater;
    private   ArrayList<Image> images;


    public ImagePickerAdapter(Activity activity, ArrayList<Image> images) {

        homeActivity = activity;
        this.images = images;
        this.inflater = LayoutInflater.from(activity);

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ImagePickerAdapter.ViewHolder(inflater.inflate(R.layout.image_picker_item,viewGroup,false));

    }


    public void setNewImageList(List<Image> newImageList){


        if(newImageList != null && !newImageList.isEmpty()){

            images.addAll(newImageList);
        }

        notifyDataSetChanged();


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder myViewHolder = (ViewHolder) viewHolder;


        Glide.with(homeActivity)
                .load(images.get(i).getPath())
                .into(myViewHolder.imageItem);


    }



    @Override
    public int getItemCount() {
        if (images == null || images.isEmpty()) {
            return 0;
        } else {

            return images.size();
        }
    }



    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageItem;
        RelativeLayout clearRelative;

        private ViewHolder(View itemView) {
            super(itemView);

            imageItem     = itemView.findViewById(R.id.image_item);
            clearRelative = itemView.findViewById(R.id.clear_relative);

            clearRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    images.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());


                }
            });


        }
    }

}
