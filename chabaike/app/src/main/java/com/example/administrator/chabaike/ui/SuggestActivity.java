package com.example.administrator.chabaike.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.utils.ConstantKey;
import com.example.administrator.chabaike.utils.Pref_Utils;


public class SuggestActivity extends AppCompatActivity {
    private EditText editText_title;
    private EditText editText_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banben_suggest);
        editText_title= (EditText) findViewById(R.id.banben_suggest_title);
        editText_content= (EditText) findViewById(R.id.banben_suggest_advice);

    }
    public void suggestclick(View view){
        String str_title=editText_title.getText().toString();
        String str_content=editText_content.getText().toString();
        SQLiteDatabase db=SQLiteDatabase.openDatabase(ConstantKey.SUGGESTPATH,null,SQLiteDatabase.OPEN_READWRITE);
        db.execSQL("insert into advice(title,content) values(?,?)" , new Object[]{str_title,str_content});
        db.close();
        Toast.makeText(SuggestActivity.this,"我们已收到你的建议，会做出相应的调整",Toast.LENGTH_SHORT).show();
    }

}