package com.example.officedemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.officedemo.R;
import com.example.officedemo.util.PlayerConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class mobileRegistrationActivity extends YouTubeBaseActivity implements View
        .OnClickListener, RecognitionListener {


    private static final long ALERT_DISPLAY_LENGTH = 2000;
    private TextInputLayout getMobileWrapper;
    private TextInputEditText etMobile;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private TextView tv_videoExplainOtp;
    private String PINString;
    private LinearLayout ll_buttons_registrations;
    private LinearLayout ll_otp;
    private Button btn_skip_registration, btn_cancel_registration;
    private YouTubePlayerView youTubePlayerViewOtp;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer videoPlayerOtp;
    public static final int RequestPermissionCode = 1;
    private boolean fullScreen;
    private TextView tv_manual;
    ArrayAdapter<String> dataAdapter;
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private TextView tvTap;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private fr.ganfra.materialspinner.MaterialSpinner spinner_mobileNumber;
    private Bundle dataBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mobile_registration);

        init();

    }


    private void init() {
        youTubePlayerViewOtp =findViewById(R.id.youtube_player_otp);
        TextView tvGetOtp = findViewById(R.id.tvGetOtp);
        tvTap = findViewById(R.id.tvTap);
        getMobileWrapper = findViewById(R.id.getMobileWrapper);
        etMobile =  findViewById(R.id.etMobile);
        etMobile.setSelection(3);
        tvGetOtp.setOnClickListener(this);
        ll_otp = findViewById(R.id.ll_otp);
        tv_manual = findViewById(R.id.tv_manual);
        ll_buttons_registrations = findViewById(R.id.ll_buttons_registrations);
        sp = PreferenceManager.getDefaultSharedPreferences(mobileRegistrationActivity.this);
        tv_videoExplainOtp = findViewById(R.id.tv_videoExplainOtp);
        btn_cancel_registration = findViewById(R.id.btn_cancel_registration);
        btn_skip_registration = findViewById(R.id.btn_skip_registration);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {


            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {


                if (!wasRestored) {

                    videoPlayerOtp = player;
                    videoPlayerOtp.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {

                        @Override
                        public void onFullscreen(boolean _isFullScreen) {
                            fullScreen = _isFullScreen;
                        }
                    });
                    videoPlayerOtp.loadVideo("88gS_9xx0EE");


                }

                videoPlayerOtp.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
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


                        videoPlayerOtp.release();

                        Toast.makeText(mobileRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();
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

        tv_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMobileWrapper.setVisibility(View.VISIBLE);
                tv_manual.setVisibility(View.GONE);
                tvTap.setVisibility(View.GONE);
                spinner_mobileNumber.setVisibility(View.GONE);
                toggleButton.setVisibility(View.GONE);


            }
        });

        progressBar =findViewById(R.id.progressBar1);
        toggleButton = findViewById(R.id.toggleButton1);
        spinner_mobileNumber = findViewById(R.id.spinner_mobileNumber);
        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "hi-IN");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    ActivityCompat.requestPermissions
                            (mobileRegistrationActivity.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    REQUEST_RECORD_PERMISSION);
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speech.startListening(recognizerIntent);
                } else {
                    Toast.makeText(mobileRegistrationActivity.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        //Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
//        returnedText.setText(errorMessage);
        toggleButton.setChecked(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (matches != null) {
            dataAdapter = new ArrayAdapter<>(this, android.R.layout
                    .simple_spinner_item, matches);
        }
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mobileNumber.setVisibility(View.VISIBLE);

        initSpinnerHintAndFloatingLabel();



    }

    private void initSpinnerHintAndFloatingLabel() {
        spinner_mobileNumber = findViewById(R.id.spinner_mobileNumber);
        spinner_mobileNumber.setAdapter(dataAdapter);
        spinner_mobileNumber.setPaddingSafe(0, 0, 0, 0);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvGetOtp) {



            if (etMobile.getText().toString().equalsIgnoreCase(getString(R.string
                    .PREFS_MOBILE_NUMBER))) {
                if (!sp.getBoolean("is_manager", true)) {
                    Intent departmentIntent = new Intent(mobileRegistrationActivity.this, showStatusActivity
                            .class);
                    dataBundle= new Bundle();
                    dataBundle.putString("isFromDirectEmployee", "23");
                    dataBundle.putString("isFromDirectManager", "23");
                    departmentIntent.putExtras(dataBundle);
                    startActivity(departmentIntent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();

                } else {
                    Intent departmentIntent = new Intent(mobileRegistrationActivity.this, departmentActivity
                            .class);
                    dataBundle= new Bundle();
                    dataBundle.putString("isFromDirectEmployee", "23");
                    dataBundle.putString("isFromDirectManager", "23");
                    departmentIntent.putExtras(dataBundle);
                    startActivity(departmentIntent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }

            } else {

                generateOtp();


            }

        }
    }

    private void generateOtp() {

        //generate a 4 digit integer 1000 <10000
        int randomPIN = (int) (Math.random() * 9000) + 1000;

        //Store integer in a string
        PINString = String.valueOf(randomPIN);

        Log.e("otp generated==", PINString);
        editor = sp.edit();
        editor.putString(getString(R.string.PREFS_MOBILE_NUMBER), etMobile.getText().toString());
        editor.apply();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
        dialogBuilder.setView(dialogView);

        final EditText etOtpReceived =  dialogView.findViewById(R.id.etOtpReceived);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                etOtpReceived.setText(PINString);

                if (etOtpReceived.getText().toString().isEmpty()) {


                    Toast.makeText(mobileRegistrationActivity.this, "please generate otp first", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.dismiss();


                    if (!sp.getBoolean("is_manager", true)) {


                        Intent departmentIntent = new Intent(mobileRegistrationActivity.this, departmentActivity
                                .class);

                        dataBundle= new Bundle();
                        dataBundle.putString("isFromDirectEmployee", "23");
                        dataBundle.putString("isFromDirectManager", "23");
                        departmentIntent.putExtras(dataBundle);
                        editor = sp.edit();
                        editor.putBoolean(getString(R.string.PREF_ISLOGIN_EMPLOYEE), true);
                        editor.apply();
                        startActivity(departmentIntent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();

                    } else {


                        if (sp.getBoolean(getString(R.string.isFirstTimeShowVideo), true)) {
                            ll_otp.setVisibility(View.GONE);

                            youTubePlayerViewOtp.setVisibility(View.VISIBLE);
                            tv_videoExplainOtp.setVisibility(View.VISIBLE);
                            ll_buttons_registrations.setVisibility(View.VISIBLE);
                            youTubePlayerViewOtp.initialize(PlayerConfig.API_KEY, onInitializedListener);


                        } else {

                            Intent departmentIntent = new Intent(mobileRegistrationActivity.this, departmentActivity.class);
                            dataBundle= new Bundle();
                            dataBundle.putString("isFromDirectEmployee", "23");
                            dataBundle.putString("isFromDirectManager", "23");
                            departmentIntent.putExtras(dataBundle);
                            editor = sp.edit();
                            editor.putBoolean(getString(R.string.PREF_ISLOGIN_MANAGER), true);
                            editor.apply();
                            startActivity(departmentIntent);
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        }
                        btn_skip_registration.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //change boolean value


                                videoPlayerOtp.release();
                                ll_otp.setVisibility(View.VISIBLE);
                                youTubePlayerViewOtp.setVisibility(View.GONE);
                                ll_buttons_registrations.setVisibility(View.GONE);
                                tv_videoExplainOtp.setVisibility(View.GONE);

                                if (sp.getBoolean(getString(R.string.PREF_ISLOGIN_MANAGER), true)) {
                                    Toast.makeText(mobileRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                   finish();
                                   // overridePendingTransition(R.anim.left_in, R.anim.right_out);
                                } else {
                                    Intent deptIntent = new Intent(mobileRegistrationActivity.this,
                                            showStatusActivity.class);
                                    startActivity(deptIntent);
                                    finish();
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                }


                            }
                        });


                        btn_cancel_registration.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (videoPlayerOtp.isPlaying()) {
                                    videoPlayerOtp.release();
                                    ll_otp.setVisibility(View.VISIBLE);
                                    youTubePlayerViewOtp.setVisibility(View.GONE);
                                    ll_buttons_registrations.setVisibility(View.GONE);
                                    tv_videoExplainOtp.setVisibility(View.GONE);
                                }


                            }
                        });


                    }


                }


            }
        }, ALERT_DISPLAY_LENGTH);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestPermissionCode) {
            getYouTubePlayerProvider().initialize(PlayerConfig.API_KEY, (YouTubePlayer.OnInitializedListener) this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubePlayerViewOtp;
    }

    @Override
    public void onBackPressed() {


        if (fullScreen) {
            videoPlayerOtp.setFullscreen(false);
        } else {
            if (youTubePlayerViewOtp.getVisibility() == View.VISIBLE) {
                youTubePlayerViewOtp.setVisibility(View.GONE);
                tv_videoExplainOtp.setVisibility(View.GONE);
                ll_buttons_registrations.setVisibility(View.GONE);
                ll_otp.setVisibility(View.VISIBLE);
                if (videoPlayerOtp.isPlaying()) {
                    videoPlayerOtp.release();
                }
            } else {
                super.onBackPressed();
                     clearPreferences();
                youTubePlayerViewOtp.setVisibility(View.GONE);
                tv_videoExplainOtp.setVisibility(View.GONE);
                ll_buttons_registrations.setVisibility(View.GONE);
                ll_otp.setVisibility(View.VISIBLE);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                finish();

            }

        }


    }

    private void clearPreferences() {

        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
