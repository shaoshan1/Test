package com.example.administrator.chabaike.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.adapter.InfoListAdapter;
import com.example.administrator.chabaike.beans.Info;
import com.example.administrator.chabaike.ui.DetailActivity;
import com.example.administrator.chabaike.utils.ConstantKey;
import com.example.httplib.HttpHelper;
import com.example.httplib.Request;
import com.example.httplib.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class ContentFragment extends Fragment{
    private ListView mlv;
    private InfoListAdapter adapter;
    private int class_id;
    private PtrClassicFrameLayout mRefreshView;
    private List<Info> infos=new ArrayList<Info>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle bundle) {
        View view=View.inflate(getContext(), R.layout.frag_content,null);
        initView(view);
        class_id=getArguments().getInt("id");
         if (bundle!=null){
             Parcelable[] ps=bundle.getParcelableArray("cache");
             Info[] ins= (Info[]) bundle.getParcelableArray("cache");
             if (ins!=null&&ins.length!=0){
                 infos= Arrays.asList(ins);
                 adapter=new InfoListAdapter(infos);
                 mlv.setAdapter(adapter);
             }else {
                 getDataFromNetwork();
             }
         }else {
             getDataFromNetwork();
         }
        return view;
    }

    private void initView(View view) {
        mlv= (ListView) view.findViewById(R.id.frag_content_lv);
        mRefreshView = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mRefreshView.setResistance(1.7f);
        mRefreshView.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshView.setDurationToClose(200);
        mRefreshView.setDurationToCloseHeader(1000);
        // default is false
        mRefreshView.setPullToRefresh(true);
        // default is true
        mRefreshView.setKeepHeaderWhenRefresh(true);

        mRefreshView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDataFromNetwork();
            }
        });

        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long  cid= infos.get(position).getId();
                String description= infos.get(position).getDescription();
                String time=infos.get(position).getTime();
                String keywords=infos.get(position).getKeywords();
                String img1=infos.get(position).getImg();
                String title=infos.get(position).getTitle();
                setDataInDataBase(title,keywords,cid,time,img1,description);

//                SQLiteDatabase db=SQLiteDatabase.openDatabase(ConstantKey.DETAILPATH,null,SQLiteDatabase.OPEN_READWRITE);
//                db.execSQL("insert into info(title,keywords,time,img,description) values(?,?,?,?,?)" , new Object[]{title,keywords,time,img1,description});
//                db.close();
//                Toast.makeText(getActivity().getApplicationContext(),"数据插入detail成功",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("description",description);
                bundle.putString("time",time);
                bundle.putLong("cid",cid);
                bundle.putString("keywords",keywords);
                bundle.putString("img1",img1);
                bundle.putString("title",title);
                intent.putExtras(bundle);
                startActivity(intent);

//                http://www.tngou.net/top/show/+id
            }
        });
    }

    private void setDataInDataBase(String title, String keywords,long cid, String time, String img1, String description) {
        SQLiteDatabase db=SQLiteDatabase.openDatabase(ConstantKey.DETAILPATH,null,SQLiteDatabase.OPEN_READWRITE);
        db.execSQL("insert into look(title,keywords,lookid,time,img,description) values(?,?,?,?,?,?)" , new Object[]{title,keywords,cid,time,img1,description});
        db.close();
        Toast.makeText(getActivity().getApplicationContext(),"数据插入detail成功",Toast.LENGTH_SHORT).show();
    }

    private void getDataFromNetwork() {
        String url = "http://www.tngou.net/api/info/list?id="+class_id;
        StringRequest req=new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onEror(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onReponse(String response) {
                try {
                    JSONObject jsonOb=new JSONObject(response);
                    List<Info> listInfo=parseJsonOb(jsonOb);
                    if (listInfo!=null){
                        infos.clear();
                        infos.addAll(listInfo);
                        if (adapter==null){
                            adapter=new InfoListAdapter(infos);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mlv.setAdapter(adapter);
                                }
                            });
                        }
                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mlv.setAdapter(adapter);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshView.refreshComplete();
                    }
                });
            }
        });
        HttpHelper.addRequest(req);
    }

    private List<Info> parseJsonOb(JSONObject jsonOb) throws JSONException {
        if (jsonOb==null) return null ;
            JSONArray array= jsonOb.getJSONArray("tngou");
            if (array==null&&array.length()==0)return  null;
            List<Info> list=new ArrayList<Info>();
            Info info=null;
            for (int i = 0; i <array.length() ; i++) {
                JSONObject obj=array.getJSONObject(i);
                info=new Info();

                info.setDescription(obj.optString("description"));
                info.setImg( obj.optString("img"));
                info.setId(obj.optInt("id"));
                info.setRcount(obj.optInt("rcount"));
                info.setKeywords(obj.optString("keywords"));
                info.setTitle(obj.optString("title"));

                long time=obj.optLong("time");
                String str= new SimpleDateFormat("yyyyMMdd:hhmmss").format(time);
                info.setTime(str);
                list.add(info);
            }
        return  list;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (infos==null&&infos.size()==0)return;
        Info[] parce=new Info[infos.size()];
        infos.toArray(parce);
        outState.putParcelableArray("cache",parce);
    }
}
