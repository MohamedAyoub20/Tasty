package com.example.tasty.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;



import com.example.tasty.R;
import com.example.tasty.adapter.ItemAdapterHorizontal;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity  implements
        ItemAdapterHorizontal.OnDetailAction   {

    SharedPreferences sharedPreferences;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getSupportFragmentManager().beginTransaction().replace(R.id.placeholder,new HomeFragment()).commit();

        addTabItems();

        changeTabItem();

    }

    private void init(){
        sharedPreferences=getSharedPreferences("saveData",MODE_PRIVATE);
        tabLayout = findViewById(R.id.tabLayout);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences.edit().remove("searchWord").apply();
        sharedPreferences.edit().remove("displayName").apply();
        sharedPreferences.edit().remove("Fragment").apply();
        sharedPreferences.edit().remove("search").apply();
        sharedPreferences.edit().remove("tab").apply();
    }


    @Override
    public void moveFragment(DetailsFragment detailsFragment){
            getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.placeholder,detailsFragment).commit();
    }

    private void addTabItems(){

        TabLayout.Tab discoverTab = tabLayout.newTab();
        discoverTab.setText("discover");
        discoverTab.setIcon(R.drawable.ic_bottom_discover_active);

        TabLayout.Tab recipeTab = tabLayout.newTab();
        recipeTab.setText("my recipe");
        recipeTab.setIcon(R.drawable.ic_bottom_favorite_inactive);

        tabLayout.addTab(discoverTab);
        tabLayout.addTab(recipeTab);


    }

    private void changeTabItem(){

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TabLayout.Tab discoverTab = tabLayout.getTabAt(0);
                TabLayout.Tab recipeTab = tabLayout.getTabAt(1);

                if(tab.getPosition()==0){

                   moveToFragment();

                    discoverTab.setIcon(R.drawable.ic_bottom_discover_active);
                    recipeTab.setIcon(R.drawable.ic_bottom_favorite_inactive);

                    sharedPreferences.edit().putInt("tab", 0).apply();

                }

                else {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.placeholder,new MyRecipeFragment()).commit();

                    discoverTab.setIcon(R.drawable.ic_bottom_discover_inactive);
                    recipeTab.setIcon(R.drawable.ic_bottom_favorite);

                    sharedPreferences.edit().putInt("tab", 1).apply();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void moveToFragment(){

        if(sharedPreferences.getInt("Fragment",-1)==0){

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.placeholder,new HomeFragment()).commit();

        }
        else if(sharedPreferences.getInt("Fragment",-1)==1){

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.placeholder,new SearchFragment()).commit();

        }
        else {

              DetailsFragment detailsFragment = new DetailsFragment();
              Bundle bundle = new Bundle();
              bundle.putInt("id",sharedPreferences.getInt("Fragment",-1));
              detailsFragment.setArguments(bundle);

              getSupportFragmentManager().beginTransaction()
                    .replace(R.id.placeholder,detailsFragment).commit();

        }
    }

}


