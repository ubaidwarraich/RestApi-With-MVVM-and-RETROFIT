package com.codingwithmitch.foodrecipes.repositories;

import android.provider.MediaStore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.codingwithmitch.foodrecipes.models.Recipe;
import com.codingwithmitch.foodrecipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private RecipeApiClient mRecipeApiClient;
    private String mQuery;
    private int mPageNumber;
    // both below are for paging
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> mRecipes = new MediatorLiveData<>();

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository() {
        mRecipeApiClient = RecipeApiClient.getInstance();
        initMediators();
    }

    // mediator is used to control the logic of displaying the text no more result at the end of recycler view if there are no more results left
    //instead of showing the loading dots continuously
    // in below method we r observing the recipes list coming from apiCLient and then we ill see if it has data or not
    //based on that we will then return the mediator recipe list
    private void initMediators() {
        LiveData<List<Recipe>> recipeListApiSource = mRecipeApiClient.getRecipes();
        mRecipes.addSource(recipeListApiSource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null) {
                    mRecipes.setValue(recipes);
                    doneQuery(recipes);
                } else {
                    //search database
                    doneQuery(null);
                }
            }
        });
    }


    //this method is checking the list being returned by the api client and then checking if results are less then 30 results
    //which means there will be no more results
    public void doneQuery(List<Recipe> list) {
        if (list != null) {
            if (list.size() % 30!=0) {
                mIsQueryExhausted.setValue(true);
            }
        }
        //list is null
        else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted(){
        return this.mIsQueryExhausted;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeApiClient.getRecipe();
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mIsQueryExhausted.setValue(false);
        mRecipeApiClient.searchRecipesApi(query, pageNumber);
    }

    public void cancelRequest() {
        mRecipeApiClient.cancelRequest();
    }

    public void searchRecipeById(String recipeId) {
        mRecipeApiClient.searchRecipeById(recipeId);
    }

    public void searchNextPage() {
        searchRecipesApi(mQuery, mPageNumber + 1);
    }

    public LiveData<Boolean> isRecipeRequestTimedOut() {
        return mRecipeApiClient.IsRecipeRequestTimeout();
    }
}
