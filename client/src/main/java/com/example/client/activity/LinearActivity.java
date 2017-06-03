package com.example.client.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.data.Image;
import com.example.client.util.SnackbarUtil;
import com.example.client.util.okHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class LinearActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;
    private ViewAdapter mAdapter;
    private List<Image> Images;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;
    private int mPage = 1;
    private ItemTouchHelper mItemTouchHelper;
    private int mScreenWidth;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        setListener();

        new GetData().execute("http://gank.io/api/data/福利/10/1");

        //获取屏幕宽度
        WindowManager wm = (WindowManager) LinearActivity.this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.line_coordinatorLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.line_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.line_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0, swipeFlags = 0;
                if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    //设置侧滑方向为从左到右和从右到左都可以
                    swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(Images, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.removeItem(viewHolder.getAdapterPosition());

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                viewHolder.itemView.setAlpha(1 - Math.abs(dX) / mScreenWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 0, 当前屏幕停止滚动;
                // 1, 屏幕在滚动 且 用户仍在触碰或手指还在屏幕上;
                // 2, 随用户的操作，屏幕上产生的惯性滑动;
                // 滑动状态停止并且剩余两个item时自动加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 2 >= mLayoutManager.getItemCount()) {
                    new GetData().execute("http://gank.io/api/data/福利/10/" + (++mPage));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 获取加载的最后一个可见视图在适配器的位置
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    private class GetData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            return okHttpUtil.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!TextUtils.isEmpty(result)) {

                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (Images == null || Images.size() == 0) {
                    Images = gson.fromJson(jsonData, new TypeToken<List<Image>>() {
                    }.getType());
                    Image mPages = new Image();
                    mPages.setPage(mPage);
                    Images.add(mPages);
                } else {
                    List<Image> more = gson.fromJson(jsonData, new TypeToken<List<Image>>() {
                    }.getType());
                    Images.addAll(more);
                    Image mPages = new Image();
                    mPages.setPage(mPage);
                    Images.add(mPages);
                }

                if (mAdapter == null) {
                    mRecyclerView.setAdapter(mAdapter = new ViewAdapter());
                    mItemTouchHelper.attachToRecyclerView(mRecyclerView);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> implements View.OnClickListener {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LinearActivity.this).inflate(R.layout.line_image_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(position);
            Picasso.with(LinearActivity.this).load(Images.get(position).getUrl()).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return Images.size();
        }

        @Override
        public void onClick(View v) {
            int position = mRecyclerView.getChildAdapterPosition(v);
            SnackbarUtil.ShortSnackbar(mCoordinatorLayout, String.valueOf(position), SnackbarUtil.Info).show();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView image;
            private TextView text;

            ViewHolder(View view) {
                super(view);
                image = (ImageView) view.findViewById(R.id.linear_item_image);
                text = (TextView) view.findViewById(R.id.linear_item_text);
            }
        }

        void addItem(Image Image, int position) {
            Images.add(position, Image);
            notifyItemInserted(position);
            mRecyclerView.scrollToPosition(position);
        }

        void removeItem(final int position) {
            final Image removed = Images.get(position);
            Images.remove(position);
            notifyItemRemoved(position);
            SnackbarUtil.ShortSnackbar(mCoordinatorLayout, String.valueOf(position), SnackbarUtil.Warning).setAction("撤销", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem(removed, position);
                    SnackbarUtil.ShortSnackbar(mCoordinatorLayout, String.valueOf(position), SnackbarUtil.Confirm).show();
                }
            }).setActionTextColor(Color.WHITE).show();
        }
    }
}
