package com.bluip.test.behive.ui.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.Utils;

public class StartActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeStatusBarColor();
        setContentView(R.layout.activity_start);

        ImageView logoImage = findViewById(R.id.logo_image);
        startRotatingImage(logoImage);

    }

    private void changeStatusBarColor(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));

        }
    }


    private void startRotatingImage(ImageView imageView) {

        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_animation);
        imageView.startAnimation(startRotateAnimation);

        goToLoginActivity();

    }


    private void goToLoginActivity(){


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Utils.getBooleanInPreference(getApplicationContext(),ConstantValues.IS_LOGIN_USER_PREFERENCE_KEY)){

                    startActivity(new Intent(StartActivity.this,HomeActivity.class));

                }else {

                    startActivity(new Intent(StartActivity.this,LoginActivity.class));

                }



            }
        },2500);


    }
}
