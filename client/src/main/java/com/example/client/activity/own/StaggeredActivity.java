package com.example.client.activity.own;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.example.client.R;
import com.example.client.data.Image;
import com.example.client.util.SnackbarUtil;
import com.example.client.util.okHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StaggeredActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;
    private GridAdapter mAdapter;
    private List<Image> Images;
    private StaggeredGridLayoutManager mLayoutManager;
    private int mLastVisibleItem;
    private int mPage = 1;
    private ItemTouchHelper mItemTouchHelper;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggerde);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        setListener();

        new GetData().execute("http://gank.io/api/data/福利/10/1");
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.staggered_coordinatorLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.staggered_recycler);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.staggered_swipe_refresh);
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
                int dragFlags = 0;
                if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Image moveItem = Images.get(from);
                Images.remove(from);
                Images.add(to, moveItem);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 0, 当前屏幕停止滚动;
                // 1, 屏幕在滚动且用户仍在触碰或手指还在屏幕上;
                // 2, 随用户的操作,屏幕上产生的惯性滑动.
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 2 >= mLayoutManager.getItemCount()) {
                    new GetData().execute("http://gank.io/api/data/福利/10/" + (++mPage));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions = mLayoutManager.findLastVisibleItemPositions(null);
                mLastVisibleItem = Math.max(positions[0], positions[1]);
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
                    Images = gson.fromJson(jsonData, new TypeToken<List<Image>>() {}.getType());
                    Image pages = new Image();
                    pages.setPage(mPage);
                    Images.add(pages);
                } else {
                    List<Image> more = gson.fromJson(jsonData, new TypeToken<List<Image>>() {}.getType());
                    Images.addAll(more);
                    Image pages = new Image();
                    pages.setPage(mPage);
                    Images.add(pages);
                }

                if (mAdapter == null) {
                    mRecyclerView.setAdapter(mAdapter = new GridAdapter(StaggeredActivity.this, Images));
                    mAdapter.setOnItemClickListener(new GridAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int position = mRecyclerView.getChildAdapterPosition(view);
                            SnackbarUtil.ShortSnackbar(mCoordinatorLayout, String.valueOf(position), SnackbarUtil.Info).show();
                        }

                        @Override
                        public void onItemLongClick(View view) {
                            mItemTouchHelper.startDrag(mRecyclerView.getChildViewHolder(view));
                        }
                    });
                    mItemTouchHelper.attachToRecyclerView(mRecyclerView);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
