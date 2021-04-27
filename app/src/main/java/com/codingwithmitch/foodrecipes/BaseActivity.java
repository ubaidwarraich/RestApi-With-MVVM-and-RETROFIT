package com.codingwithmitch.foodrecipes;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

//declaring abstract because we dont want this activity to be registered in menifest file
public abstract class BaseActivity extends AppCompatActivity {
     public ProgressBar progressBar;
    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout=(ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base,null);
        FrameLayout frameLayout=constraintLayout.findViewById(R.id.activity_content);
        progressBar=constraintLayout.findViewById(R.id.progress_bar);

        getLayoutInflater().inflate(layoutResID,frameLayout,true);
        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visibility){
        progressBar.setVisibility(visibility? View.VISIBLE:View.INVISIBLE);
    }
}
