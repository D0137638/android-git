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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button reg;
    TextView tvLogin;
    EditText edID,  edPass;

    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DbHelper(this);

        reg = (Button)findViewById(R.id.btn_Register);
        tvLogin = (TextView)findViewById(R.id.tvLogin);
        edID = (EditText)findViewById(R.id.ed_userid);
        edPass = (EditText)findViewById(R.id.ed_password);

        reg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Register:
                register();
                break;

            case R.id.tvLogin:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;

            default:
        }
    }

    private void register(){
        String userid = edID.getText().toString();
        String password = edPass.getText().toString();

        if(userid.isEmpty() && password.isEmpty()){
            Toast.makeText(getApplicationContext(), "帳號或密碼未輸入 !", Toast.LENGTH_SHORT).show();
        }
        else{
            Cursor c = db.searchUser(userid);
            if (c.getCount() == 0) {
                db.addUser(userid, password);
                Toast.makeText(getApplicationContext() , "註冊完畢 !" , Toast.LENGTH_SHORT).show();
            }
            else
            {
                finish();
                startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                Toast.makeText(getApplicationContext() , "此帳號已被註冊 !" , Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    }
}
