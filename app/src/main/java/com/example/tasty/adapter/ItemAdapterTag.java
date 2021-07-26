package com.example.tasty.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasty.R;
import com.example.tasty.model.search.TagResult;

import java.util.List;
import java.util.Set;

public class ItemAdapterTag extends RecyclerView.Adapter<ItemAdapterTag.ViewHolder> {

   List<String> stringList , typeList;
   OnActionTag onActionTag;

   public ItemAdapterTag(List<String> stringList,List<String> typeList,OnActionTag onActionTag){
       this.stringList = stringList;
       this.typeList = typeList;
       this.onActionTag=onActionTag;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout_tag, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       if(typeList.contains(stringList.get(position))){

           holder.textView.setText(stringList.get(position));

           holder.textView.setBackgroundColor(Color.WHITE);

           holder.textView.setTextColor(Color.BLACK);
       }
       else {

           holder.textView.setText(stringList.get(position));

           holder.textView.setBackgroundColor(Color.WHITE);

           holder.textView.setTextColor(Color.BLUE);

           showItemByTag(holder,position);
       }


    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    private void showItemByTag(ViewHolder holder, final int position){

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onActionTag.getSearchDataByTag(stringList.get(position).toLowerCase(),stringList.get(position));

            }
        });
    }

    public interface OnActionTag{
      void getSearchDataByTag(String tagName , String displayName);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tag_textView);
        }
    }
}
