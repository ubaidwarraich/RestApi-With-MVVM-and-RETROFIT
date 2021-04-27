package com.codingwithmitch.foodrecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.codingwithmitch.foodrecipes.models.Recipe;
import com.codingwithmitch.foodrecipes.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private String mRecipeId;
    private boolean mDidRetrieveRecipe;

    public RecipeViewModel() {
        recipeRepository = RecipeRepository.getInstance();
        mDidRetrieveRecipe = false;
    }

    public boolean DidRetrieveRecipe() {
        return mDidRetrieveRecipe;
    }

    public void setDidRetrieveRecipe(boolean mDidRetrieveRecipe) {
        this.mDidRetrieveRecipe = mDidRetrieveRecipe;
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe();
    }

    public void searchRecipeById(String recipeId) {
        mRecipeId = recipeId;
        recipeRepository.searchRecipeById(recipeId);
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public LiveData<Boolean> isRecipeRequestTimedOut() {
        return recipeRepository.isRecipeRequestTimedOut();
    }
}
