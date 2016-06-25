package com.example.httplib;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class HttpHelper {

    private static HttpHelper instance;
    private RequestQueue mque;
    private static HttpHelper getInstance(){
        if (instance==null){
            synchronized (HttpHelper.class){
                if (instance==null){
                    instance=new HttpHelper();
                }
            }
        }
        return instance;
    }

    public HttpHelper(){
        mque=new RequestQueue();
    }

    public static void addRequest(Request request){
        getInstance().mque.addRequest(request);
    }
}
