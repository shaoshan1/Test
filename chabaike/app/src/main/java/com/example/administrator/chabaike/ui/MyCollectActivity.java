package com.example.administrator.chabaike.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.utils.ConstantKey;
import com.example.administrator.chabaike.utils.MySQLiteDataBaseHelper;
import com.example.administrator.chabaike.utils.Pref_Utils;

import java.util.List;
import java.util.Map;


public class MyCollectActivity extends AppCompatActivity {
    private MySQLiteDataBaseHelper db;
    private ListView listView_mycollect;
    private  List<Map<String, Object>> list_mycollect;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollect);
        db=new MySQLiteDataBaseHelper();
        listView_mycollect= (ListView) findViewById(R.id.listview_mycollect);
        readList();
        listView_mycollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               
            }
        });
    }

    private void readList() {
         list_mycollect= db.selectList("select * from collect order by cid desc",null);
        adapter=new SimpleAdapter(this,list_mycollect,
                R.layout.item_listview_mycollect,new String[]{"title","keywords","description"},
                new int[]{R.id.lv_text_title,R.id.lv_text_keywords, R.id.lv_text_description });
        listView_mycollect.setAdapter(adapter);
    }


}