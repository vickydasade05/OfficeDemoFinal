package com.example.officedemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.officedemo.R;

public class showStatusActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAvailable, btnAway;
    Boolean isClickedAvailable, isClickedAway;
    private Toolbar tb_status;
    private TextView tv_title,tvAvailable,tvUnavailable;
    private Switch switchAvailable,switchUnavailable;
    String switchOn = "Available";
    String switchOff = "Unavailable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status);

        btnAvailable = findViewById(R.id.btnAvailable);
        btnAway = findViewById(R.id.btnAway);
        tb_status = findViewById(R.id.tb_status);
        tv_title = tb_status.findViewById(R.id.tb_title);
        tv_title.setText("Status");

    //    tb_status.setNavigationIcon(andR.drawable.ic_arrow_back_icon);// Toolbar icon in Drawable
        // folder

        setSupportActionBar(tb_status);

        tb_status.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();// Do what do you want on toolbar button
            }
        });

        tvAvailable = findViewById(R.id.tvAvailable);
        tvUnavailable = findViewById(R.id.tvUnavailable);

        btnAway.setOnClickListener(this);
        btnAvailable.setOnClickListener(this);
        isClickedAvailable = true;
        isClickedAway = true;
        switchAvailable = findViewById(R.id.switchAvailable);
        switchUnavailable = findViewById(R.id.switchUnavailable);




        switchAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    tvAvailable.setText(switchOn);
                    switchUnavailable.setChecked(false);
                } else {

                }
            }
        });

        if (switchAvailable.isChecked()) {

            tvAvailable.setText(switchOn);
            switchUnavailable.setChecked(false);
        } else {
        }


        switchUnavailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {

                    tvUnavailable.setText(switchOff);
                    switchAvailable.setChecked(false);
                } else {

                }
            }
        });

        if (switchUnavailable.isChecked()) {
            switchAvailable.setChecked(false);

            tvUnavailable.setText(switchOff);
        } else {

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAvailable:

                Toast.makeText(this, " Available Now", Toast.LENGTH_SHORT).show();


                if (isClickedAvailable) {
                    v.setBackgroundDrawable(getResources().getDrawable(R.drawable
                            .circle_shape_status));
                    btnAway.setBackgroundDrawable(getResources().getDrawable(R.drawable
                            .circle_shape));

                    isClickedAvailable = false;
                    isClickedAway = true;
                }


                break;


            case R.id.btnAway:


                Toast.makeText(this, "Away Sorry Not Available!!!", Toast.LENGTH_SHORT).show();
                if (isClickedAway) {
                    v.setBackgroundDrawable(getResources().getDrawable(R.drawable
                            .circle_shape_status));
                    btnAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable
                            .circle_shape));

                    isClickedAway = false;
                    isClickedAvailable = true;

                }
                break;


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();


    }

}
