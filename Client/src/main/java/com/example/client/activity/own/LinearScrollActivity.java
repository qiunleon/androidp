package com.example.client.activity.own;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.data.Image;
import com.example.client.util.SnackbarUtil;
import com.example.client.util.okHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class LinearScrollActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;
    private ViewAdapter mAdapter;
    private List<Image> Images;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;
    private int mPage = 1;
    private ItemTouchHelper mItemTouchHelper;
    private int mScreenWidth;
    private boolean mIsRemove;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_scroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        setListener();

        new GetData().execute("http://gank.io/api/data/福利/10/1");
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.line_coordinatorLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.line_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.line_swipe_refresh);
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
                    // 设置侧滑方向为从左到右和从右到左都可以
                    swipeFlags = ItemTouchHelper.LEFT;
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        viewHolder.itemView.setElevation(100);
                    }
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewHolder.itemView.setElevation(0);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                viewHolder.itemView.scrollTo(-(int) dX, -(int) dY);//根据item的滑动偏移修改HorizontalScrollView的滚动
                if (Math.abs(dX) > mScreenWidth / 5 && !mIsRemove && isCurrentlyActive) {
                    // 用户收滑动item超过屏幕1/5，标记为要删除
                    mIsRemove = true;
                } else if (Math.abs(dX) < mScreenWidth / 5 && mIsRemove && !isCurrentlyActive) {
                    // 用户收滑动item没有超过屏幕1/5，标记为不删除
                    mIsRemove = false;
                }
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && mIsRemove == true && !isCurrentlyActive) {
                    // 当用户滑动tem超过屏幕1/5，并且松手时，执行删除item
                    if (viewHolder != null && viewHolder.getAdapterPosition() >= 0) {
                        mAdapter.removeItem(viewHolder.getAdapterPosition());
                        mIsRemove = false;
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 0, 当前屏幕停止滚动;
                // 1, 屏幕在滚动且用户仍在触碰或手指还在屏幕上;
                // 2, 随用户的操作,屏幕上产生的惯性滑动
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItem + 2 >= mLayoutManager.getItemCount()) {
                    new GetData().execute("http://gank.io/api/data/福利/10/" + (++mPage));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
            return okHttpUtils.get(params[0]);
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
                    mRecyclerView.setAdapter(mAdapter = new ViewAdapter());
                    mItemTouchHelper.attachToRecyclerView(mRecyclerView);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
        
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LinearScrollActivity.this).inflate(R.layout.linear_scroll_image_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            WindowManager wm = (WindowManager) LinearScrollActivity.this.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            mScreenWidth = outMetrics.widthPixels;
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            if (holder.itemView.getScrollX() != 0) {
                ((HorizontalScrollView) holder.itemView).fullScroll(View.FOCUS_UP); // 如果item的HorizontalScrollView没在初始位置，则滚动回顶部
            }
            holder.linearLayout.setMinimumWidth(mScreenWidth); // 设置LinearLayout宽度为屏幕宽度
            holder.text.setText(String.valueOf(position));
            Picasso.with(LinearScrollActivity.this).load(Images.get(position).getUrl()).into(holder.image);
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SnackbarUtil.ShortSnackbar(mCoordinatorLayout, String.valueOf(position), SnackbarUtil.Info).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return Images.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView image;
            private TextView text;
            private LinearLayout linearLayout;

            public MyViewHolder(View view) {
                super(view);
                image = (ImageView) view.findViewById(R.id.image);
                text = (TextView) view.findViewById(R.id.text);
                linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            }
        }

        public void addItem(Image Image, int position) {
            Images.add(position, Image);
            notifyItemInserted(position);
            mRecyclerView.scrollToPosition(position);
        }

        public void removeItem(final int position) {
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

        public void removeItem(Image Image) {
            int position = Images.indexOf(Image);
            Images.remove(position);
            notifyItemRemoved(position);
        }
    }
}
