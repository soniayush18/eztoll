package com.example.eztoll;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button button;
    CardView nfc_tap;
    CardView recharge_account;
    static FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener authListener;


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Intent intent = getIntent();
        //String result = intent.getStringExtra("MY_KEY");
        //System.out.print(result);
        //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        //String name_usr = getString(R.string.name_usr, result);

        //TextView t = (TextView) findViewById(R.id.nav_name);
        //t.setText(name_usr);

/*
        Button hamMenu = findViewById(R.id.button3);
        hamMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout navDrawer = findViewById(R.id.drawer_layout);
                // If navigation drawer is not open yet, open it else close it.
                if(!navDrawer.isDrawerOpen(GravityCompat.START)) navDrawer.openDrawer(Gravity.START);
                else navDrawer.closeDrawer(Gravity.END);
            }
        });
*/


        button = (Button) findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    //startActivity(new Intent(Main2Activity.this,MainActivity.class));
                }
            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.logout:
                        sendToLogin();
                        break;
                    // ...
                }
            }
        });

        nfc_tap = (CardView)findViewById(R.id.nfc);
        recharge_account = (CardView)findViewById(R.id.recharge);

        nfc_tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Main2Activity.this, "Logout Successfull", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), NfcActivity.class);
                startActivity(intent);
            }
        });

        //payment activity

        recharge_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Main2Activity.this, "Logout Successfull", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), payment.class);
                startActivity(intent);
            }
        });




        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void sendToLogin() {
        GoogleSignInClient mGoogleSignInClient ;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getBaseContext(), gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(Main2Activity.this,
                new OnCompleteListener<Void>() {  //signout Google
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut(); //signout firebase
                        Intent setupIntent = new Intent(getBaseContext(),MainActivity.class);
                        Toast.makeText(getBaseContext(), "Logout Successfull", Toast.LENGTH_LONG).show(); //if u want to show some text
                        setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupIntent);
                        finish();
                    }
                });
    }
}
