package com.pharmeasy.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pharmeasy.R;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener {
    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private EditText mUserNameET;
    private EditText mPasswordET;
    private Button mLoginBTN;
    private TextView mNewSignUpTV;

    private void initViews() {
        mUserNameET = getView().findViewById(R.id.userNameET);
        mPasswordET = getView().findViewById(R.id.passwordET);
        mLoginBTN = getView().findViewById(R.id.loginBTN);
        mNewSignUpTV = getView().findViewById(R.id.newSignUpTV);

        mLoginBTN.setOnClickListener(this);
        mNewSignUpTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBTN:
                break;

            case R.id.newSignUpTV:
                onNewSignUpClicked();
                break;
        }
    }


    private void onNewSignUpClicked() {
        String userName = mUserNameET.getText().toString();
        String password = mPasswordET.getText().toString();

        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password))
        {

        }
    }

    private void signUp(String username, String password)
    {

    }
}