package com.starnet.drysistertest.LeftNavigationBar;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by yangyong on 18-7-12.
 */

public class NavigationTools {
    private ListView listView;
    private Context context;

    public NavigationTools(Context context,ListView listView) {
        this.listView = listView;
        this.context = context;
    }
    public void drawTools() {
        String data[] = {"我的收藏","历史观看","查看本地相册","设置","关于"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);
    }
}
