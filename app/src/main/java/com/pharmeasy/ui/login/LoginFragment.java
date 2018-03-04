package com.pharmeasy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pharmeasy.R;
import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.MainActivity;
import com.pharmeasy.ui.signup.SignUpActivity;
import com.pharmeasy.utils.SessionUtil;

import org.parceler.Parcels;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener , LoginContract.View{

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
        initUser();
        initViews();
    }

    private void initUser(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            User user = SessionUtil.getLoggedInUser(getActivity());
            Doctor doctor = SessionUtil.getLoggedInDoctor(getActivity());
            Bundle bundle = new Bundle();
            if(user!=null)
            {
                bundle.putParcelable(MainActivity.EXTRA_USER, Parcels.wrap(user));
                startMainActivity(bundle);
            }
            else if(doctor!=null)
            {
                bundle.putParcelable(MainActivity.EXTRA_DOCTOR, Parcels.wrap(doctor));
                startMainActivity(bundle);
            }

            getActivity().finish();
        }
    }

    private EditText mUserNameET;
    private EditText mPasswordET;
    private Button mLoginBTN;
    private TextView mNewSignUpTV;

    private void initViews() {
        mUserNameET = getView().findViewById( R.id.userNameET );
        mPasswordET = getView().findViewById( R.id.passwordET );
        mLoginBTN = getView().findViewById( R.id.loginBTN );
        mNewSignUpTV = getView().findViewById( R.id.newSignUpTV );

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

        String username = mUserNameET.getText().toString();
        String password = mPasswordET.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))
        {
            mPresenter.login(username, password);
        }
    }

    private void onNewSignUpClicked(){
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        startActivity(intent);
    }



    @Override
    public void setUser(User user) {
        SessionUtil.saveLoggedInUser(getActivity(), user);
        SessionUtil.clearLoggedInDoctor(getActivity());
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.EXTRA_USER, Parcels.wrap(user));
        startMainActivity(bundle);
    }

    @Override
    public void setDoctor(Doctor doctor) {
        SessionUtil.saveLoggedInDoctor(getActivity(), doctor);
        SessionUtil.clearLoggedInUser(getActivity());
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.EXTRA_DOCTOR, Parcels.wrap(doctor));
        startMainActivity(bundle);
    }

    private void startMainActivity(Bundle bundle)
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    public void loginException(Throwable exception) {
        String message = exception.getMessage();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private LoginContract.Presenter mPresenter;
    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
