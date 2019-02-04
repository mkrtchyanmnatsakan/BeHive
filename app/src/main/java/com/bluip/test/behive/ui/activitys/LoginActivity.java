package com.bluip.test.behive.ui.activitys;

import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.Utils;
import com.bluip.test.behive.ui.fragments.LoginUserNameFragment;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeStatusBarColor();

        setContentView(R.layout.activity_login);



        initViews();

    }


    private void initViews(){

        TextView signUpText = findViewById(R.id.sign_up_text);
        signUpText.setPaintFlags(signUpText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView versionNameText = findViewById(R.id.version_name_text);
        String versionName = getResources().getString(R.string.version) + " " + Utils.getVersionAppName(this);
        versionNameText.setText(versionName);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);


    }


    private void changeStatusBarColor(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sign_in_button:

                goToLoginUserNameFragment();

                break;
        }

    }

    private void goToLoginUserNameFragment() {


        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_container,LoginUserNameFragment.newInstance(),
                ConstantValues.TAG_LOGIN_USER_NAME_FRAGMENT)
                .commit();

    }
}
