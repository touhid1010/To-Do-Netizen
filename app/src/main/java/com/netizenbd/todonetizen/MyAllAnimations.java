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
     */
    public void floatingActionButtonsAppearance_middleToBottom(final Context context, final View floatingActionBar, final View view) {
        Animation midToBottom = AnimationUtils.loadAnimation(context, R.anim.animation_floationg_button_appearance_middle_to_bottom);
        midToBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // animation to highlight new institution
                if (view.getVisibility() == View.VISIBLE) {
                    Animation shake = AnimationUtils.loadAnimation(context, R.anim.animation_floationg_button_if_no_institution);
                    floatingActionBar.startAnimation(shake);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        floatingActionBar.startAnimation(midToBottom);

    }


    public void floatingActionButtonsAppearance_middleToTop(Context context, View floatingActionBar) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.animation_floationg_button_appearance_middle_to_top);
        floatingActionBar.startAnimation(shake);
    }

//    public void floatingActionButtonIfNoInstitution(Context context, View floatingActionBar) {
//        Animation shake = AnimationUtils.loadAnimation(context, R.anim.animation_floationg_button_if_no_institution);
//        floatingActionBar.startAnimation(shake);
//    }


}
