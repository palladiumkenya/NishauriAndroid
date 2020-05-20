package com.mhealth.nishauri.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.ViewAnimation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private List<View> view_list = new ArrayList<>();
    private List<RelativeLayout> step_view_list = new ArrayList<>();
    private int success_step = 0;
    private int current_step = 0;
    private View parent_view;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();


    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");

    }

    private void initComponent() {
        // populate layout field
        view_list.add(findViewById(R.id.lyt_ccc_no));
        view_list.add(findViewById(R.id.lyt_sequrity_question));
        view_list.add(findViewById(R.id.lyt_password));
        view_list.add(findViewById(R.id.lyt_confirmation));

        // populate view step (circle in left)
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_ccc_no)));
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_security_question)));
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_password)));
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_confirmation)));

        for (View v : view_list) {
            v.setVisibility(View.GONE);
        }

        view_list.get(0).setVisibility(View.VISIBLE);
        hideSoftKeyboard();



    }

    public void clickAction(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_continue_ccc_no:
                // validate input user here
                if (((EditText) findViewById(R.id.txt_ccc_no)).getText().toString().trim().equals("") ) {
                    Snackbar.make(parent_view, "Please provide your CCC Number.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (((EditText) findViewById(R.id.txt_phone_no)).getText().toString().trim().equals("") ) {
                    Snackbar.make(parent_view, "Please provide your Phone Number.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                collapseAndContinue(0);
                break;
            case R.id.bt_continue_security_question:
                // validate input user here
                if (((EditText) findViewById(R.id.txt_security_answer)).getText().toString().trim().equals("")) {
                    Snackbar.make(parent_view, "Please provide an answer to you question.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                collapseAndContinue(1);
                break;
            case R.id.bt_continue_password:
                // validate input user here
                if (((EditText) findViewById(R.id.txt_password)).getText().toString().trim().equals("")) {
                    Snackbar.make(parent_view, "Please provide a password.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (((EditText) findViewById(R.id.txt_repassword)).getText().toString().trim().equals("")) {
                    Snackbar.make(parent_view, "Please provide the password.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                collapseAndContinue(2);
                break;
            case R.id.bt_sign_up:
                // validate input user here
                if (((CheckBox) findViewById(R.id.terms)).isChecked()){
                    Snackbar.make(parent_view, "You have successfully Signed up.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }else {
                    Snackbar.make(parent_view, "Please confirm consent to NiShauri.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

    public void clickLabel(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_ccc:
                if (success_step >= 0 && current_step != 0) {
                    current_step = 0;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(0));
                }
                break;
            case R.id.txt_security:
                if (success_step >= 1 && current_step != 1) {
                    current_step = 1;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(1));
                }
                break;
            case R.id.txt_pass:
                if (success_step >= 2 && current_step != 2) {
                    current_step = 2;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(2));
                }
                break;
            case R.id.txt_confirmation:
                if (success_step >= 3 && current_step != 3) {
                    current_step = 3;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(3));
                }
                break;
        }
    }

    private void collapseAndContinue(int index) {
        ViewAnimation.collapse(view_list.get(index));
        setCheckedStep(index);
        index++;
        current_step = index;
        success_step = index > success_step ? index : success_step;
        ViewAnimation.expand(view_list.get(index));
    }

    private void collapseAll() {
        for (View v : view_list) {
            ViewAnimation.collapse(v);
        }
    }

    private void setCheckedStep(int index) {
        RelativeLayout relative = step_view_list.get(index);
        relative.removeAllViews();
        ImageButton img = new ImageButton(this);
        img.setImageResource(R.drawable.ic_done);
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        relative.addView(img);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


}
