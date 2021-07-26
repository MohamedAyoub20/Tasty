package com.example.tasty.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.tasty.R;
import com.example.tasty.adapter.ItemAdapterComponent;
import com.example.tasty.adapter.ItemAdapterInstruction;
import com.example.tasty.apiConnection.ApiClient;
import com.example.tasty.apiConnection.ApiInterface;
import com.example.tasty.dataBase.Recipe;
import com.example.tasty.dataBase.RecipeDao;
import com.example.tasty.dataBase.RecipeDataBase;
import com.example.tasty.model.Component;
import com.example.tasty.model.Instruction;
import com.example.tasty.model.Item;
import com.example.tasty.model.Nutrition;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    ApiInterface apiInterface;
    Call<Item> call;
    RecyclerView  componentRecyclerView ,instructionRecyclerView ;
    TextView ingredientTextView , totalTimeTextView , prepTimeTextView , cookTimeTextView
            ,textViewTotalTime , textViewPrepTime , textViewCookTime ,nameTextView,likePercentageTextView ;
    VideoView videoView;
    ImageButton backButton, shareButton , favoriteButton ;
    Intent shareIntent;
    Button buttonNutrition;
    Bundle bundle;
    RelativeLayout relativeLayout;
    ListView listView;
    SharedPreferences sharedPreferences;
    RecipeDataBase recipeDataBase;
    RecipeDao recipeDao;
    LinearLayout linearLayout;
    int idRecipe;

    boolean stateVisible=false;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        if(sharedPreferences.getInt("tab",0)==0){

            new SelectId().execute();

            sharedPreferences.edit().putInt("Fragment",bundle.getInt("id")).apply();

            showData();
        }
        else {

            idRecipe = bundle.getInt("id");
            //new SelectTask().execute();

        }

        backTOFragment();

    }

    private void init(View view){
        bundle=getArguments();

        sharedPreferences=getActivity().getSharedPreferences("saveData",getActivity().MODE_PRIVATE);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        call=apiInterface.getItem(ApiClient.key,bundle.getInt("id"));
        recipeDataBase = RecipeDataBase.getAppDatabase(getContext());
        recipeDao = recipeDataBase.getRecipeDao();
        shareIntent=new Intent(Intent.ACTION_SEND);

        id(view);

    }

    private void id(View view){
        nameTextView=view.findViewById(R.id.itemName_tv);
        likePercentageTextView=view.findViewById(R.id.likePercentage);
        videoView=view.findViewById(R.id.itemVideo);
        ingredientTextView=view.findViewById(R.id.ingredient_tv);
        totalTimeTextView=view.findViewById(R.id.totalTime_tv);
        prepTimeTextView=view.findViewById(R.id.prepTime_tv);
        cookTimeTextView=view.findViewById(R.id.cookTime_tv);
        textViewCookTime=view.findViewById(R.id.cook_T);
        textViewPrepTime=view.findViewById(R.id.Prep_T);
        textViewTotalTime=view.findViewById(R.id.Total_T);
        componentRecyclerView=view.findViewById(R.id.recycler_component);
        instructionRecyclerView=view.findViewById(R.id.recycler_instruction);
        backButton =view.findViewById(R.id.backFragment_imageView);
        shareButton=view.findViewById(R.id.share_bt);
        buttonNutrition=view.findViewById(R.id.showNutrition_bt);
        relativeLayout=view.findViewById(R.id.relativeLayout_Nutrition);
        listView=view.findViewById(R.id.showNutrition_LV);
        favoriteButton=view.findViewById(R.id.favorite_bt);
        linearLayout=view.findViewById(R.id.linearLayoutDetail);
        linearLayout.setVisibility(View.GONE);
    }

    private void showData(){
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {

                if(response.isSuccessful()){

                    linearLayout.setVisibility(View.VISIBLE);

                    Item item=response.body();


                    setDetails(item.getName(),item.getUserRating().getScore(),item.getVideoUrl());

                    if(item.getSections().get(0)!=null){

                        setComponent(item.getSections().get(0).getComponents());
                    }
                    else {
                        componentRecyclerView.setVisibility(View.GONE);
                    }

                    if(item.getInstructions()!=null){

                        setInstruction(item.getInstructions());
                    }
                    else {
                        instructionRecyclerView.setVisibility(View.GONE);
                    }

                    setTime(item.getNumberServings(), item.getCookTime() , item.getPrepTime());

                    setNutrition(item.getNutrition());

                    shareData(item.getSlug(),item.getSections().get(0).getComponents());

                    favoriteButtonClick(item);
                }

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });
    }

    private void showFavoriteData(Recipe recipe){
/*
        linearLayout.setVisibility(View.VISIBLE);

        setDetails(recipe.getName(), recipe.getUserRating().getScore() , recipe.getVideoUrl());

        if(recipe.getSections().get(0)!=null){

            setComponent(recipe.getSections().get(0).getComponents());
        }
        else {
            componentRecyclerView.setVisibility(View.GONE);
        }

        if(recipe.getInstructions()!=null){

            setInstruction(recipe.getInstructions());
        }
        else {
            instructionRecyclerView.setVisibility(View.GONE);
        }

        setTime(recipe.getNumberServings() , recipe.getCookTime() , recipe.getPrepTime());

        setNutrition(recipe.getNutrition());

        shareData(recipe.getSlug(),recipe.getSections().get(0).getComponents());

        favoriteButton.setVisibility(View.GONE);*/
    }

    private void setDetails(String name , double Score , String videoUrl){

        nameTextView.setText(name);


        double score = Score*100;

        if(String.format("%.0f",score).equals("0")){

            likePercentageTextView.setVisibility(View.GONE);
        }
        else {
           likePercentageTextView.setText("   "+String.format("%.0f",score)+" % "+"would make this again");
        }

        if(videoUrl!=null) {

           videoView.setVideoPath(videoUrl);
            videoView.start();

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.start();
                }
            });

        }
        else {
           videoView.setVisibility(View.GONE);
        }

    }

    private void setComponent(List<Component>components){

        ItemAdapterComponent itemAdapterComponent= new ItemAdapterComponent(components);

        componentRecyclerView.setAdapter(itemAdapterComponent);

        componentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setInstruction(List<Instruction> instructionList){
        ItemAdapterInstruction itemAdapterInstruction = new ItemAdapterInstruction(instructionList);

        instructionRecyclerView.setAdapter(itemAdapterInstruction);

        instructionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void  setTime(int numberServings , int cookTime , int prepTime){

          ingredientTextView.setText("Ingredients"+"\n"+"for "+numberServings+" servings");

          if(cookTime>0){
              totalTimeTextView.setText((prepTime+cookTime)+" min");

              prepTimeTextView.setText(prepTime+" min");

              cookTimeTextView.setText(cookTime+" min");
          }
          else {
              totalTimeTextView.setVisibility(View.GONE);
              prepTimeTextView.setVisibility(View.GONE);
              cookTimeTextView.setVisibility(View.GONE);
              textViewTotalTime.setVisibility(View.GONE);
              textViewPrepTime.setVisibility(View.GONE);
              textViewCookTime.setVisibility(View.GONE);
          }

    }

    private void setNutrition(Nutrition nutrition){

        if(nutrition.getCalories()==0 && nutrition.getCarbohydrates()==0 && nutrition.getFat()==0
        && nutrition.getProtein()==0 && nutrition.getSugar()==0 && nutrition.getFiber()==0){

            relativeLayout.setVisibility(View.GONE);
        }
        else {

            final List<String> stringList=new ArrayList();

            stringList.add("calories                  "+nutrition.getCalories());
            stringList.add("carbohydrates       "+nutrition.getCarbohydrates()+" g");
            stringList.add("fat                           "+nutrition.getFat()+" g");
            stringList.add("protein                    "+nutrition.getProtein()+" g");
            stringList.add("sugar                      "+nutrition.getSugar()+" g");
            stringList.add("fiber                        "+nutrition.getFiber()+" g");

            ArrayAdapter<String> arrayAdapter= new ArrayAdapter
                    (getContext(),R.layout.item_layout_nutrition,R.id.nutrition_text,stringList);

            listView.setAdapter(arrayAdapter);

            buttonNutrition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(stateVisible==false){

                        listView.setVisibility(View.VISIBLE);
                        stateVisible=true;
                        buttonNutrition.setText("Hide Info -");

                    }
                    else {

                        listView.setVisibility(View.GONE);
                        stateVisible=false;
                        buttonNutrition.setText("Show info +");

                    }
                }
            });
        }

    }

    private void backTOFragment(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                getActivity().onBackPressed();
            }
        });
    }

    private void shareData(final String slugItem, final List<Component>componentList){

        final PopupMenu popupMenu=new PopupMenu(getContext(),shareButton);
        popupMenu.getMenu().add("Share recipe link");
        popupMenu.getMenu().add("Share ingredients list");

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                         if (item.getTitle().equals("Share recipe link")){

                             shareRecipeLink(slugItem);

                         }
                         else if(item.getTitle().equals("Share ingredients list")){

                             shareIngredientsList(componentList);

                         }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private void shareIngredientsList(final List<Component>components){

        List<String>instructions=new ArrayList();

        for(int i=0;i<components.size();i++){
            instructions.add(components.get(i).getRawText());
        }

        shareIntent.setType("text/*");

        shareIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(instructions));

        startActivity(shareIntent);
    }

    private void shareRecipeLink(String slugItem){

        String recipeLink="https://tasty.co/recipe/"+slugItem;

        shareIntent.setType("text/*");

        shareIntent.putExtra(Intent.EXTRA_TEXT, recipeLink);

        startActivity(shareIntent);

        //https://tasty.co/recipe/blackberry-freezer-jam
    }

    private void favoriteButtonClick(Item item){

        final Recipe recipe = new Recipe();
        recipe.setId(item.getId());
        recipe.setName(item.getName());
        recipe.setThumbnailUrl(item.getThumbnailUrl());
       /* recipe.setInstructions(item.getInstructions());
        recipe.setNumberServings(item.getNumberServings());
        recipe.setNutrition(item.getNutrition());
        recipe.setCookTime(item.getCookTime());
        recipe.setPrepTime(item.getPrepTime());
        recipe.setSections(item.getSections());
        recipe.setSlug(item.getSlug());
        recipe.setUserRating(item.getUserRating());
        recipe.setVideoUrl(item.getVideoUrl());*/

        favoriteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

            if(favoriteButton.getDrawable().getConstantState()==favoriteButton.getContext()
                    .getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){

                favoriteButton.setImageResource(R.drawable.ic_bottom_favorite_active);

                new InsertTask().execute(recipe);

            }
            else {

                favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                new DeleteTask().execute(recipe);

            }


           }

       });


    }

    public class InsertTask extends AsyncTask<Recipe,Void,Void>{

        @Override
        protected Void doInBackground(Recipe... recipes) {

            recipeDao.insertRecipe(recipes[0]);

            return null;
        }
    }

    public class DeleteTask extends AsyncTask<Recipe , Void , Void>{

        @Override
        protected Void doInBackground(Recipe... recipes) {

            recipeDao.deleteRecipe(recipes[0]);

            return null;
        }
    }

    public class SelectTask extends AsyncTask<Void, Void , Recipe>{


        @Override
        protected Recipe doInBackground(Void... voids) {
            return recipeDao.getRecipe(idRecipe);
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            super.onPostExecute(recipe);

            showFavoriteData(recipe);
        }
    }

    public class SelectId extends AsyncTask<Void, Void , List<Integer>>{

        @Override
        protected List<Integer> doInBackground(Void... voids) {
            return recipeDao.getAllId();
        }

        @Override
        protected void onPostExecute(List<Integer> integers) {
            super.onPostExecute(integers);

            if(integers.contains(bundle.getInt("id"))){

                favoriteButton.setImageResource(R.drawable.ic_bottom_favorite_active);
            }

        }
    }

}
