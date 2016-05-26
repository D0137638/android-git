package com.example.user.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton, imageButton2, imageButton3,imageButton4;
    ImageButton btnLogout;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        session = new Session(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_navgation_drawer);

        drawerFragment.setUp(R.id.fragment_navgation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);



        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RecommendActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FavoriteActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShopcartActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btnLogout = (ImageButton)findViewById(R.id.btn_Logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        if(session.logged() == true){
            btnLogout.setVisibility(View.VISIBLE);  //登出鍵顯示
        }
    }

    private void logout() {
        session.setLogged(false);
        btnLogout.setVisibility(View.INVISIBLE);
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }
}
