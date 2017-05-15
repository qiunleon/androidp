package com.evideo.service.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toolbar;

import com.evideo.service.R;
import com.evideo.service.adapter.ImageAdapter;
import com.evideo.service.data.Image;
import com.evideo.service.util.MyOkhttp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ImageActivity extends Activity {

    private static RecyclerView recyclerview;
    private CoordinatorLayout coordinatorLayout;
    private ImageAdapter mAdapter;
    private List<Image> Images;
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem ;
    private int page=1;
    private ItemTouchHelper itemTouchHelper;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        initView();//初始化布局
        setListener();//设置监听事件

        //执行加载数据
        new GetData().execute("http://gank.io/api/data/福利/10/1");
    }
    private void initView(){
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.grid_coordinatorLayout);

        recyclerview=(RecyclerView)findViewById(R.id.grid_recycler);
        mLayoutManager=new GridLayoutManager(ImageActivity.this,3, GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.grid_swipe_refresh) ;
        //调整SwipeRefreshLayout的位置
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void setListener(){
        //swipeRefreshLayout刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });
    }


    private class GetData extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置swipeRefreshLayout为刷新状态
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {

            return MyOkhttp.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!TextUtils.isEmpty(result)){

                JSONObject jsonObject;
                Gson gson=new Gson();
                String jsonData=null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(Images==null||Images.size()==0){
                    Images= gson.fromJson(jsonData, new TypeToken<List<Image>>() {}.getType());

                    Image pages=new Image();
                    pages.setPage(page);
                    Images.add(pages);//在数据链表中加入一个用于显示页数的item
                }else{
                    List<Image>  more= gson.fromJson(jsonData, new TypeToken<List<Image>>() {}.getType());
                    Images.addAll(more);

                    Image pages=new Image();
                    pages.setPage(page);
                    Images.add(pages);//在数据链表中加入一个用于显示页数的item
                }

                if(mAdapter==null){
                    recyclerview.setAdapter(mAdapter = new ImageAdapter(ImageActivity.this,Images));//recyclerview设置适配器

                    //实现适配器自定义的点击监听
                    mAdapter.setOnItemClickListener(new ImageAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int position=recyclerview.getChildAdapterPosition(view);
//                            SnackbarUtil.ShortSnackbar(coordinatorLayout,"点击第"+position+"个",SnackbarUtil.Info).show();
                            //彩色Snackbar工具类，请看我之前的文章《没时间解释了，快使用Snackbar!——Android Snackbar花式使用指南》http://www.jianshu.com/p/cd1e80e64311
                        }
                        @Override
                        public void onItemLongClick(View view) {

                        }
                    });
                }else{
                    //让适配器刷新数据
                    mAdapter.notifyDataSetChanged();
                }
            }
            //停止swipeRefreshLayout加载动画
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
