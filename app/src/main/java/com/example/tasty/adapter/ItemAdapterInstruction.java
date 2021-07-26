package com.example.tasty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasty.R;
import com.example.tasty.model.Instruction;

import java.util.List;

public class ItemAdapterInstruction extends RecyclerView.Adapter<ItemAdapterInstruction.ViewHolder> {

    List<Instruction>instructionList;

    public ItemAdapterInstruction(List<Instruction>instructionList){
        this.instructionList=instructionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout_instruction, parent, false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.countTextView.setText(""+(position+1));

        holder.instructionTextView.setText(instructionList.get(position).getDisplayText());

    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView countTextView,instructionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            countTextView=itemView.findViewById(R.id.count_tv);
            instructionTextView=itemView.findViewById(R.id.instruction_tv);
        }
    }
}
