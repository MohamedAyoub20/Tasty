package com.example.tasty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tasty.R;
import com.example.tasty.model.Item;
import com.example.tasty.model.homeAndDetails.Result;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapterVertical extends RecyclerView.Adapter<ResultAdapterVertical.ViewHolder>{

    List<Result> resultList;
    ItemAdapterHorizontal itemAdapterHorizontal;


    public ResultAdapterVertical(List<Result> resultList){
        this.resultList=resultList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.result_layout_vertical, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           if(position>1&&position<8) {

               holder.textView.setText(resultList.get(position).getName());

                   itemAdapterHorizontal = new ItemAdapterHorizontal(resultList.get(position).getItems());

                   holder.recyclerView.setAdapter(itemAdapterHorizontal);

                   holder.recyclerView.setLayoutManager(
                           new LinearLayoutManager(
                                   holder.recyclerView.getContext(),LinearLayoutManager.HORIZONTAL,false));

           }
          /* else if(position>=8&&position%2==0){

               holder.itemList.add(resultList.get(position).getItem());
               holder.itemList.add(resultList.get(position+1).getItem());

                   itemAdapterHorizontal = new ItemAdapterHorizontal(holder.itemList);

                   holder.recyclerView.setAdapter(itemAdapterHorizontal);

                   holder.recyclerView.setLayoutManager(
                           new GridLayoutManager(holder.recyclerView.getContext(),2));

                   if (position==8){
                       holder.textView.setText("Resent");
                   }
           }*/

        }


    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        RecyclerView recyclerView;
        List <Item> itemList;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemList=new ArrayList();
            textView = itemView.findViewById(R.id.type_tv);
            recyclerView=itemView.findViewById(R.id.item_rec);

        }
    }
}
