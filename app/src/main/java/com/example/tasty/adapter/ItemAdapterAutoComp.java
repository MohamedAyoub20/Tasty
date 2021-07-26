package com.example.tasty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tasty.R;
import com.example.tasty.model.search.SearchResult;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ItemAdapterAutoComp extends RecyclerView.Adapter<ItemAdapterAutoComp.ViewHolder> {

  List<SearchResult> resultList;
  OnSearchData onSearchData;


  public ItemAdapterAutoComp(List<SearchResult> resultList,OnSearchData onSearchData){
      this.resultList=resultList;
      this.onSearchData=onSearchData;
  }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout_autocomp, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

             holder.textView.setText(resultList.get(position).getDisplayAutoComp());

             showSearchData(holder,position);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    private void showSearchData(final ViewHolder holder, final int position){

      holder.textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              onSearchData.getSearchDataByQuery(resultList.get(position).getDisplayAutoComp());
          }
      });
    }

    public interface OnSearchData{
      void getSearchDataByQuery(String searchWord);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.autoComp_TV);
        }
    }
}
