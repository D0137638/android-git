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
    EditText ed_BookName;
    Button btn_AddBook;
    ListView lv_Book;
    Cursor cursor;

    int[] imageIDs = {
            R.drawable.ic_launcher,
            R.drawable.ic_person_add_black_24dp
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
        ed_BookName = (EditText)findViewById(R.id.ed_BookName);
        btn_AddBook = (Button)findViewById(R.id.btn_AddBook);
        lv_Book = (ListView)findViewById(R.id.lv_Book);
        Cursor c = myDb.allBook();


        btn_AddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int imageId = imageIDs[nextImageIndex];
                nextImageIndex = (nextImageIndex + 1) % imageIDs.length;

                myDb.addBook("香蕉", 30 ,imageId);

                imageId = imageIDs[nextImageIndex];
                nextImageIndex = (nextImageIndex + 1) % imageIDs.length;
                myDb.addBook("西瓜", 30 ,imageId);
                finish();
                startActivity(new Intent(RecommendActivity.this,RecommendActivity.class));
            }
        });


        lv_Book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RecommendActivity.this, BookContent.class);
                intent.putExtra("POS", i);
                intent.putExtra("ID", l);
                startActivity(intent);
            }
        });

        //cursor=myDb.allBook();       // 查詢所有資料
        UpdateAdapter(c); // 載入資料表至 ListView 中
    }

    public void UpdateAdapter(Cursor cursor){
        if (cursor != null && cursor.getCount() >= 0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.lv_booklayout,// 自訂的 mylayout.xml
                    cursor, // 資料庫的 Cursors 物件
                    new String[] {"_id","name", "price","book_picture" }, // _id、name、price 欄位
                    new int[] {R.id.txtId,R.id.txtName, R.id.txtPrice ,R.id.imgIcon}, //與 _id、name、price對應的元件
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
