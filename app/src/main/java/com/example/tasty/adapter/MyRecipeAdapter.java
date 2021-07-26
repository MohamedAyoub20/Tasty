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
import com.example.tasty.dataBase.Recipe;
import com.example.tasty.view.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.ViewHolder> {

    List <Recipe> recipeList;
    OnDetailAction onDetailAction;

    public MyRecipeAdapter (List <Recipe> recipeList , OnDetailAction onDetailAction) {
        this.recipeList=recipeList;
        this.onDetailAction = onDetailAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_recipe_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(recipeList.get(position).getThumbnailUrl()).into(holder.imageView);

        holder.textView.setText(recipeList.get(position).getName());

        moveToDetailsFragment(holder,position);

    }

    private void moveToDetailsFragment(ViewHolder holder, final int position){

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailsFragment detailsFragment = new DetailsFragment();
                Bundle args=new Bundle();
                args.putInt("id",recipeList.get(position).getId());
                detailsFragment.setArguments(args);

                OnDetailAction detailAction=(OnDetailAction) v.getContext();
                detailAction.moveFragment(detailsFragment);
            }
        });

    }

    public interface OnDetailAction {
        void moveFragment(DetailsFragment detailsFragment);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.recipeUrl_imageView);
            textView=itemView.findViewById(R.id.recipeName_textView);
        }
    }
}
