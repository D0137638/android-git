package com.example.user.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button login, register;
    EditText edID, edPass;

    DbHelper myDb;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDb = new DbHelper(this);
        session = new Session(this);

        login = (Button)findViewById(R.id.btn_Login);
        register = (Button)findViewById(R.id.btn_Register);
        edID = (EditText)findViewById(R.id.ed_userid);
        edPass = (EditText)findViewById(R.id.ed_password);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

/*        if(session.logged()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/
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

            case R.id.btn_Login:
                login();
                break;

            case R.id.btn_Register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }


    private void login(){
        String userid = edID.getText().toString();
        String password = edPass.getText().toString();

        if(myDb.getUser(userid , password)){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            session.setLogged(true);
            finish();
            Toast.makeText(getApplicationContext(), "歡迎使用 !", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "帳號或密碼錯誤 !", Toast.LENGTH_SHORT).show();
        }
    }
}
