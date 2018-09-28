package com.example.officedemo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.officedemo.R;
import com.example.officedemo.util.PlayerConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class departmentActivity extends YouTubeBaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    private TextView tb_title;
    YouTubePlayerView youTubePlayerViewDepartment;
    private TextView tv_videoExplainDepartment, tvTitleHeader;
    private Button btnDept1, btnDept2, btnDept3, btnDept4;
    private SharedPreferences sp;
    private Button btn_skip, btn_cancel_department;
    private SharedPreferences.Editor editor;
    private LinearLayout ll_department, ll_buttons;
    YouTubePlayer videoPlayerDepartment;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private boolean fullScreen;
    String isFromDirectEmployee,isFromDirectManager;
    public final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        toolbar = (Toolbar) findViewById(R.id.tbDepartment);
        tb_title = toolbar.findViewById(R.id.tb_title);
        tb_title.setText("Departments");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();// Do what do you want on toolbar button
            }
        });
        youTubePlayerViewDepartment = findViewById(R.id.youtube_player_department);
        tv_videoExplainDepartment = findViewById(R.id.tv_videoExplainDepartment);
        ll_department = findViewById(R.id.ll_department);
        btnDept1 = findViewById(R.id.btnDept1);
        btnDept2 = findViewById(R.id.btnDept2);
        btnDept3 = findViewById(R.id.btnDept3);
        btnDept4 = findViewById(R.id.btnDept4);
        ll_buttons = findViewById(R.id.ll_buttons);
        btn_skip = findViewById(R.id.btn_skip);
        btn_cancel_department = findViewById(R.id.btn_cancel_department);

        tvTitleHeader = toolbar.findViewById(R.id.tb_title);
        tvTitleHeader.setText("Department");

        sp = PreferenceManager.getDefaultSharedPreferences(departmentActivity.this);
        btnDept1.setOnClickListener(this);
        btnDept2.setOnClickListener(this);
        btnDept3.setOnClickListener(this);
        btnDept4.setOnClickListener(this);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {


                if (!wasRestored) {

                    videoPlayerDepartment = player;
                    videoPlayerDepartment.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {

                        @Override
                        public void onFullscreen(boolean _isFullScreen) {
                            fullScreen = _isFullScreen;
                        }
                    });
                    videoPlayerDepartment.loadVideo("88gS_9xx0EE");

                }
                videoPlayerDepartment.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onAdStarted() {
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason arg0) {
                    }

                    @Override
                    public void onLoaded(String arg0) {
                    }

                    @Override
                    public void onLoading() {
                    }

                    @Override
                    public void onVideoEnded() {

                        if (!sp.getBoolean("is_manager", true)) {
                            Intent splashIntent = new Intent(departmentActivity.this, showStatusActivity.class);
                            editor = sp.edit();
                            editor.putBoolean(getString(R.string.isFirstTimeShowVideoDepartmentEmployee),
                                    true);
                            editor.apply();
                            startActivity(splashIntent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }else {
                            Intent nearByIntent = new Intent(departmentActivity.this, nearByEmployees
                                    .class);
                            editor = sp.edit();
                            editor.putBoolean(getString(R.string.isFirstTimeShowVideoDepartment),
                                    true);
                            editor.apply();
                            startActivity(nearByIntent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }

                    }

                    @Override
                    public void onVideoStarted() {
                    }
                });

                btn_skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //change boolean value

                        if (!sp.getBoolean("is_manager", true)) {
                            videoPlayerDepartment.release();

                            Intent splashIntent = new Intent(departmentActivity.this, showStatusActivity.class);
                            editor = sp.edit();
                            editor.putBoolean(getString(R.string.isFirstTimeShowVideoDepartmentEmployee),
                                    true);
                            editor.apply();
                            startActivity(splashIntent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }else {
                            videoPlayerDepartment.release();

                            Intent splashIntent = new Intent(departmentActivity.this, nearByEmployees.class);
                            editor = sp.edit();
                            editor.putBoolean(getString(R.string.isFirstTimeShowVideoDepartment),
                                    true);
                            editor.apply();
                            startActivity(splashIntent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }



                    }
                });


                btn_cancel_department.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        videoPlayerDepartment.release();
                        ll_department.setVisibility(View.VISIBLE);
                        youTubePlayerViewDepartment.setVisibility(View.GONE);
                        // btn_cancel.setVisibility(View.GONE);
                        ll_buttons.setVisibility(View.GONE);
                        tv_videoExplainDepartment.setVisibility(View.GONE);

                    }
                });

            }


            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        };


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        if (fullScreen) {
            videoPlayerDepartment.setFullscreen(false);
        } else {
            if (youTubePlayerViewDepartment.getVisibility() == View.VISIBLE) {
                youTubePlayerViewDepartment.setVisibility(View.GONE);
                tv_videoExplainDepartment.setVisibility(View.GONE);
                ll_buttons.setVisibility(View.GONE);
                ll_department.setVisibility(View.VISIBLE);
                if (videoPlayerDepartment.isPlaying()) {
                    videoPlayerDepartment.release();
                }
            } else {
                super.onBackPressed();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();

            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestPermissionCode) {

            getYouTubePlayerProvider().initialize(PlayerConfig.API_KEY, (YouTubePlayer.OnInitializedListener) this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerViewDepartment;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnDept1:

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();

                isFromDirectEmployee     = bundle.getString("isFromDirectEmployee");
                isFromDirectManager =  bundle.getString("isFromDirectManager");

                if( isFromDirectEmployee.equalsIgnoreCase("2")){
                    Intent departmentIntent = new Intent(departmentActivity.this, showStatusActivity
                            .class);

                    startActivity(departmentIntent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();

                }else if (isFromDirectManager.equalsIgnoreCase("1")){

                    Intent nearByIntent = new Intent(departmentActivity.this, nearByEmployees
                            .class);
                    startActivity(nearByIntent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }else {
                    if (!sp.getBoolean("is_manager", true)) {


                        if (sp.getBoolean(getString(R.string.isFirstTimeShowVideoDepartmentEmployee),
                                false)) {
                            Intent departmentIntent = new Intent(departmentActivity.this, showStatusActivity
                                    .class);

                            startActivity(departmentIntent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }else {
                            ll_department.setVisibility(View.GONE);
                            youTubePlayerViewDepartment.setVisibility(View.VISIBLE);
                            tv_videoExplainDepartment.setVisibility(View.VISIBLE);
                            ll_buttons.setVisibility(View.VISIBLE);
                            youTubePlayerViewDepartment.initialize(PlayerConfig.API_KEY, onInitializedListener);
                            editor = sp.edit();
                            editor.putBoolean(getString(R.string.isFirstTimeShowVideoDepartmentEmployee),
                                    true);
                            editor.apply();
                        }

                    } else {

                        if (sp.getBoolean(getString(R.string.isFirstTimeShowVideoDepartment), false)) {

                            Intent nearByIntent = new Intent(departmentActivity.this, nearByEmployees
                                    .class);

                            youTubePlayerViewDepartment.setVisibility(View.GONE);
                            tv_videoExplainDepartment.setVisibility(View.GONE);
                            ll_buttons.setVisibility(View.GONE);
                            ll_department.setVisibility(View.VISIBLE);
                            startActivity(nearByIntent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                             //finish();

                        } else {
                            ll_department.setVisibility(View.GONE);
                            youTubePlayerViewDepartment.setVisibility(View.VISIBLE);
                            tv_videoExplainDepartment.setVisibility(View.VISIBLE);
                            ll_buttons.setVisibility(View.VISIBLE);
                            youTubePlayerViewDepartment.initialize(PlayerConfig.API_KEY, onInitializedListener);
                            editor = sp.edit();
                            editor.putBoolean(getString(R.string.isFirstTimeShowVideoDepartment),
                                    true);
                            editor.apply();

                        }

                    }

                }


                break;

            case R.id.btnDept2:
                Toast.makeText(this, "Under developement", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnDept3:
                Toast.makeText(this, "Under developement", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btnDept4:
                Toast.makeText(this, "Under developement", Toast.LENGTH_SHORT).show();

                break;


        }
    }


}
