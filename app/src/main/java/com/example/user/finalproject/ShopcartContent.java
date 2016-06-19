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
import android.widget.Toast;

public class ShopcartContent extends AppCompatActivity {

    DbHelper myDb;
    TextView tv_BookName , tv_BookPrice;
    ImageView Detail_pictute;
    Button btn_AddToCart , btn_AddToFavorite , btn_Buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcart_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DbHelper(this);
        tv_BookName = (TextView)findViewById(R.id.tv_BookName);
        tv_BookPrice = (TextView)findViewById(R.id.tv_BookPrice);
        Detail_pictute = (ImageView)findViewById(R.id.Detail_pictute);
        btn_AddToFavorite = (Button)findViewById(R.id.btn_AddToFavorite);
        btn_AddToCart = (Button)findViewById(R.id.btn_AddToCart);
        btn_Buy = (Button)findViewById(R.id.btn_Buy);

        Intent intent = getIntent();
        final long pos = intent.getExtras().getLong("ID");

        Cursor c= myDb.getShopcart(pos);
        final int id = c.getInt(0);
        final String bookname = c.getString(1);
        int bookprice = c.getInt(2);
        int bookpicture = c.getInt(3);
        tv_BookName.setText(bookname);
        tv_BookPrice.setText(String.valueOf(bookprice));
        Detail_pictute.setImageResource(bookpicture);


        btn_AddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDb.getBook(Long.valueOf(id));
                Cursor c = myDb.searchFavorite(bookname);
                if( c.getCount() == 0 ) {
                    myDb.addFavorite(cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                }
                else
                {
                    Toast.makeText(ShopcartContent.this, "此商品已加入最愛", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(ShopcartContent.this, BookContent.class));
                }
                finish();
                startActivity(new Intent(ShopcartContent.this, FavoriteActivity.class));
            }
        });

        btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDb.getBook(Long.valueOf(id));
                myDb.addShopcart(cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                finish();
                startActivity(new Intent(ShopcartContent.this, ShopcartActivity.class));
            }
        });



        btn_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShopcartContent.this,"購買完成 !",Toast.LENGTH_SHORT).show();
            }
        });
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
