package com.example.administrator.chabaike.ui;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.ActionBar;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import android.view.View;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.utils.ConstantKey;


import java.io.File;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView_content_title;
    private TextView textView_content_create_time;
    private TextView textView_content_keywords;


    private ImageView collect_bar,share_bar,next_bar;
    private WebView webView;

    private String description;
    private String time;
    private String keywords;
    private String img1;
    private String title;
    private long collectid;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        View action_ic= LayoutInflater.from(this).inflate(R.layout.action_icon,null);
        actionBar.setCustomView(action_ic);

        collect_bar= (ImageView) action_ic.findViewById(R.id.collect_bar);
        share_bar= (ImageView) action_ic.findViewById(R.id.share_bar);
        next_bar= (ImageView) action_ic.findViewById(R.id.next_bar);
        collect_bar.setOnClickListener(this);
        share_bar.setOnClickListener(this);
        next_bar.setOnClickListener(this);

        textView_content_title = (TextView) findViewById(R.id.textView_content_title);
        textView_content_create_time = (TextView) findViewById(R.id.textView_content_create_time);
        textView_content_keywords = (TextView) findViewById(R.id.textView_content_keywords);


        initData();
        webView= (WebView) findViewById(R.id.detail_web);
        webView.loadUrl(" http://www.tngou.net/info/show/"+collectid);
    }



    private void initData() {

         Bundle bundle=getIntent().getExtras();
         description= bundle.getString("description");
         time= bundle.getString("time");
         keywords= bundle.getString("keywords");
         img1= bundle.getString("img1");
         title= bundle.getString("title");
         collectid=bundle.getLong("cid");


//        SQLiteDatabase db=SQLiteDatabase.openDatabase(ConstantKey.DETAILPATH,null,SQLiteDatabase.OPEN_READWRITE);
//        db.execSQL("insert into info(title,keywords,time,img,description) values(?,?,?,?,?)" , new Object[]{title,keywords,time,img1,description});
//        db.close();
//        Toast.makeText(DetailActivity.this,"数据插入detail成功",Toast.LENGTH_SHORT).show();


        textView_content_title.setText(title);
        textView_content_create_time.setText(time);
        textView_content_keywords.setText(keywords);
//        textView_detail_desc.setText(description);
//        textView_detail_img.setImageResource(R.drawable.ic_launcher);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //分享
            case R.id.share_bar:
                Bundle bundle_share=getIntent().getExtras();
                String detail_share=bundle_share.getString("description");
                Intent intent_share=new Intent();
                intent_share.setAction(Intent.ACTION_SEND);
                intent_share.putExtra(Intent.EXTRA_TEXT,detail_share);
                intent_share.setType("text/*");
                startActivity(intent_share);
                break;

            //收藏
            case R.id.collect_bar:
                Bundle bundle_collect=getIntent().getExtras();
                String description_collect= bundle_collect.getString("description");
                String keywords_collect= bundle_collect.getString("keywords");
                String title_collect= bundle_collect.getString("title");
                long collectid=bundle_collect.getLong("cid");
                SQLiteDatabase db=SQLiteDatabase.openDatabase(ConstantKey.DBPATH,null,SQLiteDatabase.OPEN_READWRITE);
                db.execSQL("insert into collect(title,keywords,collectid,description) values(?,?,?,?)" , new Object[]{title_collect,keywords_collect,collectid,description_collect});
                db.close();
                Toast.makeText(DetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                break;

            //跳转页面
            case R.id.next_bar:
                Intent intent_next=new Intent();
                intent_next.setClass(DetailActivity.this,NextActivity.class);
                startActivity(intent_next);
                break;
        }
    }
}