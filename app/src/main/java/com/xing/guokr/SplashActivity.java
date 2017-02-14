package com.xing.guokr;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xing.guokr.http.HttpApi;
import com.xing.guokr.pro.prefer.AppConfig;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.activity_splash, null);
        setContentView(rootView);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(rootView, "alpha", 0.9f, 1);
        objectAnimator.setDuration(2000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                objectAnimator.removeListener(this);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
        objectAnimator.start();
    }
}
