package com.example.administrator.chabaike.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.beans.Info;
import com.example.httplib.HttpHelper;
import com.example.httplib.BitmapRequest;
import com.example.httplib.Request;


/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class InfoListAdapter extends BaseAdapter {
    private List<Info> infoList;

    public InfoListAdapter(List<Info> infoList) {
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return this.infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(parent.getContext(), R.layout.content_lv_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_desc= (TextView) convertView.findViewById(R.id.frag_content_desc);
            viewHolder.tv_reads= (TextView) convertView.findViewById(R.id.frag_content_reads);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.frag_content_time);
            viewHolder.iv_imag= (ImageView) convertView.findViewById(R.id.frag_content_image);
            convertView.setTag(viewHolder);
        }
        Info info= (Info) getItem(position);
        viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.tv_desc.setText(info.getDescription());
        viewHolder.tv_time.setText(info.getTime());
        viewHolder.tv_reads.setText(""+info.getRcount());
        viewHolder.iv_imag.setImageResource(R.drawable.ic_launcher);
        load(viewHolder.iv_imag,"http://tnfs.tngou.net/image"+info.getImg()+"_100x100");
        return convertView;
    }

    public void load(final ImageView iv, final String url) {
        iv.setTag(url);
        BitmapRequest bm=new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {
            @Override
            public void onEror(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onReponse(final Bitmap response) {
                     if (iv!=null && response!=null &&((String ) iv.getTag()).equals(url)){
                         new Handler(Looper.getMainLooper()).post(new Runnable() {
                             @Override
                             public void run() {
                                 iv.setImageBitmap(response);
                             }
                         });
                     }
            }
        });
        HttpHelper.addRequest(bm);
    }

    public class ViewHolder{
         public TextView tv_desc;
        public TextView tv_reads;
        public TextView tv_time;
        public ImageView iv_imag;

    }
}
