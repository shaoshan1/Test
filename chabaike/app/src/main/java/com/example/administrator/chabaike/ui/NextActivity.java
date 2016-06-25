package com.example.administrator.chabaike.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.utils.ConstantKey;


public class NextActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banben_work);


    }
    public void click(View view){
        switch (view.getId()){
            //收藏夹
            case R.id.next_btn_collection:
                Intent intent_collect=new Intent();
                intent_collect.setClass(NextActivity.this,MyCollectActivity.class);
                startActivity(intent_collect);
                break;

            //浏览记录
            case R.id.next_btn_liulan:
                Intent intent_look=new Intent();
                intent_look.setClass(NextActivity.this,LookActivity.class);
                startActivity(intent_look);
                break;

            //版本信息
            case R.id.next_btn_bx:
                Intent intent_next=new Intent();
                intent_next.setClass(NextActivity.this,BanbenInfoActivity.class);
                startActivity(intent_next);
                break;

            //客户反馈
            case R.id.next_btn_fk:
                Intent intent_kehu=new Intent();
                intent_kehu.setClass(NextActivity.this,SuggestActivity.class);
                startActivity(intent_kehu);
                break;

        }
    }

}