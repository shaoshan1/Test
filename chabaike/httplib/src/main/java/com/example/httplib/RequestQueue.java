package com.example.httplib;

import android.provider.UserDictionary;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class RequestQueue {
    private BlockingDeque<Request> blockingDeque=new LinkedBlockingDeque<>();
    private final int MAX_NET_NUM=3;
    private NetworkDispatcher[] works=new NetworkDispatcher[MAX_NET_NUM];

    public RequestQueue() {
        initDispatcher();
    }

    private void initDispatcher() {
        for (int i = 0; i < works.length; i++) {
            works[i]=new NetworkDispatcher(blockingDeque);
            works[i].start();
        }
    }

    public void addRequest(Request request){
      blockingDeque.add(request);
  }

    public void stop(){
        for (int i = 0; i < works.length; i++) {
            works[i].flag=false;
            works[i].interrupt();
        }
    }
}
