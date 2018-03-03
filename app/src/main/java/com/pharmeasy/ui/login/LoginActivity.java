package com.pharmeasy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pharmeasy.R;
import com.pharmeasy.ui.signup.SignUpActivity;
import com.pharmeasy.utils.ActivityUtils;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        ActivityUtils.showFragment(getSupportFragmentManager(), R.id.frameLayout, LoginFragment.newInstance());
    }
}
