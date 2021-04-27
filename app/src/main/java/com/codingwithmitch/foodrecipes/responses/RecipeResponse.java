package com.codingwithmitch.foodrecipes.responses;

import com.codingwithmitch.foodrecipes.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {
//    expose is used by gson to serialize and deserialize the response objects
    @SerializedName("recipe")
    @Expose()
    private Recipe recipe;

    public Recipe gerRecipe(){
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
