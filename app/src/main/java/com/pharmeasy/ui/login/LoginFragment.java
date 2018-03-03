package com.pharmeasy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pharmeasy.R;
import com.pharmeasy.ui.signup.SignUpActivity;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener{

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private EditText mUserNameET;
    private EditText mPasswordET;
    private Button mLoginBTN;
    private TextView mNewSignUpTV;

    private void initViews() {
        mUserNameET = (EditText)getView().findViewById( R.id.userNameET );
        mPasswordET = (EditText)getView().findViewById( R.id.passwordET );
        mLoginBTN = (Button)getView().findViewById( R.id.loginBTN );
        mNewSignUpTV = (TextView)getView().findViewById( R.id.newSignUpTV );

        mLoginBTN.setOnClickListener( this );
        mNewSignUpTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.loginBTN:
                onLoginClicked();
                break;

            case R.id.newSignUpTV:
                onNewSignUpClicked();
                break;
        }
    }

    private void onLoginClicked(){

    }

    private void onNewSignUpClicked(){
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        startActivity(intent);
    }
}
