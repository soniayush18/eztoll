package com.example.eztoll;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    DatabaseHelper mydb;
    EditText editName,editEmail,editPassword,editVehicle_Number,editMobile_Number,editDriving_license_Number;
    Button btnSignup;


/*
    public void toDashboardActivity(View view){
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(intent);
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mydb = new DatabaseHelper(this);
        //mydb.openDataBase();

        editName = (EditText)findViewById(R.id.editText_name);
        editEmail = (EditText)findViewById(R.id.editText_email);
        editPassword = (EditText)findViewById(R.id.editText_password);
        editVehicle_Number = (EditText)findViewById(R.id.editText_vehicle_number);
        editMobile_Number = (EditText)findViewById(R.id.editText_mobile_number);
        editDriving_license_Number = (EditText)findViewById(R.id.editText_driving_license_number);
        btnSignup = (Button)findViewById(R.id.signup);
        AddData();

        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);
    }

    public void AddData(){
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = mydb.insertData(editName.getText().toString(), editEmail.getText().toString()
                        , editPassword.getText().toString(), editVehicle_Number.getText().toString()
                        , editMobile_Number.getText().toString(), editDriving_license_Number.getText().toString());

                if (isInserted = true) {
                    Toast.makeText(RegisterActivity.this, "Sign-up Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }

                else {
                    Toast.makeText(RegisterActivity.this,"Data not Inserted",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
