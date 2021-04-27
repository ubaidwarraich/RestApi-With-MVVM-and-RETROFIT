package com.codingwithmitch.foodrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codingwithmitch.foodrecipes.models.Recipe;
import com.codingwithmitch.foodrecipes.viewmodels.RecipeViewModel;

import org.w3c.dom.Text;

public class RecipeActivity extends BaseActivity {
    private static final String TAG = "RecipeActivity";
    //    ui components attach them
    private ImageView recipeImage;
    private TextView recipeTitle, recipeRank;
    private LinearLayout recipeIngredientsContainer;
    private ScrollView scrollView;
    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeImage = findViewById(R.id.recipe_image);
        recipeTitle = findViewById(R.id.recipe_title);
        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        scrollView = findViewById(R.id.parent);
        recipeRank = findViewById(R.id.recipe_social_score);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        showProgressBar(true);
        subscribeObservers();
        getIncomingIntent();

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            recipeViewModel.searchRecipeById(recipe.getRecipe_id());

        }
    }

    private void subscribeObservers() {
        recipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {
                    Log.d(TAG, "onChanged: Previous Recipe ID" + recipeViewModel.getRecipeId());
                    if (recipe.getRecipe_id().equals(recipeViewModel.getRecipeId())) {
                        setRecipeproperties(recipe);
                        recipeViewModel.setDidRetrieveRecipe(true);
                    }
                }
            }
        });

        recipeViewModel.isRecipeRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !recipeViewModel.DidRetrieveRecipe()) {
                    displayErrorScreen("Error retrieving data. Check Network Connection");
                }
            }
        });
    }

    private void displayErrorScreen(String errorMessage){
        recipeTitle.setText("Error Retrieving recipe...");
        recipeRank.setText("");
        TextView textView=new TextView(this);
        if(!errorMessage.equals("")){
            textView.setText(errorMessage);
        }
        else{
            textView.setText("Error");
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        recipeIngredientsContainer.addView(textView);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_launcher_background)
                .into(recipeImage);

        showParent();
        showProgressBar(false);
    }
    private void setRecipeproperties(Recipe recipe) {
        if (recipe != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipe.getImage_url())
                    .into(recipeImage);

            recipeTitle.setText(recipe.getTitle());
            recipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

            recipeIngredientsContainer.removeAllViews();
            for (String ingredient : recipe.getIngredients()) {
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                recipeIngredientsContainer.addView(textView);
            }
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent() {
        scrollView.setVisibility(View.VISIBLE);
    }

}
