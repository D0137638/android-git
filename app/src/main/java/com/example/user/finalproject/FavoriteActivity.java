package com.example.user.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FavoriteActivity extends AppCompatActivity {

    DbHelper myDb;
    ListView lv_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DbHelper(this);
        lv_favorite = (ListView)findViewById(R.id.lv_favorite);
        Cursor c = myDb.allFavorite();

        if(c.getCount()<=0)
        {
                Toast.makeText(FavoriteActivity.this, "目前未加入最愛 !", Toast.LENGTH_SHORT).show();
        }

        UpdateAdapter(c);

        lv_favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavoriteActivity.this, FavoriteContent.class);
                intent.putExtra("ID", l);
                startActivity(intent);
            }
        });

        lv_favorite.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, final long l) {

                AlertDialog.Builder about_msg = new AlertDialog.Builder(FavoriteActivity.this);
                about_msg.setTitle("刪除項目");
                about_msg.setMessage("確定要刪除?");

                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDb.delFavorite(l);
                        finish();
                        startActivity(new Intent(FavoriteActivity.this, FavoriteActivity.class));
                    }
                };

                DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(new Intent(FavoriteActivity.this, FavoriteActivity.class));
                    }
                };
                about_msg.setNegativeButton("確定", listener);
                about_msg.setPositiveButton("取消", listener2);
                about_msg.show();

                return false;

            }
        });
    }


    public void UpdateAdapter(Cursor cursor){
        if (cursor != null && cursor.getCount() >= 0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.lv_favorite,// 自訂的 mylayout.xml
                    cursor, // 資料庫的 Cursors 物件
                    new String[] {"name", "price","book_picture" }, // _id、name、price 欄位
                    new int[] {R.id.txtName, R.id.txtPrice ,R.id.imgIcon}, //與 _id、name、price對應的元件
                    0); // adapter 行為最佳化
            lv_favorite.setAdapter(adapter); // 將adapter增加到listview01中
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
