package com.example.officedemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.officedemo.R;
import com.example.officedemo.adapter.CustomAdapter2;
import com.example.officedemo.clickListeners.RecyclerTouchListener;
import com.example.officedemo.interfaces.ClickListener;
import java.util.ArrayList;
import java.util.Arrays;

public class nearByEmployees extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static ArrayList<Integer> removedItems;
    Toolbar toolbar;
   private RecyclerView recyclerView;

    ArrayList personNames = new ArrayList<>(
            Arrays.asList("Person 1",
                    "Person 2",
                    "Person 3",
                    "Person 4",
                    "Person 5",
                    "Person 6",
                    "Person 7",
                    "Person 8",
                    "Person 9",
                    "Person 10",
                    "Person 11",
                    "Person 12",
                    "Person 13",
                    "Person 14"));
    ArrayList personImages = new ArrayList<>(
            Arrays.asList(R.drawable.person1,
                    R.drawable.person2,
                    R.drawable.person3,
                    R.drawable.person4,
                    R.drawable.person5,
                    R.drawable.person6,
                    R.drawable.person7,
                    R.drawable.person1,
                    R.drawable.person2,
                    R.drawable.person3,
                    R.drawable.person4,
                    R.drawable.person5,
                    R.drawable.person6,
                    R.drawable.person7));

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_employees);
        toolbar =  findViewById(R.id.tb_near_by);
        TextView tb_title = toolbar.findViewById(R.id.tb_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tb_title.setText("Near By Employees");

        recyclerView =  findViewById(R.id.my_recycler_view);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter2 customAdapter = new CustomAdapter2(nearByEmployees.this, personNames,
                personImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        clickEventes();


      }

    private void clickEventes() {

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new
                ClickListener() {
            @Override
            public void onClick(View view, int position) {
                onCall();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:9890915152")));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public void onBackPressed() {

        super.onBackPressed();

        overridePendingTransition(R.anim.left_in, R.anim.right_out);
       // finish();

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