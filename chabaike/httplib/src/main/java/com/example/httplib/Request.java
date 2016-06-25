package com.example.httplib;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
abstract public class Request<T>{
    private String url;
    private Method method;
    private Callback callback;

        public Request(String url, Method method, Callback callback) {
            this.url = url;
            this.method = method;
            this.callback = callback;
        }
        public  enum Method{
            GET,POST
        }
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Callback getCallback() {
            return callback;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
        public interface Callback<T>{
            void onEror(Exception e);
            void onReponse(T response);
        }

        public void onResponse(T response){
            callback.onReponse(response);
        }

       public void onError(Exception e){
           callback.onEror(e);
       }

       public HashMap<String,String> getPostParams(){
           return null;
       }

    abstract  public void dispatchContent(byte[] content);
}
