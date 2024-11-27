package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    ImageView img ;
    TextView txt;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        img = findViewById(R.id.splashImg);
        txt = findViewById(R.id.wlcmText);

        Animation imgAnim = AnimationUtils.loadAnimation(this,R.anim.img_rotate_anim);
        Animation imgScale = AnimationUtils.loadAnimation(this,R.anim.img_scale_anim);
        Animation txtAnimation = AnimationUtils.loadAnimation(this,R.anim.txt_translate_anim);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(imgAnim);
        animationSet.addAnimation(imgScale);

        txt.startAnimation(txtAnimation);
        img.startAnimation(animationSet);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}