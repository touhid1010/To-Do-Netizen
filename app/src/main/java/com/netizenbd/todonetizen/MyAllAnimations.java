package com.netizenbd.todonetizen;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by n on 8/3/2016.
 */
public class MyAllAnimations {

    /**
     * HomeActivity.java animations
     *
     */
    public void floatingActionButtonAppearance(Context context, View floatingActionBar){
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.animation_floationg_button_appearance_middle_to_bottom);
        floatingActionBar.startAnimation(shake);
    }

    public void floatingActionButtonIfNoInstitution(Context context, View floatingActionBar){
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.animation_floationg_button_if_no_institution);
        floatingActionBar.startAnimation(shake);
    }


















}
