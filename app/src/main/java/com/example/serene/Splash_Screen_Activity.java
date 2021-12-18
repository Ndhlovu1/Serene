package com.example.serene;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serene.users.LoginActivity;

public class Splash_Screen_Activity extends AppCompatActivity {

    //Animation Variables
    Animation topAnim, bottomAnim;
    ImageView img_logo;
    TextView name_logo, catch_phrase;

    private static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Set Animations on Image and Text Views
        img_logo = findViewById(R.id.img_logo);
        name_logo = findViewById(R.id.app_name);
        catch_phrase = findViewById(R.id.catch_phrase);

        img_logo.setAnimation(topAnim);
        name_logo.setAnimation(bottomAnim);
        catch_phrase.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen_Activity.this, LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(img_logo,"logo_img");
                pairs[1] = new Pair<View, String>(name_logo,"app_name_logo");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash_Screen_Activity.this, pairs);
                startActivity(intent, options.toBundle());

                 // startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);


    }
}