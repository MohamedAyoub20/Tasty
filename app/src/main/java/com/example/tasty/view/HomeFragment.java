package com.example.tasty.view;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.tasty.R;
import com.example.tasty.adapter.ResultAdapterVertical;
import com.example.tasty.apiConnection.ApiClient;
import com.example.tasty.apiConnection.ApiInterface;
import com.example.tasty.model.homeAndDetails.Root;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ApiInterface apiInterface;
    Call<Root> call;
    ResultAdapterVertical resultAdapterVertical;
    RecyclerView recyclerView;
    ImageView imageView;
    ProgressBar progressBar;
    TextView textView , textViewSearch;

    SharedPreferences sharedPreferences;
    AlertDialog.Builder builder;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        //showData();

        moveToSearchFragment();

    }

    private void init(View view){
        sharedPreferences=getActivity().getSharedPreferences("saveData",getActivity().MODE_PRIVATE);
        sharedPreferences.edit().putInt("Fragment",0).apply();
        builder = new AlertDialog.Builder(getContext());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        call=apiInterface.getAllData(ApiClient.key);
        recyclerView=view.findViewById(R.id.home_rec);
        imageView=view.findViewById(R.id.image_url);
        progressBar=view.findViewById(R.id.circular_progress);
        textView=view.findViewById(R.id.text_featured);
        textViewSearch=view.findViewById(R.id.homeFragment_SV);
    }

    private void showData(){
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {

              if(response.isSuccessful()){

                  resultAdapterVertical = new ResultAdapterVertical(response.body().getResults());
                  recyclerView.setAdapter(resultAdapterVertical);
                  recyclerView.setLayoutManager(
                          new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

                  Picasso.get().load(response.body().getResults().get(0).getItem().getThumbnailUrl()).into(imageView);

                  textView.setText(response.body().getResults().get(0).getItem().getName());

                  progressBar.setVisibility(View.INVISIBLE);

              }
              else {

              }

               /*for (int i=0;i<response.body().getResults().size();i++){
                    Log.d("mohamed", "onResponse: "+response.body().getResults().get(i).toString()+"\n");
                }*/
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.d("Ayoub", "onFailure: "+t);
            }
        });
    }

    private void moveToSearchFragment(){
        textViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("")
                            .replace(R.id.placeholder,new SearchFragment()).commit();

            }
        });
    }






}
