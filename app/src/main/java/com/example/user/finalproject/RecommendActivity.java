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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class RecommendActivity extends AppCompatActivity {

    DbHelper myDb;
    ListView lv_Book;

    int[] imageIDs = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g,
            R.drawable.h,
            R.drawable.i,
            R.drawable.j
    };
    int nextImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDb = new DbHelper(this);
        lv_Book = (ListView)findViewById(R.id.lv_Book);
        Cursor c = myDb.allBook();

        if(c.getCount()<=0)
        {
            int imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("解憂○貨店", 277 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("巴黎○書店", 277 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("愛的○化論", 245 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("把你的名○曬一曬", 261 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("蘭亭○密碼", 308 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("列車○的女孩", 277 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("人魚○哭", 190 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("盛夏○開", 190 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("○聽", 342 ,imageId);

            imageId = imageIDs[nextImageIndex];
            nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
            myDb.addBook("來自天○的雨", 198 ,imageId);

            finish();
            startActivity(new Intent(RecommendActivity.this, RecommendActivity.class));
        }


        lv_Book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RecommendActivity.this, BookContent.class);
                intent.putExtra("ID", l);
                startActivity(intent);
            }
        });

        UpdateAdapter(c); // 載入資料表至 ListView 中
    }

    public void UpdateAdapter(Cursor cursor){
        if (cursor != null && cursor.getCount() >= 0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.lv_booklayout,// 自訂的 mylayout.xml
                    cursor, // 資料庫的 Cursors 物件
                    new String[] {"name", "price","book_picture" }, // _id、name、price 欄位
                    new int[] {R.id.txtName, R.id.txtPrice ,R.id.imgIcon}, //與 _id、name、price對應的元件
                    0); // adapter 行為最佳化
            lv_Book.setAdapter(adapter); // 將adapter增加到listview01中
        }
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
