package com.example.tasty.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasty.R;
import com.example.tasty.adapter.ItemAdapterTag;
import com.example.tasty.apiConnection.ApiClient;
import com.example.tasty.apiConnection.ApiInterface;
import com.example.tasty.model.search.TagRoot;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialog extends BottomSheetDialogFragment implements ItemAdapterTag.OnActionTag {

    Call<TagRoot> tagRootCall;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    Button button;
    OnBottomDialogListener onBottomDialogListener;

    public BottomSheetDialog(OnBottomDialogListener onBottomDialogListener){
        this.onBottomDialogListener=onBottomDialogListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.bottom_sheet_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         init(view);

         showTagList();

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 dismiss();

             }
         });

    }

    private void init(View view){

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        recyclerView =view.findViewById(R.id.recycler_bottomMenu);

        button = view.findViewById(R.id.done_bt);

    }

    private void showTagList() {

        tagRootCall = apiInterface.getAllTags(ApiClient.key);

        tagRootCall.enqueue(new Callback<TagRoot>() {
            @Override
            public void onResponse(Call<TagRoot> call, Response<TagRoot> response) {

                List <String> stringTypeList = getTypeList(response);

                List <String> list = getDisplayList(response,stringTypeList);

                ItemAdapterTag itemAdapterTag = new ItemAdapterTag(list,stringTypeList,BottomSheetDialog.this);
                recyclerView.setAdapter(itemAdapterTag);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<TagRoot> call, Throwable t) {

            }
        });

    }

    private List<String> getTypeList(Response<TagRoot> response){

        Set <String> stringSet = new TreeSet<>();

        for(int i=0;i< response.body().getResults().size();i++){
            stringSet.add(response.body().getResults().get(i).getType());
        }

        return new ArrayList<>(stringSet);
    }

    private List <String> getDisplayList(Response<TagRoot> response,List <String> stringTypeList){

        List <String> list = new ArrayList();

        for(int i=0;i<stringTypeList.size();i++){

            list.add(stringTypeList.get(i));

            for(int j=0;j<response.body().getResults().size();j++){

                if(stringTypeList.get(i).equals(response.body().getResults().get(j).getType())){

                    list.add(response.body().getResults().get(j).getDisplayName());

                }
            }
        }

        return  list;
    }


    @Override
    public void getSearchDataByTag(String tagName, String displayName) {

        onBottomDialogListener.getSearch(tagName,displayName);

    }

    public interface OnBottomDialogListener{
        void getSearch(String tagName, String displayName);
    }

}
