package com.codingwithmitch.foodrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Recipe implements Parcelable {
    private String title;
    private String publisher;
    private String[] ingredients;
    private String id;
    private String imageUrl;
    private float socialUrl;

    public Recipe(String title, String publisher, String[] ingredients, String recipe_id, String image_url, float social_rank) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.id = recipe_id;
        this.imageUrl = image_url;
        this.socialUrl = social_rank;
    }

    public Recipe(){}

    protected Recipe(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        id = in.readString();
        imageUrl = in.readString();
        socialUrl = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe_id() {
        return id;
    }

    public void setRecipe_id(String recipe_id) {
        this.id = recipe_id;
    }

    public String getImage_url() {
        return imageUrl;
    }

    public void setImage_url(String image_url) {
        this.imageUrl = image_url;
    }

    public float getSocial_rank() {
        return socialUrl;
    }

    public void setSocial_rank(float social_rank) {
        this.socialUrl = social_rank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipe_id='" + id + '\'' +
                ", image_url='" + imageUrl + '\'' +
                ", social_rank=" + socialUrl +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeStringArray(ingredients);
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeFloat(socialUrl);
    }
}
