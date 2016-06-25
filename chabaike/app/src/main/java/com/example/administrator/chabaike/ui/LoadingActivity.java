package com.example.administrator.chabaike.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.utils.ConstantKey;
import com.example.administrator.chabaike.utils.Pref_Utils;


public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(LoadingActivity.this,WelcomeActivity.class);
                if (!getFirstOpenFlag()){
                    intent.setClass(LoadingActivity.this,HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);
    }

      public Boolean getFirstOpenFlag(){
          return Pref_Utils.getBoolean(this, ConstantKey.PRE_KEY_FIRST_OPEN,true);
      }
}