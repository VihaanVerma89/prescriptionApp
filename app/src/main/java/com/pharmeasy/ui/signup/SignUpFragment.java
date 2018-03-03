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
import android.widget.Toast;

import com.pharmeasy.R;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.MainActivity;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener, SignUpContract.View {
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
        initViews();
    }

    private EditText mUserNameET;
    private EditText mPasswordET;
    private Button mSignUpBTN;

    private void initViews() {
        mUserNameET = getView().findViewById(R.id.userNameET);
        mPasswordET = getView().findViewById(R.id.passwordET);
        mSignUpBTN = getView().findViewById(R.id.signUpBTN);
        mSignUpBTN.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBTN:
                break;

            case R.id.signUpBTN:
                onSignUpClicked();
                break;
        }
    }


    private void onSignUpClicked() {
        String userName = mUserNameET.getText().toString();
        String password = mPasswordET.getText().toString();

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            mPresenter.signUp(userName, password);
        } else {
            Toast.makeText(getActivity(), "Please enter Username & Password", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private SignUpContract.Presenter mPresenter;

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setUser(User user) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void signUpException(Throwable exception) {
        String message = exception.getMessage();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}