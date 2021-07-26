package com.example.tasty.view;

import android.os.AsyncTask;
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

import com.example.tasty.R;
import com.example.tasty.adapter.MyRecipeAdapter;
import com.example.tasty.adapter.ResultAdapterSearch;
import com.example.tasty.dataBase.Recipe;
import com.example.tasty.dataBase.RecipeDao;
import com.example.tasty.dataBase.RecipeDataBase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRecipeFragment extends Fragment implements MyRecipeAdapter.OnDetailAction {


    RecyclerView recyclerView;
    RecipeDataBase recipeDataBase;
    RecipeDao recipeDao;

    public MyRecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        new SelectTask().execute();

    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.recycler_myRecipe);
        recipeDataBase=RecipeDataBase.getAppDatabase(getContext());
        recipeDao=recipeDataBase.getRecipeDao();
    }

    @Override
    public void moveFragment(DetailsFragment detailsFragment) {
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("")
                .replace(R.id.placeholder,detailsFragment).commit();
    }

    public class SelectTask extends AsyncTask<Void,Void, List<Recipe>>{

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            return recipeDao.getAllData();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);

            MyRecipeAdapter myRecipeAdapter = new MyRecipeAdapter(recipes,MyRecipeFragment.this);
            recyclerView.setAdapter(myRecipeAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        }
    }
}
