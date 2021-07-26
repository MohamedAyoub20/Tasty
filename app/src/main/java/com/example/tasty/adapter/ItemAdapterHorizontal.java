package com.example.tasty.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasty.R;
import com.example.tasty.model.Item;
import com.example.tasty.view.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ItemAdapterHorizontal extends RecyclerView.Adapter<ItemAdapterHorizontal.ViewHolder> {

   List<Item> itemsList;



   public ItemAdapterHorizontal ( List<Item> itemsList){
       this.itemsList=itemsList;
   }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout_horizontal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
             Picasso.get().load(itemsList.get(position).getThumbnailUrl()).into(holder.imageView);
             holder.textView.setText(itemsList.get(position).getName());
             moveToDetailsFragment(holder,position);
   }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private void moveToDetailsFragment( ViewHolder holder, final int position){

       holder.imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               DetailsFragment detailsFragment = new DetailsFragment();
               Bundle args=new Bundle();
               args.putInt("id",itemsList.get(position).getId());
               detailsFragment.setArguments(args);

              OnDetailAction detailAction=(OnDetailAction) v.getContext();
              detailAction.moveFragment(detailsFragment);
           }
       });

    }

    public interface OnDetailAction {
       void moveFragment(DetailsFragment detailsFragment);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         ImageView imageView;
         TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image_view);
            textView=itemView.findViewById(R.id.name_tv);
        }
    }
}
