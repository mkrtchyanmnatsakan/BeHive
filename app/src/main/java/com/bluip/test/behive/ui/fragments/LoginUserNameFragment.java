package com.bluip.test.behive.ui.fragments;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.Utils;
import com.bluip.test.behive.helpers.listeners.OnKeyboardVisibilityListener;

public class LoginUserNameFragment extends BaseLoginFragment implements OnKeyboardVisibilityListener ,
        View.OnClickListener,TextWatcher {

    private RelativeLayout  goToRelative;
    private EditText        userNameEdit;
    private TextInputLayout userNameTextInput;

    private int     globalHeightDiff;


    public static LoginUserNameFragment newInstance(){

        LoginUserNameFragment loginUserNameFragment = new LoginUserNameFragment();
        Bundle bundle = new Bundle();
        loginUserNameFragment.setArguments(bundle);
        return loginUserNameFragment;

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.login_user_name_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setKeyboardVisibilityListener(this,view);
        initViews(view);

    }

    private void initViews(View view) {

        goToRelative = view.findViewById(R.id.go_to_relative);
        goToRelative.setOnClickListener(this);

        RelativeLayout backUserNameRelative = view.findViewById(R.id.back_user_name_relative);
        backUserNameRelative.setOnClickListener(this);

        userNameEdit = view.findViewById(R.id.user_name_edit);
        userNameEdit.addTextChangedListener(this);

        userNameTextInput = view.findViewById(R.id.user_name_text_input);


    }



    /** calculated virtual keyboard height
     used this link
     https://stackoverflow.com/questions/4312319/how-to-capture-the-virtual-keyboard-show-hide-event-in-android */

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
    public void onVisibilityChanged(boolean visible) {

        goToRelative.setVisibility(visible ? View.VISIBLE : View.GONE);
        userNameEdit.setCursorVisible(visible);
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back_user_name_relative:

                if(getActivity()!= null){

                    Utils.hideKeyboard(getActivity());

                    getActivity().onBackPressed();
                }

                break;

            case R.id.go_to_relative:

                Utils.hideKeyboard(getActivity());

                if(validateDate(userNameEdit.getText().toString().trim())){

                    goToLoginPassword();


                } else {

                    setErrorTextInput();


                }

                break;


        }

    }



    private void goToLoginPassword(){

        if(getActivity() != null){

            getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragment_container,LoginPasswordFragment.newInstance(),
                            ConstantValues.TAG_LOGIN_PASSWORD_FRAGMENT)

                    .commit();

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        userNameTextInput.setErrorEnabled(false);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    boolean validateDate(String validateDate) {
         return validateDate.length() > 0 && validateDate.equals(ConstantValues.USER_NAME) ;
    }

    @Override
    void setMarginRelative(int diff) {

        RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams)goToRelative.getLayoutParams();
        relativeParams.setMargins(0, 0, 0, diff);
        goToRelative.setLayoutParams(relativeParams);

    }

    @Override
    void setErrorTextInput() {

        userNameEdit.setText("");

        userNameTextInput.setErrorEnabled(true);

        userNameTextInput.setError(getResources().getString(R.string.incorrect_username));


    }
}
