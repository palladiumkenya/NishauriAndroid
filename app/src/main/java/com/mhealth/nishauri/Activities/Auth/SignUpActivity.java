package com.mhealth.nishauri.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Tools;
import com.mhealth.nishauri.utils.ViewAnimation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private List<View> view_list = new ArrayList<>();
    private List<RelativeLayout> step_view_list = new ArrayList<>();
    private int success_step = 0;
    private int current_step = 0;
    private View parent_view;
    private Date date = null;
    private String time = null;
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
        view_list.add(findViewById(R.id.lyt_title));
        view_list.add(findViewById(R.id.lyt_description));
        view_list.add(findViewById(R.id.lyt_time));
        view_list.add(findViewById(R.id.lyt_date));
        view_list.add(findViewById(R.id.lyt_confirmation));

        // populate view step (circle in left)
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_title)));
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_description)));
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_time)));
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_date)));
        step_view_list.add(((RelativeLayout) findViewById(R.id.step_confirmation)));

        for (View v : view_list) {
            v.setVisibility(View.GONE);
        }

        view_list.get(0).setVisibility(View.VISIBLE);
        hideSoftKeyboard();

        ((TextView) findViewById(R.id.tv_time)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ((TextView) findViewById(R.id.tv_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void clickAction(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bt_continue_title:
                // validate input user here
                if (((EditText) findViewById(R.id.et_title)).getText().toString().trim().equals("")) {
                    Snackbar.make(parent_view, "Title cannot empty", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                collapseAndContinue(0);
                break;
            case R.id.bt_continue_description:
                // validate input user here
                if (((EditText) findViewById(R.id.et_description)).getText().toString().trim().equals("")) {
                    Snackbar.make(parent_view, "Description cannot empty", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                collapseAndContinue(1);
                break;
            case R.id.bt_continue_time:
                // validate input user here
                if (time == null) {
                    Snackbar.make(parent_view, "Please set event time", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                collapseAndContinue(2);
                break;
            case R.id.bt_continue_date:
                // validate input user here
                if (date == null) {
                    Snackbar.make(parent_view, "Please set event date", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                collapseAndContinue(3);
                break;
            case R.id.bt_add_event:
                // validate input user here
                finish();
                break;
        }
    }

    public void clickLabel(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_label_title:
                if (success_step >= 0 && current_step != 0) {
                    current_step = 0;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(0));
                }
                break;
            case R.id.tv_label_description:
                if (success_step >= 1 && current_step != 1) {
                    current_step = 1;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(1));
                }
                break;
            case R.id.tv_label_time:
                if (success_step >= 2 && current_step != 2) {
                    current_step = 2;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(2));
                }
                break;
            case R.id.tv_label_date:
                if (success_step >= 3 && current_step != 3) {
                    current_step = 3;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(3));
                }
                break;
            case R.id.tv_label_confirmation:
                if (success_step >= 4 && current_step != 4) {
                    current_step = 4;
                    collapseAll();
                    ViewAnimation.expand(view_list.get(4));
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
