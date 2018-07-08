package com.starnet.drysistertest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button showBtn;
    private Button refreshBtn;
    private ImageView showImg;


    private ArrayList<Sister> data;
    private int curPos = 0;  // 当前显示的哪一张图片
    private int page = 1; // 当前页数
    private PictureLoad loader;
    private SisterApi sisterApi;
    private SisterTask sisterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sisterApi = new SisterApi();
        loader = new PictureLoad();
        initData();
        initUrl();
    }

    private void initData() {
        data = new ArrayList<>();
    }

    private void initUrl() {
        showBtn = (Button) findViewById(R.id.btn_show);
        showImg = (ImageView) findViewById(R.id.img_show);
        refreshBtn = (Button) findViewById(R.id.btn_refresh);

        showBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                if (data != null && !data.isEmpty()) {
                    if (curPos > 9) {
                        curPos = 0;
                    }
                    loader.load(showImg, data.get(curPos).getUrl());
                    curPos++;
                }
                break;

            case R.id.btn_refresh:
                sisterTask = new SisterTask();
                sisterTask.execute();
                curPos = 0;
                break;

            default:
                break;
        }
    }

    private class SisterTask extends AsyncTask<Void,Void,ArrayList<Sister>> {

        public SisterTask() {}

        @Override
        protected ArrayList<Sister> doInBackground(Void... params) {
            Log.d("sister","###### page: " + page);
            return sisterApi.fetchSister(10,page);
        }

        protected void onPostExecute(ArrayList<Sister> sisters) {
            super.onPostExecute(sisters);
            data.clear();
            data.addAll(sisters);
            page++;
            Log.d("sister","############################### page: " + page);
        }

        protected void onCanclelled() {
            super.onCancelled();
            sisterTask = null;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        sisterTask.onCanclelled();
    }
}
