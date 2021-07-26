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
import com.example.tasty.model.search.SearchResult;
import com.example.tasty.view.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultAdapterSearch extends RecyclerView.Adapter<ResultAdapterSearch.ViewHolder> {

    List <SearchResult> searchResults;
    OnDetailAction onDetailAction;


    public ResultAdapterSearch(List <SearchResult> searchResults ,OnDetailAction onDetailAction ){
        this.searchResults=searchResults;
        this.onDetailAction = onDetailAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.result_layout_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(searchResults.get(position).getThumbnailUrl()).into(holder.imageView);

        holder.textView.setText(searchResults.get(position).getName());

        moveToDetailsFragment(holder,position);

    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    private void moveToDetailsFragment(ViewHolder holder, final int position){

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailsFragment detailsFragment = new DetailsFragment();
                Bundle args=new Bundle();
                args.putInt("id",searchResults.get(position).getId());
                detailsFragment.setArguments(args);
                onDetailAction.moveFragment(detailsFragment);

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

            imageView=itemView.findViewById(R.id.url_imageView);
            textView=itemView.findViewById(R.id.name_textView);
        }
    }
}
