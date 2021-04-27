package com.codingwithmitch.foodrecipes.requests;

import com.codingwithmitch.foodrecipes.responses.RecipeResponse;
import com.codingwithmitch.foodrecipes.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApi {

    //    Search
    @GET("api/v2/recipes")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("q") String query,
            @Query("page") String page
    );

    //Get Recipe Request
    @GET("api/v2/recipes/{Id}")
    Call<RecipeResponse> getRecipe(
            @Path("Id") String recipe_id
    );
}
