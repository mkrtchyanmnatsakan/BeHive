package com.bluip.test.behive.ui.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.Utils;
import com.bluip.test.behive.helpers.listeners.OnKeyboardVisibilityListener;
import com.bluip.test.behive.ui.activitys.HomeActivity;

public class LoginPasswordFragment extends BaseLoginFragment implements View.OnTouchListener,View
                                                    .OnClickListener , OnKeyboardVisibilityListener
                                                    ,TextWatcher,  CompoundButton.OnCheckedChangeListener {


    private RelativeLayout goToHomeRelative;

    private EditText passwordEdit;
    private TextInputLayout passwordTextInput;
    private CheckBox checkBoxPassword;
    private boolean isClickedEye;
    private int     globalHeightDiff;




    public static LoginPasswordFragment newInstance(){

        LoginPasswordFragment loginPasswordFragment = new LoginPasswordFragment();
        Bundle bundle = new Bundle();
        loginPasswordFragment.setArguments(bundle);
        return loginPasswordFragment;

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_password_fragment,container,false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setKeyboardVisibilityListener(this,view);
        initViews(view);
    }

    private void initViews(View view) {

        passwordEdit      = view.findViewById(R.id.password_edit);
        passwordTextInput = view.findViewById(R.id.password_text_input);

        String savePassword = Utils.getStringInPreference(getActivity(),ConstantValues.
                PASSWORD_PREFERENCE_KEY);

        if(savePassword!= null && savePassword.length() > 0){

            passwordEdit.setText(savePassword);
        }

        TextView forgotText = view.findViewById(R.id.forgot_text);
        forgotText.setPaintFlags(forgotText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            passwordEdit.setOnTouchListener(this);
            passwordEdit.addTextChangedListener(this);

        goToHomeRelative = view.findViewById(R.id.go_to_home_relative);
        goToHomeRelative.setOnClickListener(this);

        RelativeLayout backPasswordRelative = view.findViewById(R.id.back_password_relative);
        backPasswordRelative.setOnClickListener(this);

        checkBoxPassword = view.findViewById(R.id.check_box_password);
        checkBoxPassword.setOnCheckedChangeListener(this);







    }






    private void setKeyboardVisibilityListener(final OnKeyboardVisibilityListener
                                                       onKeyboardVisibilityListener, final View view) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private boolean alreadyOpen;
            private final int defaultKeyboardHeightDP = 100;
            private final int EstimatedKeyboardDP = defaultKeyboardHeightDP + (Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect rect = new Rect();

            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.
                        COMPLEX_UNIT_DIP, EstimatedKeyboardDP, view.getResources().getDisplayMetrics());
                view.getWindowVisibleDisplayFrame(rect);
                int heightDiff = view.getRootView().getHeight() - (rect.bottom - rect.top);

                boolean isShown = heightDiff >= estimatedKeyboardHeight;


                if(isShown){

                    int diffGlobal = heightDiff - globalHeightDiff;
                    setMarginRelative(diffGlobal);


                }else {

                    globalHeightDiff = heightDiff;
                }


                if (isShown == alreadyOpen) {

                    return;
                }
                alreadyOpen = isShown;
                onKeyboardVisibilityListener.onVisibilityChanged(isShown);
            }
        });
    }





    @Override
    public boolean onTouch(View v, MotionEvent event) {


        final int DRAWABLE_RIGHT = 2;

        if(event.getAction() == MotionEvent.ACTION_UP) {

            if(event.getRawX() >= (passwordEdit.getRight() - passwordEdit.getCompoundDrawables()
                    [DRAWABLE_RIGHT].getBounds().width())) {

                clickedEye(isClickedEye);


                return true;
            }
        }



        return false;
    }

    private void clickedEye(boolean isClicked) {

        if(!isClicked){


            isClickedEye = true;

            passwordEdit.setTransformationMethod(null);

            passwordEdit.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null
                    ,getResources().getDrawable(R.drawable.ic_show_password),null);

        }else {

            passwordEdit.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null
                    ,getResources().getDrawable(R.drawable.ic_hide_password),null);
            passwordEdit.setTransformationMethod(new PasswordTransformationMethod());
            isClickedEye = false;

        }




    }





    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.back_password_relative:

                if(getActivity()!= null){

                    Utils.hideKeyboard(getActivity());

                    getActivity().onBackPressed();
                }

                break;

            case R.id.go_to_home_relative:

                Utils.hideKeyboard(getActivity());

                if(validateDate(passwordEdit.getText().toString().trim())){

                    if(getActivity() != null ){

                        Utils.settingBooleanValuesInPreference(getActivity(),ConstantValues.
                                IS_LOGIN_USER_PREFERENCE_KEY,true);
                        startActivity(new Intent(getActivity(),HomeActivity.class));
                        getActivity().finish();

                    }


                }else {

                    setErrorTextInput();

                }




                break;
        }

    }




    @Override
    public void onVisibilityChanged(boolean visible) {

        goToHomeRelative.setVisibility(visible ? View.VISIBLE : View.GONE);
        passwordEdit.setCursorVisible(visible);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        passwordTextInput.setErrorEnabled(false);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        if(validateDate(passwordEdit.getText().toString())){

            if(isChecked){

                Utils.settingStringValuesInPreference(getActivity(),ConstantValues.
                        PASSWORD_PREFERENCE_KEY,passwordEdit.getText().toString().trim());

            }else {

                Utils.settingStringValuesInPreference(getActivity(),ConstantValues.
                        PASSWORD_PREFERENCE_KEY,"");

            }


        }else {

            checkBoxPassword.setChecked(false);
            setErrorTextInput();

        }


    }

    @Override
    boolean validateDate(String validateDate) {
        return validateDate.length() > 0 && validateDate.equals(ConstantValues.PASSWORD);
    }

    @Override
    void setMarginRelative(int diff) {

        RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)goToHomeRelative.getLayoutParams();
        relativeParams.setMargins(0, 0, 0, diff);
        goToHomeRelative.setLayoutParams(relativeParams);

    }

    @Override
    void setErrorTextInput() {

                passwordEdit.setText("");
                passwordTextInput.setErrorEnabled(true);
                passwordTextInput.setError(getResources().getString(R.string.incorrect_password));

    }
}
