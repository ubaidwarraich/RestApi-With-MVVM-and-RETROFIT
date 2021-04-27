package com.codingwithmitch.foodrecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codingwithmitch.foodrecipes.models.Recipe;
import com.codingwithmitch.foodrecipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;
    private boolean mIsViewingRecipes;
    private boolean mIsPerformingQuery;

    public RecipeListViewModel() {
        mIsPerformingQuery=false;
        mIsViewingRecipes=false;
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mRecipeRepository.isQueryExhausted();
    }

    public boolean isIsPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setIsPerformingQuery(boolean mIsPerformingQuery) {
        this.mIsPerformingQuery = mIsPerformingQuery;
    }

    public boolean IsViewingRecipes() {
        return mIsViewingRecipes;
    }

    public void setIsViewingRecipes(boolean mIsViewingRecipes) {
        this.mIsViewingRecipes = mIsViewingRecipes;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }
    public void searchRecipesApi(String query,int pageNumber){
        mIsPerformingQuery=true;
        mIsViewingRecipes=true;
        mRecipeRepository.searchRecipesApi(query,pageNumber);
    }

    public boolean onBackpressed(){
        if(mIsPerformingQuery){
            // cancel the query
            mRecipeRepository.cancelRequest();
            setIsPerformingQuery(false);
        }
        if(mIsViewingRecipes){
            mIsViewingRecipes=false;
            return false;
        }
        return true;
    }

    public void searchNextPage(){
        if(!mIsPerformingQuery && mIsViewingRecipes && !isQueryExhausted().getValue()){
            mRecipeRepository.searchNextPage();
        }
    }
}
