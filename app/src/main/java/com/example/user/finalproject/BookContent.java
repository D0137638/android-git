package com.example.user.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookContent extends AppCompatActivity {

    DbHelper myDb;
    TextView tv_BookName , tv_BookPrice;
    ImageView Detail_pictute;
    Button btn_AddToCart , btn_AddToFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DbHelper(this);
        tv_BookName = (TextView)findViewById(R.id.tv_BookName);
        tv_BookPrice = (TextView)findViewById(R.id.tv_BookPrice);
        Detail_pictute = (ImageView)findViewById(R.id.Detail_pictute);
        btn_AddToCart = (Button)findViewById(R.id.btn_AddToCart);
        btn_AddToFavorite = (Button)findViewById(R.id.btn_AddToFavorite);

        Intent intent = getIntent();
        final long pos = intent.getExtras().getLong("ID");

        Cursor c= myDb.getBook(pos);
        String bookname = c.getString(1);
        int bookproce = c.getInt(2);
        int bookpicture = c.getInt(3);
        tv_BookName.setText(bookname);
        tv_BookPrice.setText(String.valueOf(bookproce));
        Detail_pictute.setImageResource(bookpicture);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //返回鍵
        if(id == R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
