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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SearchActivity extends AppCompatActivity {

    DbHelper myDb;
    EditText et_search;
    Button btn_search;
    ListView lv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DbHelper(this);
        et_search = (EditText)findViewById(R.id.et_search);
        btn_search = (Button)findViewById(R.id.btn_search);
        lv_search = (ListView)findViewById(R.id.lv_search);
        Cursor c = myDb.allBook();
        UpdateAdapter(c); // 載入資料表至 ListView 中

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_search.getText().toString();
                Cursor c = myDb.searchBook(name);
                UpdateAdapter(c); // 載入資料表至 ListView 中
            }
        });

        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, BookContent.class);
                intent.putExtra("ID", l);
                startActivity(intent);
            }
        });
    }


    public void UpdateAdapter(Cursor cursor) {
        if (cursor != null && cursor.getCount() >= 0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.lv_booklayout,// 自訂的 mylayout.xml
                    cursor, // 資料庫的 Cursors 物件
                    new String[]{"name", "price", "book_picture"}, // _id、name、price 欄位
                    new int[]{R.id.txtName, R.id.txtPrice, R.id.imgIcon}, //與 _id、name、price對應的元件
                    0); // adapter 行為最佳化
            lv_search.setAdapter(adapter); // 將adapter增加到listview01中
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
