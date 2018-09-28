package com.example.officedemo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.officedemo.R;
import com.example.officedemo.util.PlayerConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class loginActivity extends YouTubeBaseActivity implements View.OnClickListener {
    private SharedPreferences sp;
    private Button btn_cancel;
    private TextView tvHelp, tv_videoExplain, tvExit;
    private LinearLayout llLogin;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer videoPlayer;
    private static final int RECOVERY_REQUEST = 1;
    private boolean fullScreen;
    Bundle dataBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = PreferenceManager.getDefaultSharedPreferences(loginActivity.this);

        youTubePlayerView = findViewById(R.id.youtube_player);
        Button btnEmployee = findViewById(R.id.btnEmployee);
        Button btnManager = findViewById(R.id.btnManager);
        btn_cancel = findViewById(R.id.btn_cancel);
        tvExit = findViewById(R.id.tvExit);
        tvHelp = findViewById(R.id.tvHelp);
        llLogin = findViewById(R.id.llLogin);
        btnManager.setOnClickListener(this);
        btnEmployee.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvExit.setOnClickListener(this);
        tv_videoExplain = findViewById(R.id.tv_videoExplain);


        onInitializedListener = new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {


                if (!wasRestored) {
                    videoPlayer = player;
                    if (videoPlayer != null) {
                        videoPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        videoPlayer.loadVideo("88gS_9xx0EE");
                        videoPlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {

                            @Override
                            public void onFullscreen(boolean _isFullScreen) {
                                fullScreen = _isFullScreen;
                            }
                        });

                        videoPlayer.play();
                    }


                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            videoPlayer.release();
                            llLogin.setVisibility(View.VISIBLE);
                            youTubePlayerView.setVisibility(View.GONE);
                            btn_cancel.setVisibility(View.GONE);
                            tvHelp.setVisibility(View.VISIBLE);
                            tvExit.setVisibility(View.VISIBLE);
                            tv_videoExplain.setVisibility(View.GONE);
                            //finish();
                        }
                    });

                }


                videoPlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
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

                        llLogin.setVisibility(View.VISIBLE);
                        youTubePlayerView.setVisibility(View.GONE);
                        btn_cancel.setVisibility(View.GONE);
                        tvExit.setVisibility(View.VISIBLE);
                        tvHelp.setVisibility(View.VISIBLE);
                        tv_videoExplain.setVisibility(View.GONE);
                        videoPlayer.release();


                    }

                    @Override
                    public void onVideoStarted() {
                    }
                });


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }


        };


    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor;
        switch (view.getId()) {

            case R.id.btnEmployee:


                if (sp.getBoolean(getString(R.string.PREF_ISLOGIN_EMPLOYEE), false)) {

                    dataBundle = new Bundle();
                    dataBundle.putString("isFromDirectEmployee", "2");
                    dataBundle.putString("isFromDirectManager", "0");

                    Intent departmentIntent = new Intent(loginActivity.this, showStatusActivity.class);
                    departmentIntent.putExtras(dataBundle);

                    startActivity(departmentIntent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                } else {
                    Intent departmentIntentEmployee = new Intent(loginActivity.this, mobileRegistrationActivity.class);
                    editor = sp.edit();
                    editor.putBoolean("is_manager", false);
                    editor.putString("isLoginType","employee");
                    editor.putBoolean(getString(R.string.PREF_ISLOGIN_EMPLOYEE), true);
                    editor.apply();
                    startActivity(departmentIntentEmployee);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }


                break;


            case R.id.btnManager:

                if (sp.getBoolean(getString(R.string.PREF_ISLOGIN_MANAGER), false)) {
                    Intent departmentIntent = new Intent(loginActivity.this, departmentActivity.class);

                    dataBundle = new Bundle();
                    dataBundle.putString("isFromDirectManager", "1");
                    dataBundle.putString("isFromDirectEmployee", "0");
                    departmentIntent.putExtras(dataBundle);
                    startActivity(departmentIntent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);


                } else {
                    Intent departmentIntent = new Intent(loginActivity.this, mobileRegistrationActivity.class);
                    editor = sp.edit();
                    editor.putBoolean("is_manager", true);
                    editor.putString("isLoginType","manager");
                    editor.putBoolean(getString(R.string.PREF_ISLOGIN_MANAGER), true);
                    editor.apply();

                    startActivity(departmentIntent);
                    finish();
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }


                break;

            case R.id.tvHelp:

                tvHelp.setVisibility(View.GONE);
                tvExit.setVisibility(View.GONE);
                llLogin.setVisibility(View.GONE);
                youTubePlayerView.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                tv_videoExplain.setVisibility(View.VISIBLE);
                youTubePlayerView.initialize(PlayerConfig.API_KEY, onInitializedListener);

                break;


            case R.id.tvExit:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    super.finishAndRemoveTask();
                } else {
                    super.finish();
                }

                break;

        }


    }


    @Override
    public void onBackPressed() {

        if (fullScreen) {

            videoPlayer.setFullscreen(false);


        } else {
            if (youTubePlayerView.getVisibility() == View.VISIBLE) {
                youTubePlayerView.setVisibility(View.GONE);
                tv_videoExplain.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                llLogin.setVisibility(View.VISIBLE);
                tvHelp.setVisibility(View.VISIBLE);
                tvExit.setVisibility(View.VISIBLE);
                if (videoPlayer.isPlaying()) {
                    videoPlayer.release();
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
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(PlayerConfig.API_KEY, (YouTubePlayer.OnInitializedListener) this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }


}
