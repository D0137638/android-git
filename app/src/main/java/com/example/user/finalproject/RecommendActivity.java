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
import android.widget.Toast;

public class RecommendActivity extends AppCompatActivity {

    DbHelper myDb;
    EditText ed_BookName;
    Button btn_AddBook;
    ListView lv_Book;

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
        if(c.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            return;
        }

        c.moveToFirst();

        final String[] title = new String[c.getCount()];
        int n = 0;

        do{
            title[n] = c.getString(1).toString();
            n = n + 1;
        }while (c.moveToNext());

        final ArrayAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,title);
        lv_Book.setAdapter(listAdapter);


        btn_AddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookname = ed_BookName.getText().toString();
                myDb.addBook(bookname);
                listAdapter.notifyDataSetChanged();
                finish();
                startActivity(new Intent(RecommendActivity.this, RecommendActivity.class));
            }
        });

        lv_Book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(RecommendActivity.this, BookContent.class);
                intent.putExtra("POSITION", i + 1);
                startActivity(intent);
                //startActivity(new Intent(RecommendActivity.this, BookContent.class));
                //Toast.makeText(RecommendActivity.this,"123",Toast.LENGTH_SHORT).show();
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
