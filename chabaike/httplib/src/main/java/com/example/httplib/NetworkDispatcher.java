package com.example.httplib;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class NetworkDispatcher extends Thread{
       public static  final String TAG=NetworkDispatcher.class.getSimpleName();
       private BlockingDeque<Request> blockingDeque;
       public  boolean flag=true;

       public NetworkDispatcher(BlockingDeque<Request> blockingDeque) {
              this.blockingDeque = blockingDeque;
       }

       @Override
       public void run() {
              while (flag && !interrupted()){
                     try {
                            Request req=blockingDeque.take();
                            byte[] result=null;
                            try {
                                   result=getNetWorkResponse(req);
                                   if (result!=null){
                                          req.dispatchContent(result);
                                   }
                            } catch (Exception e) {
                                   e.printStackTrace();
                                   req.onError(e);
                            }
                     } catch (InterruptedException e) {
                            e.printStackTrace();
                            flag=false;
                     }
              }
       }

       public byte[] getNetWorkResponse(Request request) throws Exception{
              if (TextUtils.isEmpty(request.getUrl())){
                     throw new Exception("URL is null");
              }
              if (request.getMethod()==Request.Method.GET){
                     return getResponseByGet(request);
              }
              if (request.getMethod()==Request.Method.POST){
                     return getResponseByPost(request);
              }
             return null;
       }

       public byte[] getResponseByPost(Request request) throws Exception{
              InputStream iss=null;
              OutputStream os=null;
              HttpURLConnection conn= (HttpURLConnection) new URL(request.getUrl())
                      .openConnection();
              conn.setRequestMethod("GET");
              conn.setConnectTimeout(5000);
              int code=conn.getResponseCode();
              if (code!=200) {
                     Log.d(TAG, "NetWork response code is " + code);
                     throw new Exception("http code error");
              }

              String str=getPostEncodeString(request);
              byte[] content=null;
              if (str!=null){
                     content=str.getBytes();
                     conn.setRequestProperty("content-length",content.length+"");
              }
              os=conn.getOutputStream();
              if (content!=null){
                     os.write(content);
                     os.flush();
              }

              iss=conn.getInputStream();
              ByteArrayOutputStream bos=new ByteArrayOutputStream();
              int len=0;
              byte[] buf=new byte[2048];
              while ((len=iss.read(buf))!=-1){
                     bos.write(buf,0,len);
                     bos.flush();
              }
              iss.close();
              os.close();
              return bos.toByteArray();
       }

       public byte[] getResponseByGet(Request request) throws Exception {
                    InputStream iss=null;
                     HttpURLConnection conn= (HttpURLConnection) new URL(request.getUrl())
                             .openConnection();
                     conn.setRequestMethod("GET");
                     conn.setConnectTimeout(5000);
                     int code=conn.getResponseCode();
                     if (code!=200) {
                            Log.d(TAG, "NetWork response code is " + code);
                            throw new Exception("http code error");
                     }
                     iss=conn.getInputStream();
                     ByteArrayOutputStream bos=new ByteArrayOutputStream();
                     int len=0;
                     byte[] buf=new byte[2048];
                     while ((len=iss.read(buf))!=-1){
                            bos.write(buf,0,len);
                            bos.flush();
                     }
                     iss.close();
                     return bos.toByteArray();
       }

       public String getPostEncodeString(Request request){
              HashMap<String,String> hm=request.getPostParams();
              StringBuilder builder=new StringBuilder();
              int i=0;
              if (hm!=null){
                    Iterator<Map.Entry<String,String>> it=hm.entrySet().iterator();
                     while (it.hasNext()) {
                            if (i > 0) {
                                   builder.append("&");
                            }
                            Map.Entry<String, String> value=it.next();
                            String str=value.getKey()+"="+value.getValue();
                            builder.append(str);
                            i++;
                     }
                     return builder.toString();
              }
              return null;
       }
}
