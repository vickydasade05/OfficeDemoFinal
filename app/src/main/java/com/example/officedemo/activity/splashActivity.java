package com.example.officedemo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.officedemo.R;

public class splashActivity extends AppCompatActivity {
    private ProgressBar mProgress;
    private SharedPreferences sp;
    Bundle dataBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgress = findViewById(R.id.splash_screen_progress_bar);
        sp = PreferenceManager.getDefaultSharedPreferences(splashActivity.this);
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();

        
    }

    private void doWork() {
        for (int progress=0; progress<100; progress+=30) {
            try {
                Thread.sleep(1000);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private void startApp() {
              String loginType = sp.getString("isLoginType","");
        if (loginType.equalsIgnoreCase("manager")){
            Intent intent = new Intent(splashActivity.this, departmentActivity.class);
            dataBundle = new Bundle();
            dataBundle.putString("isFromDirectManager", "1");
            dataBundle.putString("isFromDirectEmployee", "0");
            intent.putExtras(dataBundle);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();
        }else if (loginType.equalsIgnoreCase("employee")){
            Intent intent = new Intent(splashActivity.this, showStatusActivity.class);
            dataBundle = new Bundle();
            dataBundle.putString("isFromDirectEmployee", "1");
            dataBundle.putString("isFromDirectManager", "0");
            intent.putExtras(dataBundle);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();
        }else {
            Intent intent = new Intent(splashActivity.this, loginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();
        }



    }
}
