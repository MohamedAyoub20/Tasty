package com.example.tasty.view;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasty.R;
import com.example.tasty.adapter.ItemAdapterAutoComp;
import com.example.tasty.adapter.ItemAdapterTag;
import com.example.tasty.adapter.ResultAdapterSearch;
import com.example.tasty.apiConnection.ApiClient;
import com.example.tasty.apiConnection.ApiInterface;
import com.example.tasty.model.search.SearchRoot;
import com.example.tasty.model.search.TagRoot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements ItemAdapterTag.OnActionTag
        , ItemAdapterAutoComp.OnSearchData , ResultAdapterSearch.OnDetailAction , BottomSheetDialog.OnBottomDialogListener {


    ApiInterface apiInterface;
    Call<SearchRoot> callAutoComp, callSearchByQuery, callSearchByTag , callSearchByQueryAndTag;
    Call<TagRoot>tagRootCall;
    SearchView searchView;
    RecyclerView recyclerViewAutoComp , recyclerViewSearch , recyclerViewTag ;
    ImageButton imageButtonBack , imageButtonFilter;
    SharedPreferences sharedPreferences;
    TextView textView;
    BottomSheetDialog bottomSheetDialog;
    Map<String, List<String>> stringListMap;
    String querySearch ="";



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return  inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        mainMethod();

        showData();

        backToHomeFragment();

        removeSearchDataByTag();

        imageButtonFilterClick();

    }


    private void init(View view){

        apiInterface=ApiClient.getClient().create(ApiInterface.class);

        sharedPreferences=getActivity().getSharedPreferences("saveData",getActivity().MODE_PRIVATE);
        sharedPreferences.edit().putInt("Fragment",1).apply();

        bottomSheetDialog = new BottomSheetDialog(SearchFragment.this);

        stringListMap = new HashMap();

        searchView = view.findViewById(R.id.searchFragment_SV);
        recyclerViewAutoComp =view.findViewById(R.id.autoCompRecyclerView);
        recyclerViewSearch=view.findViewById(R.id.searchRecyclerView);
        recyclerViewTag=view.findViewById(R.id.tagRecyclerView);
        imageButtonBack=view.findViewById(R.id.backFragment_IM);
        textView=view.findViewById(R.id.showDisplayNameTag_tv);
        imageButtonFilter=view.findViewById(R.id.filter_IB);
    }

    private void showData(){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(!query.equals("")){

                    showSearchDataByQuery(query);

                    querySearch=query;

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

               if(!newText.equals("")) {

                   showAutoCompData(newText);
               }
               else {
                   recyclerViewSearch.setVisibility(View.INVISIBLE);
                   recyclerViewAutoComp.setVisibility(View.INVISIBLE);
                   showTagsList();
                   sharedPreferences.edit().remove("searchWord").apply();
               }
                return false;
            }

        });

    }

    private void showSearchDataByQuery(String query) {

            sharedPreferences.edit().putString("searchWord",query).apply();

                 callSearchByQuery =apiInterface.getSearchDataByQuery(ApiClient.key,query);

                 callSearchByQuery.enqueue(new Callback<SearchRoot>() {
                     @Override
                     public void onResponse(Call<SearchRoot> call, Response<SearchRoot> response) {

                         if(response.isSuccessful()){

                             recyclerViewAutoComp.setVisibility(View.GONE);

                             recyclerViewSearch.setVisibility(View.VISIBLE);

                             imageButtonFilter.setVisibility(View.VISIBLE);

                             ResultAdapterSearch resultAdapterSearch = new ResultAdapterSearch(response.body().getResults() , SearchFragment.this);

                             recyclerViewSearch.setAdapter(resultAdapterSearch);

                             recyclerViewSearch.setLayoutManager(new GridLayoutManager(getContext(),2));

                         }

                     }

                     @Override
                     public void onFailure(Call<SearchRoot> call, Throwable t) {

                     }
                 });



    }

    private void showSearchDataByTag(String displayName){

        sharedPreferences.edit().putString("displayName",displayName).apply();

        callSearchByTag =apiInterface.getSearchDataByTag(ApiClient.key,displayName.toLowerCase());

        callSearchByTag.enqueue(new Callback<SearchRoot>() {
            @Override
            public void onResponse(Call<SearchRoot> call, Response<SearchRoot> response) {

                if(response.isSuccessful()){

                        recyclerViewTag.setVisibility(View.GONE);

                        recyclerViewSearch.setVisibility(View.VISIBLE);

                        ResultAdapterSearch resultAdapterSearch = new ResultAdapterSearch(response.body().getResults(), SearchFragment.this);

                        recyclerViewSearch.setAdapter(resultAdapterSearch);

                        recyclerViewSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    }

                }


            @Override
            public void onFailure(Call<SearchRoot> call, Throwable t) {

            }
        });

    }

    private void showSearchDataByQueryAndTag(String query,String displayName){

        sharedPreferences.edit().remove("searchWord").apply();
        sharedPreferences.edit().putString("search",query+" "+displayName).apply();

        callSearchByQueryAndTag = apiInterface.getSearchDataByQueryAndTag(ApiClient.key,query,displayName.toLowerCase());

        callSearchByQueryAndTag.enqueue(new Callback<SearchRoot>() {
            @Override
            public void onResponse(Call<SearchRoot> call, Response<SearchRoot> response) {

                if(response.body()!=null){

                    recyclerViewSearch.setVisibility(View.VISIBLE);

                    ResultAdapterSearch resultAdapterSearch = new ResultAdapterSearch(response.body().getResults() , SearchFragment.this);

                    recyclerViewSearch.setAdapter(resultAdapterSearch);

                    recyclerViewSearch.setLayoutManager(new GridLayoutManager(getContext(),2));
                }
                else {
                    Toast.makeText(getContext(), "There is no data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchRoot> call, Throwable t) {

            }
        });

    }

    private void backToHomeFragment(){

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
                recyclerViewSearch.setVisibility(View.GONE);
                recyclerViewTag.setVisibility(View.GONE);
                recyclerViewAutoComp.setVisibility(View.GONE);

            }
        });
    }

    private void showAutoCompData(String newText){

        recyclerViewAutoComp.setVisibility(View.VISIBLE);
        recyclerViewTag.setVisibility(View.GONE);
        recyclerViewSearch.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        imageButtonFilter.setVisibility(View.GONE);


        callAutoComp =apiInterface.getAllAutoCompData(ApiClient.key,newText);

            callAutoComp.enqueue(new Callback<SearchRoot>() {
                @Override
                public void onResponse(Call<SearchRoot> call, Response<SearchRoot> response) {

                    if(response.isSuccessful()){

                        ItemAdapterAutoComp itemAdapterAutoComp=new ItemAdapterAutoComp(response.body().getResults(),SearchFragment.this);
                        recyclerViewAutoComp.setAdapter(itemAdapterAutoComp);
                        recyclerViewAutoComp.setLayoutManager(new LinearLayoutManager(getContext()));

                    }

                }

                @Override
                public void onFailure(Call<SearchRoot> call, Throwable t) {

                }
            });

    }

    private void showTagsList(){

        recyclerViewTag.setVisibility(View.VISIBLE);

        tagRootCall=apiInterface.getAllTags(ApiClient.key);

        tagRootCall.enqueue(new Callback<TagRoot>() {
            @Override
            public void onResponse(Call<TagRoot> call, Response<TagRoot> response) {

             List <String> stringTypeList = getTypeList(response);

             List <String> list = getDisplayList(response,stringTypeList);

                    ItemAdapterTag itemAdapterTag = new ItemAdapterTag(list,stringTypeList,SearchFragment.this);
                    recyclerViewTag.setAdapter(itemAdapterTag);
                    recyclerViewTag.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<TagRoot> call, Throwable t) {

            }
        });

    }

    private List <String> getTypeList(Response<TagRoot> response){

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

    private void removeSearchDataByTag(){

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.GONE);
                recyclerViewSearch.setVisibility(View.INVISIBLE);

                if(querySearch.equals("")){
                    showTagsList();
                    imageButtonFilter.setVisibility(View.INVISIBLE);
                    sharedPreferences.edit().remove("displayName").apply();
                }
                else {
                    showSearchDataByQuery(querySearch);
                    sharedPreferences.edit().remove("search").apply();
                    sharedPreferences.edit().putString("searchWord",querySearch).apply();
                }
            }
        });
    }

    private void imageButtonFilterClick(){
        imageButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.show(getActivity().getSupportFragmentManager(),"");

            }
        });
    }

    @Override
    public void getSearchDataByTag(String tagName , String displayName) {

        textView.setVisibility(View.VISIBLE);
        textView.setText(displayName+"   ");
        imageButtonFilter.setVisibility(View.VISIBLE);
        showSearchDataByTag(displayName);

    }

    @Override
    public void getSearchDataByQuery(String searchWord) {

        showSearchDataByQuery(searchWord);
        searchView.setQuery(searchWord,true);

    }

    @Override
    public void moveFragment(DetailsFragment detailsFragment) {
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.placeholder,detailsFragment).commit();
    }

    private void mainMethod(){

        if(sharedPreferences.contains("searchWord")){

            String searchWord = sharedPreferences.getString("searchWord","");

            getSearchDataByQuery(searchWord);

        }
        else if(sharedPreferences.contains("displayName")){

            String displayName = sharedPreferences.getString("displayName","");

            getSearchDataByTag(displayName.toLowerCase(),displayName);

        }
        else if (sharedPreferences.contains("search")){

            String [] search = sharedPreferences.getString("search","").split(" ");

            recyclerViewSearch.setVisibility(View.GONE);

            textView.setVisibility(View.VISIBLE);
            textView.setText(search[1]+"   ");
            searchView.setQuery(search[0],true);
            imageButtonFilter.setVisibility(View.VISIBLE);

            showSearchDataByQueryAndTag(search[0],search[1]);

        }

        else {

            showTagsList();

        }
    }

    @Override
    public void getSearch(String tagName, String displayName) {

        if(textView.getVisibility()==View.VISIBLE && querySearch.equals("")){

            bottomSheetDialog.dismiss();

            recyclerViewSearch.setVisibility(View.GONE);

            getSearchDataByTag(tagName,displayName);

        }
        else  {

            bottomSheetDialog.dismiss();

            recyclerViewSearch.setVisibility(View.GONE);

            textView.setVisibility(View.VISIBLE);
            textView.setText(displayName+"   ");

            showSearchDataByQueryAndTag(querySearch,displayName);

        }

    }


}
