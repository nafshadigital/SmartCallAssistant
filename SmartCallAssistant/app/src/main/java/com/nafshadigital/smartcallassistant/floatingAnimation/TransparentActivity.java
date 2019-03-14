package com.nafshadigital.smartcallassistant.floatingAnimation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.nafshadigital.smartcallassistant.R;

public class TransparentActivity extends Activity {
    Context context;

    public void flyEmoji(final int resId) {
        ZeroGravityAnimation animation = new ZeroGravityAnimation();
        animation.setCount(1);
        animation.setScalingFactor(1f);
        animation.setOriginationDirection(Direction.BOTTOM);
        animation.setDestinationDirection(Direction.TOP);
        animation.setImage(resId);
        animation.setAnimationListener(new Animation.AnimationListener() {
                                           @Override
                                           public void onAnimationStart(Animation animation) {

                                           }
                                           @Override
                                           public void onAnimationEnd(Animation animation) {

                                           }

                                           @Override
                                           public void onAnimationRepeat(Animation animation) {

                                           }
                                       }
        );

        ViewGroup container = findViewById(R.id.animation_holder);
        animation.playWithEnd(this,container);

    }

    public void emoji_one() {
        // You can change the number of emojis that will be flying on screen
        for (int i = 0; i < 5; i++) {
            flyEmoji(R.drawable.ic_heart);
        }
    }
    // You can change the number of emojis that will be flying on screen

    public void emoji_two(){
        for(int i=0;i<15;i++) {
            flyEmoji(R.drawable.ic_heart);
        }

    }
    // You can change the number of emojis that will be flying on screen

    public void emoji_three(){
        for(int i=0;i<5;i++) {
            flyEmoji(R.drawable.ic_heart);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        super.onCreate(savedInstanceState);
        emoji_one();
        emoji_two();
        emoji_three();



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
