package com.codingwithmitch.foodrecipes.adapters;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codingwithmitch.foodrecipes.R;
import com.codingwithmitch.foodrecipes.models.Recipe;
import com.codingwithmitch.foodrecipes.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecipeRecyclerAdapter";
    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    public static final int CATEGORY_TYPE = 3;
    public static final int EXHAUSTED_TYPE = 4;

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener mOnRecipeListener) {
        this.mOnRecipeListener = mOnRecipeListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        // checking view type and then inflating views
        switch (viewType) {
            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_exhausted, viewGroup, false);
                return new SearchExhaustedViewHolder(view);
            }
            case CATEGORY_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_list_item, viewGroup, false);
                return new CategoryViewHolder(view, mOnRecipeListener);
            }
            case RECIPE_TYPE:
            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }
        }

    }

    //    view type is set in this method by overridding it and providing my own logic
    @Override
    public int getItemViewType(int position) {
        if (mRecipes.get(position).getSocial_rank() == -1) {
            return CATEGORY_TYPE;
        } else if (mRecipes.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (mRecipes.get(position).getTitle().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        } else if (position == mRecipes.size() - 1 &&
                position != 0 &&
                !mRecipes.get(position).getTitle().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void setQueryExhausted(){
        hideLoading();
        Recipe exhaustedRecipe=new Recipe();
        exhaustedRecipe.setTitle("EXHAUSTED...");
        mRecipes.add(exhaustedRecipe);
        notifyDataSetChanged();
    }

    private void hideLoading(){
        if(isLoading()){
            for(Recipe recipe:mRecipes){
                if(recipe.getTitle().equals("LOADING...")){
                    mRecipes.remove(recipe);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadinglist = new ArrayList<>();
            loadinglist.add(recipe);
            mRecipes = loadinglist;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (mRecipes != null) {
            if (mRecipes.size() > 0) {
                if (mRecipes.get(mRecipes.size() - 1).getTitle().equals("LOADING...")) {
                    return true;
                }
            }
        }

        return false;
    }

    public void displaySearchCategories() {
        List<Recipe> categories = new ArrayList<>();
        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++) {
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
        }
        mRecipes = categories;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mRecipes.get(position).getImage_url())
                    .into(((RecipeViewHolder) holder).image);

            ((RecipeViewHolder) holder).title.setText(mRecipes.get(position).getTitle());
            ((RecipeViewHolder) holder).socialScore.setText(String.valueOf(Math.round(mRecipes.get(position).getSocial_rank())));

        } else if (itemViewType == CATEGORY_TYPE) {

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Uri path = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/drawable/" + mRecipes.get(position).getImage_url());
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(((CategoryViewHolder) holder).categoryImage);
            ((CategoryViewHolder) holder).categoryTitle.setText(mRecipes.get(position).getTitle());


        }

    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) {
            return mRecipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getSelectedRecipe(int pos) {
        if (mRecipes != null) {
            if (mRecipes.size() > 0) {
                return mRecipes.get(pos);
            }
        }
        return null;
    }
}
