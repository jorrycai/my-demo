package com.example.meican.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by jorry on 2016/7/21.
 */
public class test {
    public void get(){
        String url = "http://apis.baidu.com/songshuxiansheng/news/news";
        Map<String, String> map2 = new HashMap<>();
        map2.put("apikey", "69423f293c4e6e6ea93e1ff05da05c9a");
        OkHttpUtils
                .get()
                .url(url)
                .headers(map2)
                .addHeader("Content-Type","application/json")
                .addParams("apikey", "69423f293c4e6e6ea93e1ff05da05c9a")
                .id(1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.print("this.."+response);
                    }
                });
    }
    public static void main(String args[]) {
        test t= new test();
        t.get();
        System.out.println("Hello World!");
    }
}
