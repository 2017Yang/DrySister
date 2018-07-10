package com.starnet.drysistertest;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangyong on 18-7-7.
 */

/**
 *  查询妹子的信息
 */

public class SisterApi {
    private static final String TAG = "NetWork";
    private static final String BASE_URL = "http://gank.io/api/data/福利/";

    public ArrayList<Sister> fetchSister(int count, int page){
      String fetchUrl = BASE_URL + count + "/" + page;
      Log.d(TAG,"Server fetchUrl: " + fetchUrl);

      ArrayList<Sister> sistes = new ArrayList<>();

      try {
          OkHttpClient client = new OkHttpClient();
          Request request = new Request.Builder()
                  .url(fetchUrl)
                  .build();
          Response response = client.newCall(request).execute();
          if (response != null) {
              Log.d(TAG,"response != null");
              InputStream in = response.body().byteStream();
              byte[] data = readFromStream(in);
              String results = new String(data,"UTF-8");
              sistes =  parseSister(results);
              for (int i = 0; i < 10; i++) {
                  String url = sistes.get(i).getUrl();
                  Log.d(TAG,"url: " + url);
              }
          }
      } catch(Exception e) {
          e.printStackTrace();
      }
      return sistes;
    }

    /***
     * 解析返回json的数据
     */
     public ArrayList<Sister> parseSister(String content) throws Exception{
         ArrayList<Sister> sisters = new ArrayList<>();
         JSONObject object = new JSONObject(content);
         JSONArray array = object.getJSONArray("results");
         for (int i = 0; i < array.length(); i++) {
             Sister sister = new Sister();
             JSONObject results = (JSONObject)array.get(i);
             sister.set_id(results.getString("_id"));
             sister.setCreateAt(results.getString("createdAt"));
             sister.setDesc(results.getString("desc"));
             sister.setPublishedAt(results.getString("publishedAt"));
             sister.setSource(results.getString("source"));
             sister.setType(results.getString("type"));
             sister.setUrl(results.getString("url"));
             sister.setUsed(results.getBoolean("used"));
             sister.setWho(results.getString("who"));

             Log.d(TAG,"##### url: " + sister.getUrl());
             sisters.add(sister);
         }
         return sisters;
     }

    /**
     * InputStream 输入数据 ==（read)
     * OutputStream 输出数据 == （write)
     * 读取流中的数据数据
     * */
    public byte[] readFromStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer,0,len);
        }
        inputStream.close();
        return  outputStream.toByteArray();
    }

}
