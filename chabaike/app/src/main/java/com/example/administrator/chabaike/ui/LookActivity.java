package com.example.administrator.chabaike.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.utils.LookSQLiteDataBaseHelper;
import com.example.administrator.chabaike.utils.MySQLiteDataBaseHelper;

import java.util.List;
import java.util.Map;


public class LookActivity extends AppCompatActivity {
    private LookSQLiteDataBaseHelper db_look;
    private ListView listView_look;
    private  List<Map<String, Object>> list_mylook;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        db_look=new LookSQLiteDataBaseHelper();
        listView_look= (ListView) findViewById(R.id.listview_mylook);
        readList();
//        listView_look.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               db_look.selecCursor("select lookid from look where lookid=?");
//            }
//        });

    }

    private void readList() {
         list_mylook= db_look.selectList("select * from look order by lid desc",null);
        adapter=new SimpleAdapter(this,list_mylook,
                R.layout.item_listview_mycollect,new String[]{"title","keywords","description"},
                new int[]{R.id.lv_text_title,R.id.lv_text_keywords, R.id.lv_text_description });
        listView_look.setAdapter(adapter);
    }


}